package com.cooljs.modules.dict.service.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cooljs.core.base.BaseServiceImpl;
import com.cooljs.modules.dict.entity.DictInfoEntity;
import com.cooljs.modules.dict.entity.DictTypeEntity;
import com.cooljs.modules.dict.mapper.DictInfoMapper;
import com.cooljs.modules.dict.mapper.DictTypeMapper;
import com.cooljs.modules.dict.service.DictInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典信息
 */
@Service
public class DictInfoServiceImpl extends BaseServiceImpl<DictInfoMapper, DictInfoEntity> implements DictInfoService {
    @Resource
    DictTypeMapper dictTypeMapper;

    @Override
    public Object data(List<String> types) {
        Dict result = Dict.create();
        QueryWrapper<DictTypeEntity> find = new QueryWrapper<>();
        find.select("keyName as `key`", "name", "id");
        if (ArrayUtil.isNotEmpty(types)) {
            find.in("keyName", types);
        }
        List<DictTypeEntity> typeData = dictTypeMapper.selectList(find);
        if (typeData.size() <= 0) {
            return result;
        }
        List<DictInfoEntity> infos = this.list(Wrappers.<DictInfoEntity>lambdaQuery()
                .select(DictInfoEntity::getId, DictInfoEntity::getName, DictInfoEntity::getTypeId, DictInfoEntity::getParentId)
                .in(DictInfoEntity::getTypeId, typeData.stream().map(DictTypeEntity::getId).collect(Collectors.toList()))
                .orderByAsc(DictInfoEntity::getOrderNum)
                .orderByAsc(DictInfoEntity::getCreateTime));
        typeData.forEach(item -> {
            List<Dict> datas = new ArrayList<>();
            infos.stream().filter(d -> d.getTypeId().equals(item.getId())).collect(Collectors.toList()).forEach(d -> {
                Dict data = Dict.create();
                data.set("typeId", d.getTypeId());
                data.set("parentId", d.getParentId());
                data.set("name", d.getName());
                data.set("id", d.getId());
                datas.add(data);
            });
            result.set(item.getKey(), datas);
        });
        return result;
    }

    @Override
    public void delete(Long... ids) {
        super.delete(ids);
        for (Long id : ids) {
            this.delDictChild(id);
        }
    }

    /**
     * 删除子菜单
     *
     * @param id 删除的菜单ID
     */
    private void delDictChild(Long id) {
        List<DictInfoEntity> delDict = list(Wrappers.<DictInfoEntity>lambdaQuery().eq(DictInfoEntity::getParentId, id));
        if (ArrayUtil.isEmpty(delDict)) {
            return;
        }
        Long[] ids = delDict.stream().map(DictInfoEntity::getId).toArray(Long[]::new);
        if (ArrayUtil.isNotEmpty(ids)) {
            delete(ids);
            for (Long delId : ids) {
                this.delDictChild(delId);
            }
        }
    }
}
package com.cooljs.modules.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cooljs.core.base.BaseServiceImpl;
import com.cooljs.modules.dict.entity.DictInfoEntity;
import com.cooljs.modules.dict.entity.DictTypeEntity;
import com.cooljs.modules.dict.mapper.DictInfoMapper;
import com.cooljs.modules.dict.mapper.DictTypeMapper;
import com.cooljs.modules.dict.service.DictTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 字典类型
 */
@Service
public class DictTypeServiceImpl extends BaseServiceImpl<DictTypeMapper, DictTypeEntity> implements DictTypeService {
    @Resource
    private DictInfoMapper dictInfoMapper;

    @Override
    public Object list(QueryWrapper<DictTypeEntity> queryWrapper) {
        return super.list(queryWrapper.select("keyName as `key`", "name", "id"));
    }

    @Override
    public void delete(Long... ids) {
        super.delete(ids);
        dictInfoMapper.delete(new QueryWrapper<DictInfoEntity>().in("typeId", ids));
    }
}
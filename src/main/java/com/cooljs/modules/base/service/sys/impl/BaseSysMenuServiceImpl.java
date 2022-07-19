package com.cooljs.modules.base.service.sys.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cooljs.core.base.BaseServiceImpl;
import com.cooljs.core.base.ModifyEnum;
import com.cooljs.modules.base.entity.sys.BaseSysMenuEntity;
import com.cooljs.modules.base.mapper.sys.BaseSysMenuMapper;
import com.cooljs.modules.base.security.CoolSecurityUtil;
import com.cooljs.modules.base.service.sys.BaseSysMenuService;
import com.cooljs.modules.base.service.sys.BaseSysPermsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统菜单
 */
@Service
public class BaseSysMenuServiceImpl extends BaseServiceImpl<BaseSysMenuMapper, BaseSysMenuEntity> implements BaseSysMenuService {
    @Resource
    private BaseSysPermsService baseSysPermsService;

    @Resource
    private CoolSecurityUtil coolSecurityUtil;

    @Override
    public Object list(JSONObject requestParams, QueryWrapper<BaseSysMenuEntity> queryWrapper) {
        List<BaseSysMenuEntity> list = baseSysPermsService.getMenus(coolSecurityUtil.username());
        list.forEach(e -> {
            List<BaseSysMenuEntity> parent = list.stream().filter(sysMenuEntity ->
                    e.getParentId() != null && e.getParentId().equals(sysMenuEntity.getId())).collect(Collectors.toList());
            if (!parent.isEmpty()) {
                e.setParentName(parent.get(0).getName());
            }
        });
        return list;
    }

    @Override
    public void modifyAfter(JSONObject requestParams, BaseSysMenuEntity sysMenuEntity, ModifyEnum type) {
        if (sysMenuEntity != null && sysMenuEntity.getId() != null) {
            baseSysPermsService.refreshPermsByMenuId(requestParams.getLong("id"));
        }
        if (requestParams.get("ids") != null) {
            Long[] ids = requestParams.get("ids", Long[].class);
            for (Long id : ids) {
                baseSysPermsService.refreshPermsByMenuId(id);
            }
        }
    }

    @Override
    public void delete(Long... ids) {
        super.delete(ids);
        for (Long id : ids) {
            this.delChildMenu(id);
        }
    }

    /**
     * 删除子菜单
     *
     * @param id 删除的菜单ID
     */
    private void delChildMenu(Long id) {
        List<BaseSysMenuEntity> delMenu = list(Wrappers.<BaseSysMenuEntity>lambdaQuery().eq(BaseSysMenuEntity::getParentId, id));
        if (ArrayUtil.isEmpty(delMenu)) {
            return;
        }
        Long[] ids = delMenu.stream().map(BaseSysMenuEntity::getId).toArray(Long[]::new);
        if (ArrayUtil.isNotEmpty(ids)) {
            delete(ids);
            for (Long delId : ids) {
                this.delChildMenu(delId);
            }
        }
    }
}
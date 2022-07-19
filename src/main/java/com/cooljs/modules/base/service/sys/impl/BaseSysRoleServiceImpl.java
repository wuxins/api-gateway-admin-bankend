package com.cooljs.modules.base.service.sys.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cooljs.core.base.BaseEntity;
import com.cooljs.core.base.BaseServiceImpl;
import com.cooljs.core.base.ModifyEnum;
import com.cooljs.core.exception.CoolException;
import com.cooljs.modules.base.entity.sys.BaseSysRoleDepartmentEntity;
import com.cooljs.modules.base.entity.sys.BaseSysRoleEntity;
import com.cooljs.modules.base.entity.sys.BaseSysRoleMenuEntity;
import com.cooljs.modules.base.mapper.sys.BaseSysRoleDepartmentMapper;
import com.cooljs.modules.base.mapper.sys.BaseSysRoleMapper;
import com.cooljs.modules.base.mapper.sys.BaseSysRoleMenuMapper;
import com.cooljs.modules.base.security.CoolSecurityUtil;
import com.cooljs.modules.base.service.sys.BaseSysPermsService;
import com.cooljs.modules.base.service.sys.BaseSysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统角色
 */
@Service
public class BaseSysRoleServiceImpl extends BaseServiceImpl<BaseSysRoleMapper, BaseSysRoleEntity> implements BaseSysRoleService {

    @Resource
    private BaseSysRoleMapper baseSysRoleMapper;

    @Resource
    private BaseSysRoleMenuMapper baseSysRoleMenuMapper;

    @Resource
    private BaseSysRoleDepartmentMapper baseSysRoleDepartmentMapper;

    @Resource
    private BaseSysPermsService baseSysPermsService;

    @Resource
    private CoolSecurityUtil coolSecurityUtil;

    @Override
    public Object add(JSONObject requestParams, BaseSysRoleEntity entity) {
        BaseSysRoleEntity checkLabel = getOne(Wrappers.<BaseSysRoleEntity>lambdaQuery().eq(BaseSysRoleEntity::getLabel, entity.getLabel()));
        if (checkLabel != null) {
            throw new CoolException("标识已存在");
        }
        entity.setUserId((coolSecurityUtil.userInfo(requestParams).getLong("userId")));
        return super.add(requestParams, entity);
    }

    @Override
    public Object info(Long id) {
        BaseSysRoleEntity roleEntity = getById(id);
        Long[] menuIdList = new Long[0];
        Long[] departmentIdList = new Long[0];
        if (roleEntity != null) {
            List<BaseSysRoleMenuEntity> list = baseSysRoleMenuMapper.selectList(Wrappers.<BaseSysRoleMenuEntity>lambdaQuery()
                    .eq(!id.equals(1L), BaseSysRoleMenuEntity::getRoleId, id));
            menuIdList = list.stream().map(BaseSysRoleMenuEntity::getMenuId).toArray(Long[]::new);

            List<BaseSysRoleDepartmentEntity> departmentEntities = baseSysRoleDepartmentMapper.selectList(Wrappers.<BaseSysRoleDepartmentEntity>lambdaQuery()
                    .eq(!id.equals(1L), BaseSysRoleDepartmentEntity::getRoleId, id));

            departmentIdList = departmentEntities.stream().map(BaseSysRoleDepartmentEntity::getDepartmentId).toArray(Long[]::new);
        }
        return Dict.parse(roleEntity)
                .set("menuIdList", menuIdList)
                .set("departmentIdList", departmentIdList);
    }

    @Override
    public void modifyAfter(JSONObject requestParams, BaseSysRoleEntity baseSysRoleEntity, ModifyEnum type) {
        if (type == ModifyEnum.DELETE) {
            Long[] ids = requestParams.get("ids", Long[].class);
            for (Long id : ids) {
                baseSysPermsService.refreshPermsByRoleId(id);
            }
        } else {
            baseSysPermsService.updatePerms(baseSysRoleEntity.getId(), requestParams.get("menuIdList", Long[].class), requestParams.get("departmentIdList", Long[].class));
        }
    }

    @Override
    public Object list(JSONObject requestParams, QueryWrapper<BaseSysRoleEntity> queryWrapper) {
        return baseSysRoleMapper.selectList(Wrappers.<BaseSysRoleEntity>lambdaQuery()
                .ne(BaseEntity::getId, 1L)
                .and(!coolSecurityUtil.username().equals("admin"), qw -> qw
                        .or().eq(BaseSysRoleEntity::getUserId, coolSecurityUtil.userInfo(requestParams).get("userId"))
                        .or().in(BaseEntity::getId, coolSecurityUtil.userInfo(requestParams).get("roleIds", Long[].class)))
        );
    }
}
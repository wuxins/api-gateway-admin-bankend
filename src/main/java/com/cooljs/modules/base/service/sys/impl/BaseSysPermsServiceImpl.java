package com.cooljs.modules.base.service.sys.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cooljs.core.cache.CoolCache;
import com.cooljs.modules.base.entity.sys.*;
import com.cooljs.modules.base.mapper.sys.*;
import com.cooljs.modules.base.security.CoolSecurityUtil;
import com.cooljs.modules.base.service.sys.BaseSysPermsService;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BaseSysPermsServiceImpl implements BaseSysPermsService {
    @Resource
    private CoolCache coolCache;

    @Resource
    private BaseSysUserMapper baseSysUserMapper;

    @Resource
    private BaseSysUserRoleMapper baseSysUserRoleMapper;

    @Resource
    private BaseSysMenuMapper baseSysMenuMapper;

    @Resource
    private BaseSysRoleMenuMapper baseSysRoleMenuMapper;

    @Resource
    private BaseSysRoleDepartmentMapper baseSysRoleDepartmentMapper;

    @Resource
    @Lazy
    private UserDetailsService userDetailsService;

    @Resource
    private BaseSysDepartmentMapper baseSysDepartmentMapper;

    @Resource
    private CoolSecurityUtil coolSecurityUtil;

    @Override
    public Long[] loginDepartmentIds() {
        String username = coolSecurityUtil.username();
        if (username.equals("admin")) {
            return baseSysDepartmentMapper.selectList(null).stream().map(BaseSysDepartmentEntity::getId).toArray(Long[]::new);
        } else {
            Long[] roleIds = getRoles(username);
            return baseSysRoleDepartmentMapper.selectList(Wrappers.<BaseSysRoleDepartmentEntity>lambdaQuery()
                            .in(BaseSysRoleDepartmentEntity::getRoleId, roleIds))
                    .stream().map(BaseSysRoleDepartmentEntity::getDepartmentId).toArray(Long[]::new);
        }
    }

    @Override
    public Long[] getDepartmentIdsByRoleIds(Long[] roleIds) {
        return getLongs(roleIds);
    }

    private Long[] getLongs(Long[] roleIds) {
        return baseSysRoleDepartmentMapper.selectList(Wrappers.<BaseSysRoleDepartmentEntity>lambdaQuery()
                        .in(roleIds != null && !CollUtil.toList(roleIds).contains(1L), BaseSysRoleDepartmentEntity::getRoleId, roleIds))
                .stream().map(BaseSysRoleDepartmentEntity::getDepartmentId).toArray(Long[]::new);
    }

    @Override
    public Long[] getDepartmentIdsByRoleIds(Long userId) {
        Long[] roleIds = getRoles(userId);
        return getLongs(roleIds);
    }

    @Override
    public String[] getPermsCache(Long userId) {
        Object result = coolCache.get("admin:perms:" + userId);
        if (ObjectUtil.isNotEmpty(result)) {
            return Convert.toStrArray(result);
        }
        return getPerms(userId);
    }

    @Override
    public Long[] getRoles(Long userId) {
        return getRoles(baseSysUserMapper.selectById(userId));
    }

    @Override
    public Long[] getRoles(String username) {
        return getRoles(baseSysUserMapper.selectOne(Wrappers.<BaseSysUserEntity>lambdaQuery().eq(BaseSysUserEntity::getUsername, username)));
    }

    @Override
    public Long[] getRoles(BaseSysUserEntity userEntity) {
        Long[] roleIds = null;
        if (!userEntity.getUsername().equals("admin")) {
            List<BaseSysUserRoleEntity> list = baseSysUserRoleMapper.selectList(Wrappers.<BaseSysUserRoleEntity>lambdaQuery().eq(BaseSysUserRoleEntity::getUserId, userEntity.getId()));
            roleIds = list.stream().map(BaseSysUserRoleEntity::getRoleId).toArray(Long[]::new);
            if (Arrays.asList(roleIds).contains(1L)) {
                roleIds = null;
            }
        }
        return roleIds;
    }

    @Override
    public String[] getPerms(Long userId) {
        return getPerms(getRoles(userId));
    }

    @Override
    public String[] getPerms(Long[] roleIds) {
        List<BaseSysMenuEntity> menus = getMenus(roleIds);
        Set<String> perms = new HashSet<>();
        String[] permsData = menus.stream()
                .map(BaseSysMenuEntity::getPerms)
                .filter(itemPerms -> !StrUtil.isEmpty(itemPerms)).toArray(String[]::new);
        for (String permData : permsData) {
            perms.addAll(Arrays.asList(permData.split(",")));
        }
        return ArrayUtil.toArray(perms, String.class);
    }

    @Override
    public List<BaseSysMenuEntity> getMenus(Long[] roleIds) {
        if (CollUtil.toList(roleIds).contains(1L)) {
            roleIds = null;
        }
        return baseSysMenuMapper.getMenus(roleIds);
    }

    @Override
    public List<BaseSysMenuEntity> getMenus(Long userId) {
        return getMenus(getRoles(userId));
    }

    @Override
    public List<BaseSysMenuEntity> getMenus(String username) {
        BaseSysUserEntity sysUserEntity = baseSysUserMapper.selectOne(Wrappers.<BaseSysUserEntity>lambdaQuery().eq(BaseSysUserEntity::getUsername, username));
        return getMenus(sysUserEntity.getId());
    }


    @Override
    public String[] getAllPerms() {
        return getPerms((Long[]) null);
    }

    @Override
    public Object permmenu(Long adminUserId) {
        return Dict.create()
                .set("menus", getMenus(adminUserId))
                .set("perms", getPerms(adminUserId));
    }

    @Override
    public void updatePerms(Long roleId, Long[] menuIdList, Long[] departmentIds) {
        // 更新菜单权限
        baseSysRoleMenuMapper.delete(Wrappers.<BaseSysRoleMenuEntity>lambdaQuery().eq(BaseSysRoleMenuEntity::getRoleId, roleId));
        for (Long menuId : menuIdList) {
            BaseSysRoleMenuEntity roleMenuEntity = new BaseSysRoleMenuEntity();
            roleMenuEntity.setRoleId(roleId);
            roleMenuEntity.setMenuId(menuId);
            baseSysRoleMenuMapper.insert(roleMenuEntity);
        }
        // 更新部门权限
        baseSysRoleDepartmentMapper.delete(Wrappers.<BaseSysRoleDepartmentEntity>lambdaQuery().eq(BaseSysRoleDepartmentEntity::getRoleId, roleId));
        for (Long departmentId : departmentIds) {
            BaseSysRoleDepartmentEntity roleDepartmentEntity = new BaseSysRoleDepartmentEntity();
            roleDepartmentEntity.setRoleId(roleId);
            roleDepartmentEntity.setDepartmentId(departmentId);
            baseSysRoleDepartmentMapper.insert(roleDepartmentEntity);
        }
        // 刷新对应角色用户的权限
        List<BaseSysUserRoleEntity> userRoles = baseSysUserRoleMapper.selectList(Wrappers.<BaseSysUserRoleEntity>lambdaQuery().eq(BaseSysUserRoleEntity::getRoleId, roleId));
        for (BaseSysUserRoleEntity userRole : userRoles) {
            refreshPerms(userRole.getUserId());
        }
    }

    @Override
    public void updateUserRole(Long userId, Long[] roleIdList) {
        baseSysUserRoleMapper.delete(Wrappers.<BaseSysUserRoleEntity>lambdaQuery().eq(BaseSysUserRoleEntity::getUserId, userId));
        for (Long roleId : roleIdList) {
            BaseSysUserRoleEntity sysUserRoleEntity = new BaseSysUserRoleEntity();
            sysUserRoleEntity.setRoleId(roleId);
            sysUserRoleEntity.setUserId(userId);
            baseSysUserRoleMapper.insert(sysUserRoleEntity);
        }
        refreshPerms(userId);
    }

    @Override
    public void refreshPerms(Long userId) {
        BaseSysUserEntity baseSysUserEntity = baseSysUserMapper.selectById(userId);
        if (baseSysUserEntity != null && baseSysUserEntity.getStatus() != 0) {
            userDetailsService.loadUserByUsername(baseSysUserEntity.getUsername());
        }
        if (baseSysUserEntity != null && baseSysUserEntity.getStatus() == 0) {
            coolSecurityUtil.logout(baseSysUserEntity.getUserId(), baseSysUserEntity.getUsername());
        }
    }

    @Async
    @Override
    public void refreshPermsByMenuId(Long menuId) {
        // 刷新超管权限、 找出这个菜单的所有用户、 刷新用户权限
        BaseSysUserEntity admin = baseSysUserMapper.selectOne(Wrappers.<BaseSysUserEntity>lambdaQuery().eq(BaseSysUserEntity::getUsername, "admin"));
        refreshPerms(admin.getId());

        Long[] userIds = baseSysRoleMenuMapper.userIds(menuId);
        for (Long userId : userIds) {
            refreshPerms(userId);
        }
    }

    @Override
    public void refreshPermsByRoleId(Long roleId) {
        // 找出角色对应的所有用户
        List<BaseSysUserRoleEntity> list = baseSysUserRoleMapper.selectList(Wrappers.<BaseSysUserRoleEntity>lambdaQuery().eq(BaseSysUserRoleEntity::getRoleId, roleId));
        list.forEach(e -> {
            refreshPerms(e.getUserId());
        });
    }
}

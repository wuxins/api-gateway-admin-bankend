package com.cooljs.modules.base.service.sys.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cooljs.core.base.BaseServiceImpl;
import com.cooljs.core.base.ModifyEnum;
import com.cooljs.core.cache.CoolCache;
import com.cooljs.core.exception.CoolException;
import com.cooljs.modules.base.entity.sys.BaseSysDepartmentEntity;
import com.cooljs.modules.base.entity.sys.BaseSysUserEntity;
import com.cooljs.modules.base.mapper.sys.BaseSysDepartmentMapper;
import com.cooljs.modules.base.mapper.sys.BaseSysUserMapper;
import com.cooljs.modules.base.security.CoolSecurityUtil;
import com.cooljs.modules.base.service.sys.BaseSysPermsService;
import com.cooljs.modules.base.service.sys.BaseSysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统用户
 */
@Service
public class BaseSysUserServiceImpl extends BaseServiceImpl<BaseSysUserMapper, BaseSysUserEntity> implements BaseSysUserService {
    @Resource
    private CoolCache coolCache;

    @Resource
    private CoolSecurityUtil coolSecurityUtil;

    @Resource
    private BaseSysPermsService baseSysPermsService;

    @Resource
    private BaseSysDepartmentMapper baseSysDepartmentMapper;

    @Override
    public Object page(JSONObject requestParams, Page<BaseSysUserEntity> page, QueryWrapper<BaseSysUserEntity> qw) {
        String keyWord = requestParams.getStr("keyWord");
        Integer status = requestParams.getInt("status");
        Long[] departmentIds = requestParams.get("departmentIds", Long[].class);
        JSONObject tokenInfo = coolSecurityUtil.userInfo(requestParams);
        // 用户的部门权限
        Long[] permsDepartmentArr = coolCache.get("admin:department:" + tokenInfo.get("userId"), Long[].class);
        // 不显示admin用户
        qw.ne("a.username", "admin");
        // 过滤用户
        qw.eq(!coolSecurityUtil.username().equals("admin"), "a.userId", tokenInfo.get("userId"));
        // 筛选部门
        qw.in(ArrayUtil.isNotEmpty(departmentIds), "a.departmentId", departmentIds);
        // 筛选状态
        qw.eq(status != null, "a.status", status);
        // 搜索关键字
        qw.apply(StrUtil.isNotEmpty(keyWord), "(a.name LIKE {0} or a.username LIKE {0})", "%" + keyWord + "%");
        // 过滤部门权限
        qw.in(!coolSecurityUtil.username().equals("admin"), "a.departmentId", permsDepartmentArr == null || permsDepartmentArr.length == 0 ? new Long[]{null} : permsDepartmentArr);
        qw.groupBy("a.id");
        return baseMapper.page(page, qw);
    }

    @Override
    public void personUpdate(Long userId, Dict body) {
        BaseSysUserEntity userEntity = getById(userId);
        if (ObjectUtil.isEmpty(userEntity)) {
            throw new CoolException("用户不存在");
        }
        userEntity.setNickName(body.getStr("nickName"));
        // 修改密码
        if (StrUtil.isNotEmpty(body.getStr("password"))) {
            userEntity.setPassword(MD5.create().digestHex(body.getStr("password")));
            userEntity.setPasswordV(userEntity.getPasswordV() + 1);
            coolCache.set("admin:passwordVersion:" + userId, userEntity.getPasswordV());
        }
        updateById(userEntity);
    }

    @Override
    public void move(Long departmentId, Long[] userIds) {
        update(Wrappers.<BaseSysUserEntity>lambdaUpdate().set(BaseSysUserEntity::getDepartmentId, departmentId).in(BaseSysUserEntity::getId, userIds));
    }

    @Override
    public Long add(JSONObject requestParams, BaseSysUserEntity entity) {
        BaseSysUserEntity check = getOne(Wrappers.<BaseSysUserEntity>lambdaQuery().eq(BaseSysUserEntity::getUsername, entity.getUsername()));
        if (check != null) {
            throw new CoolException("用户名已存在");
        }
        entity.setUserId(coolSecurityUtil.userInfo(requestParams).getLong("userId"));
        entity.setPassword(MD5.create().digestHex(entity.getPassword()));
        super.add(requestParams, entity);
        return entity.getId();
    }

    @Override
    public void update(JSONObject requestParams, BaseSysUserEntity entity) {
        if (entity.getUsername().equals("admin")) {
            throw new CoolException("非法操作");
        }
        BaseSysUserEntity userEntity = getById(entity.getId());
        if (StrUtil.isNotEmpty(entity.getPassword())) {
            entity.setPasswordV(entity.getPasswordV() + 1);
            entity.setPassword(MD5.create().digestHex(entity.getPassword()));
            coolCache.set("admin:passwordVersion:" + entity.getId(), entity.getPasswordV());
        } else {
            entity.setPassword(userEntity.getPassword());
            entity.setPasswordV(userEntity.getPasswordV());
        }
        // 被禁用
        if (entity.getStatus() == 0) {
            coolSecurityUtil.logout(entity);
        }
        super.update(requestParams, entity);
    }

    @Override
    public void modifyAfter(JSONObject requestParams, BaseSysUserEntity baseSysUserEntity, ModifyEnum type) {
        if (type != ModifyEnum.DELETE) {
            // 刷新权限
            baseSysPermsService.updateUserRole(baseSysUserEntity.getId(), requestParams.get("roleIdList", Long[].class));
        }
    }

    @Override
    public Object info(Long id) {
        BaseSysUserEntity userEntity = getById(id);
        Long[] roleIdList = baseSysPermsService.getRoles(id);
        BaseSysDepartmentEntity departmentEntity = baseSysDepartmentMapper.selectById(userEntity.getDepartmentId());
        userEntity.setPassword(null);
        return Dict.parse(userEntity)
                .set("roleIdList", roleIdList)
                .set("departmentName", departmentEntity != null ? departmentEntity.getName() : null);
    }
}
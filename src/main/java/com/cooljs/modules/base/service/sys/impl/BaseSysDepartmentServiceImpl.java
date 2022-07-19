package com.cooljs.modules.base.service.sys.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cooljs.core.base.BaseEntity;
import com.cooljs.core.base.BaseServiceImpl;
import com.cooljs.modules.base.entity.sys.BaseSysDepartmentEntity;
import com.cooljs.modules.base.entity.sys.BaseSysUserEntity;
import com.cooljs.modules.base.mapper.sys.BaseSysDepartmentMapper;
import com.cooljs.modules.base.mapper.sys.BaseSysRoleDepartmentMapper;
import com.cooljs.modules.base.mapper.sys.BaseSysUserMapper;
import com.cooljs.modules.base.security.CoolSecurityUtil;
import com.cooljs.modules.base.service.sys.BaseSysDepartmentService;
import com.cooljs.modules.base.service.sys.BaseSysPermsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统部门
 */
@Service
public class BaseSysDepartmentServiceImpl extends BaseServiceImpl<BaseSysDepartmentMapper, BaseSysDepartmentEntity> implements BaseSysDepartmentService {
    @Resource
    private BaseSysUserMapper baseSysUserMapper;

    @Resource
    private CoolSecurityUtil coolSecurityUtil;

    @Resource
    private BaseSysPermsService baseSysPermsService;

    @Resource
    private BaseSysRoleDepartmentMapper baseSysRoleDepartmentMapper;

    @Override
    public Object list(QueryWrapper<BaseSysDepartmentEntity> queryWrapper) {
        return super.list(queryWrapper);
    }

    @Override
    public void order(List<BaseSysDepartmentEntity> list) {
        list.forEach(baseSysDepartmentEntity ->
                this.lambdaUpdate()
                        .eq(BaseEntity::getId, baseSysDepartmentEntity.getId())
                        .set(BaseSysDepartmentEntity::getOrderNum, baseSysDepartmentEntity.getOrderNum())
                        .update()
        );
    }


    @Override
    public List<BaseSysDepartmentEntity> list(JSONObject requestParams, QueryWrapper<BaseSysDepartmentEntity> queryWrapper) {
        String username = coolSecurityUtil.username();
        Long[] loginDepartmentIds = baseSysPermsService.loginDepartmentIds();
        if (loginDepartmentIds != null && loginDepartmentIds.length == 0) {
            return new ArrayList<>();
        }
        List<BaseSysDepartmentEntity> list = this.list(Wrappers.<BaseSysDepartmentEntity>lambdaQuery()
                .in(!username.equals("admin"), BaseSysDepartmentEntity::getId, loginDepartmentIds)
                .orderByDesc(BaseSysDepartmentEntity::getOrderNum));
        list.forEach(e -> {
            List<BaseSysDepartmentEntity> parentDepartment = list.stream().filter(sysDepartmentEntity ->
                    e.getParentId() != null && e.getParentId().equals(sysDepartmentEntity.getId())).collect(Collectors.toList());
            if (!parentDepartment.isEmpty()) {
                e.setParentName(parentDepartment.get(0).getName());
            }
        });
        return list;
    }

    @Override
    public void delete(JSONObject requestParams, Long... ids) {
        super.delete(ids);
        // 是否删除对应用户 否则移动到顶层部门
        if (requestParams.getBool("deleteUser")) {
            baseSysUserMapper.delete(Wrappers.<BaseSysUserEntity>lambdaQuery()
                    .in(BaseSysUserEntity::getDepartmentId, ids));
        } else {
            BaseSysDepartmentEntity topDepartment = getOne(Wrappers.<BaseSysDepartmentEntity>lambdaQuery()
                    .isNull(BaseSysDepartmentEntity::getParentId));
            if (topDepartment != null) {
                LambdaUpdateWrapper<BaseSysUserEntity> wrapper = new LambdaUpdateWrapper<>();
                wrapper.set(BaseSysUserEntity::getDepartmentId, topDepartment.getId());
                wrapper.in(BaseSysUserEntity::getDepartmentId, ids);
                baseSysUserMapper.update(null, wrapper);
            }
        }
    }
}
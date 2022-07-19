package com.cooljs.modules.base.entity.sys;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * 系统用户角色表
 */
@Data
@CoolTable(value = "base_sys_user_role", comment = "系统用户角色表")
public class BaseSysUserRoleEntity extends BaseEntity<BaseSysUserRoleEntity> {
    @Column(comment = "用户ID", type = MySqlTypeConstant.BIGINT)
    private Long userId;

    @Column(comment = "角色ID", type = MySqlTypeConstant.BIGINT)
    private Long roleId;
}

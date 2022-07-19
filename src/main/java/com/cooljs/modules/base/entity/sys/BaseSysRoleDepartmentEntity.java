package com.cooljs.modules.base.entity.sys;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * 系统角色部门
 */
@Data
@CoolTable(value = "base_sys_role_department", comment = "系统角色部门")
public class BaseSysRoleDepartmentEntity extends BaseEntity<BaseSysRoleDepartmentEntity> {

    @Column(comment = "角色ID", type = MySqlTypeConstant.BIGINT)
    private Long roleId;

    @Column(comment = "部门ID", type = MySqlTypeConstant.BIGINT)
    private Long departmentId;
}

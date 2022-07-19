package com.cooljs.modules.base.entity.sys;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * 系统角色菜单表
 */
@Data
@CoolTable(value = "base_sys_role_menu", comment = "系统角色菜单表")
public class BaseSysRoleMenuEntity extends BaseEntity<BaseSysRoleMenuEntity> {
    @Column(comment = "菜单", type = MySqlTypeConstant.BIGINT)
    private Long menuId;

    @Column(comment = "角色ID", type = MySqlTypeConstant.BIGINT)
    private Long roleId;
}

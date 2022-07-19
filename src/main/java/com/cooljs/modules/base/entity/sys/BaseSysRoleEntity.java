package com.cooljs.modules.base.entity.sys;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.Index;
import com.tangzc.mpe.actable.annotation.Unique;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import lombok.Data;

@Data
@CoolTable(value = "base_sys_role", comment = "系统角色表")
public class BaseSysRoleEntity extends BaseEntity<BaseSysRoleEntity> {

    @Index
    @Column(comment = "用户ID", notNull = true, type = MySqlTypeConstant.BIGINT)
    private Long userId;

    @Column(comment = "名称", notNull = true)
    private String name;

    @Unique
    @Column(comment = "角色标签", notNull = true)
    private String label;

    @Column(comment = "备注")
    private String remark;

    @Column(comment = "数据权限是否关联上下级", defaultValue = "1")
    private Integer relevance;

}

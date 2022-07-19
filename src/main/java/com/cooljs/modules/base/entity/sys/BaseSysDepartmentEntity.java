package com.cooljs.modules.base.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * 系统部门
 */
@Data
@CoolTable(value = "base_sys_department", comment = "系统部门")
public class BaseSysDepartmentEntity extends BaseEntity<BaseSysDepartmentEntity> {
    @Column(comment = "部门名称", notNull = true)
    private String name;

    @Column(comment = "上级部门ID", type = MySqlTypeConstant.BIGINT)
    private Long parentId;

    @Column(comment = "排序", defaultValue = "0")
    private Integer orderNum;

    // 父菜单名称
    @TableField(exist = false)
    private String parentName;
}

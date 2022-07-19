package com.cooljs.modules.base.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.Index;
import com.tangzc.mpe.actable.annotation.Unique;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * 系统用户表
 */
@Data
@CoolTable(value = "base_sys_user", comment = "系统用户表")
public class BaseSysUserEntity extends BaseEntity<BaseSysUserEntity> {
    @Index
    @Column(comment = "部门ID", type = MySqlTypeConstant.BIGINT)
    private Long departmentId;

    @Column(comment = "创建者", type = MySqlTypeConstant.BIGINT, notNull = true)
    private Long userId;

    @Column(comment = "姓名")
    private String name;

    @Unique
    @Column(comment = "用户名", length = 100, notNull = true)
    private String username;

    @Column(comment = "密码", notNull = true)
    private String password;

    @Column(comment = "密码版本", defaultValue = "1")
    private Integer passwordV;

    @Column(comment = "昵称", notNull = true)
    private String nickName;

    @Column(comment = "头像")
    private String headImg;

    @Column(comment = "手机号")
    private String phone;

    @Column(comment = "邮箱")
    private String email;

    @Column(comment = "备注")
    private String remark;

    @Column(comment = "状态 0:禁用 1：启用", defaultValue = "1", type = MySqlTypeConstant.TINYINT)
    private Integer status;

    // 部门名称
    @TableField(exist = false)
    private String departmentName;

    // 角色名称
    @TableField(exist = false)
    private String roleName;
}

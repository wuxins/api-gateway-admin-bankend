package com.cooljs.modules.demo.entity;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.Index;
import com.tangzc.mpe.actable.annotation.Unique;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import lombok.Data;

import java.util.Date;

/**
 * crud演示
 */
@Data
@CoolTable(value = "demo_crud", comment = "crud演示")
public class DemoCrudEntity extends BaseEntity<DemoCrudEntity> {

    @Index
    @Column(comment = "头像", notNull = true)
    private String headImg;

    @Index
    @Column(comment = "名字", notNull = true)
    private String name;

    @Column(comment = "年龄", defaultValue = "18")
    private Integer age;

    @Column(comment = "生日")
    private Date birthDate;

    @Unique
    @Column(comment = "手机号", notNull = true)
    private String phone;

    @Column(comment = "状态", defaultValue = "true")
    private Boolean status;

    @Column(comment = "介绍", type = MySqlTypeConstant.TEXT)
    private String introduce;
}

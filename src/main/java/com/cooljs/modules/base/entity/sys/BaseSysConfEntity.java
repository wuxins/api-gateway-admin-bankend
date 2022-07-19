package com.cooljs.modules.base.entity.sys;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.Unique;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * 系统配置
 */
@Data
@CoolTable(value = "base_sys_conf", comment = "系统配置表")
public class BaseSysConfEntity extends BaseEntity<BaseSysConfEntity> {
    @Unique
    @Column(comment = "配置键", notNull = true)
    private String cKey;

    @Column(comment = "值", notNull = true, type = MySqlTypeConstant.TEXT)
    private String cValue;
}

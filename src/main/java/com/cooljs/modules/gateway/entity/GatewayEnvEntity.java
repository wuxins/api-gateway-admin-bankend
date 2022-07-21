package com.cooljs.modules.gateway.entity;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.modules.gateway.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import lombok.Data;

/**
 * 环境
 */
@Data
@CoolTable(value = "env", comment = "环境")
public class GatewayEnvEntity extends BaseEntity<GatewayEnvEntity> {

    @Column(comment = "环境名称", notNull = true, length = 64)
    private String name;

    @Column(comment = "环境编码", notNull = true, value = "env", length = 16)
    private String env;
}

package com.cooljs.modules.gateway.entity;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.modules.gateway.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import lombok.Data;

/**
 * API租户
 */
@Data
@CoolTable(value = "api_tenant", comment = "API租户")
public class GatewayApiTenantEntity extends BaseEntity<GatewayApiTenantEntity> {

    @Column(comment = "API编码", notNull = true, value = "api_code", length = 16)
    private String apiCode;

    @Column(comment = "租户编码", notNull = true, value = "tenant_code", length = 16)
    private String tenantCode;
}

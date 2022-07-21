package com.cooljs.modules.gateway.entity;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.modules.gateway.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * 租户
 */
@Data
@CoolTable(value = "tenant", comment = "租户")
public class GatewayTenantEntity extends BaseEntity<GatewayTenantEntity> {

    @Column(comment = "租户名称", notNull = true, length = 64)
    private String name;

    @Column(comment = "租户编码", notNull = true, value = "tenant_code", length = 16)
    private String tenantCode;

    @Column(comment = "租户是否需要鉴权", notNull = true, value = "need_api_auth", length = 1, defaultValue = "N", type = MySqlTypeConstant.CHAR)
    private String needApiAuth;

    @Column(comment = "租户鉴权类型", value = "api_auth_type", length = 1, type = MySqlTypeConstant.CHAR)
    private String apiAuthType;
}

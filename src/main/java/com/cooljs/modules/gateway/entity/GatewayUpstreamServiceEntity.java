package com.cooljs.modules.gateway.entity;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.modules.gateway.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import lombok.Data;

/**
 * 上游服务
 */
@Data
@CoolTable(value = "upstream_service", comment = "上游服务")
public class GatewayUpstreamServiceEntity extends BaseEntity<GatewayUpstreamServiceEntity> {

    @Column(comment = "上游服务名称", notNull = true)
    private String name;

    @Column(comment = "上游服务编码", notNull = true, value = "service_code")
    private String serviceCode;

    @Column(comment = "上游服务地址", notNull = true, value = "host")
    private String host;
}

package com.cooljs.modules.gateway.entity;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.modules.gateway.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.annotation.Exclude;
import lombok.Data;

import java.util.List;

/**
 * Api定义
 */
@Data
@CoolTable(value = "api", comment = "Api定义")
public class GatewayApiEntity extends BaseEntity<GatewayApiEntity> {

    @Column(comment = "Api名称", notNull = true, length = 64)
    private String name;

    @Column(comment = "Api编码", notNull = true, value = "api_code", length = 16)
    private String apiCode;

    @Column(comment = "Api请求方法", notNull = true, value = "method", length = 8)
    private String method;

    @Column(comment = "Api上游服务编码", notNull = true, value = "service_code", length = 16)
    private String serviceCode;

    @Column(comment = "Api请求URI", notNull = true, value = "src_url")
    private String srcUrl;

    @Column(comment = "Api目标URI", notNull = true, value = "des_url")
    private String desUrl;

    @Column(comment = "Api维护者", notNull = true, value = "maintainer", length = 64, defaultValue = "system")
    private String maintainer;

    @Column(comment = "Api描述", notNull = true, value = "description", length = 128)
    private String description;

    @Exclude
    private List<String> tenants;

    @Exclude
    private List<GatewayApiVersionEntity> versions;
}

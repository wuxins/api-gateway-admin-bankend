package com.cooljs.modules.gateway.entity;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.modules.gateway.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * Api版本信息
 */
@Data
@CoolTable(value = "api_version", comment = "Api版本信息")
public class GatewayApiVersionEntity extends BaseEntity<GatewayApiVersionEntity> {

    @Column(comment = "Api编码", notNull = true, value = "api_code", length = 16)
    private String apiCode;

    @Column(comment = "环境编码", notNull = true, value = "env", length = 8)
    private String env;

    @Column(comment = "是否需要限流", notNull = true, value = "need_rate_limit", length = 1, defaultValue = "N", type = MySqlTypeConstant.CHAR)
    private String needRateLimit;

    @Column(comment = "限流大小", value = "rate_limit")
    private int rateLimit;

    @Column(comment = "是否需要降级", notNull = true, value = "need_fallback", length = 1, defaultValue = "N", type = MySqlTypeConstant.CHAR)
    private String needFallback;

    @Column(comment = "降级内容", value = "fallback")
    private String fallback;

    @Column(comment = "是否开启日志监控", notNull = true, value = "need_monitor", length = 1, defaultValue = "N", type = MySqlTypeConstant.CHAR)
    private String needMonitor;

    @Column(comment = "是否开启日志监控", value = "read_timeout")
    private int readTimeout;
}

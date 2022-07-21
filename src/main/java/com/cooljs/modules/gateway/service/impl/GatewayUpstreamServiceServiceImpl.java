package com.cooljs.modules.gateway.service.impl;

import com.cooljs.modules.gateway.base.BaseServiceImpl;
import com.cooljs.modules.gateway.entity.GatewayUpstreamServiceEntity;
import com.cooljs.modules.gateway.mapper.GatewayUpstreamServiceMapper;
import com.cooljs.modules.gateway.service.GatewayUpstreamServiceService;
import org.springframework.stereotype.Service;

/**
 * 上游服务信息
 */
@Service
public class GatewayUpstreamServiceServiceImpl extends BaseServiceImpl<GatewayUpstreamServiceMapper, GatewayUpstreamServiceEntity> implements GatewayUpstreamServiceService {
}
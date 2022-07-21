package com.cooljs.modules.gateway.service.impl;

import com.cooljs.modules.gateway.base.BaseServiceImpl;
import com.cooljs.modules.gateway.entity.GatewayApiTenantEntity;
import com.cooljs.modules.gateway.mapper.GatewayApiTenantMapper;
import com.cooljs.modules.gateway.service.GatewayApiTenantService;
import org.springframework.stereotype.Service;

/**
 * API租户
 */
@Service
public class GatewayApiTenantServiceImpl extends BaseServiceImpl<GatewayApiTenantMapper, GatewayApiTenantEntity> implements GatewayApiTenantService {
}
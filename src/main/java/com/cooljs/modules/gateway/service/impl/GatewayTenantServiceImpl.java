package com.cooljs.modules.gateway.service.impl;

import com.cooljs.modules.gateway.base.BaseServiceImpl;
import com.cooljs.modules.gateway.entity.GatewayTenantEntity;
import com.cooljs.modules.gateway.mapper.GatewayTenantMapper;
import com.cooljs.modules.gateway.service.GatewayTenantService;
import org.springframework.stereotype.Service;

/**
 * 租户信息
 */
@Service
public class GatewayTenantServiceImpl extends BaseServiceImpl<GatewayTenantMapper, GatewayTenantEntity> implements GatewayTenantService {
}
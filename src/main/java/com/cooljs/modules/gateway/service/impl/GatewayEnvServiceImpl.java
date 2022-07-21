package com.cooljs.modules.gateway.service.impl;

import com.cooljs.modules.gateway.base.BaseServiceImpl;
import com.cooljs.modules.gateway.entity.GatewayEnvEntity;
import com.cooljs.modules.gateway.mapper.GatewayEnvMapper;
import com.cooljs.modules.gateway.service.GatewayEnvService;
import org.springframework.stereotype.Service;

/**
 * 环境信息
 */
@Service
public class GatewayEnvServiceImpl extends BaseServiceImpl<GatewayEnvMapper, GatewayEnvEntity> implements GatewayEnvService {
}
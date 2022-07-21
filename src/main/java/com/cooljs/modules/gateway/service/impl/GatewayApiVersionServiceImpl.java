package com.cooljs.modules.gateway.service.impl;

import com.cooljs.modules.gateway.base.BaseServiceImpl;
import com.cooljs.modules.gateway.entity.GatewayApiVersionEntity;
import com.cooljs.modules.gateway.mapper.GatewayApiVersionMapper;
import com.cooljs.modules.gateway.service.GatewayApiVersionService;
import org.springframework.stereotype.Service;

/**
 * API版本信息
 */
@Service
public class GatewayApiVersionServiceImpl extends BaseServiceImpl<GatewayApiVersionMapper, GatewayApiVersionEntity> implements GatewayApiVersionService {
}
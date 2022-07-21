package com.cooljs.modules.gateway.service.impl;

import com.cooljs.modules.gateway.base.BaseServiceImpl;
import com.cooljs.modules.gateway.entity.GatewayGroupEntity;
import com.cooljs.modules.gateway.mapper.GatewayGroupMapper;
import com.cooljs.modules.gateway.service.GatewayGroupService;
import org.springframework.stereotype.Service;

/**
 * 分组信息
 */
@Service
public class GatewayGroupServiceImpl extends BaseServiceImpl<GatewayGroupMapper, GatewayGroupEntity> implements GatewayGroupService {
}
package com.cooljs.modules.gateway.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cooljs.modules.gateway.base.BaseServiceImpl;
import com.cooljs.modules.gateway.entity.GatewayApiEntity;
import com.cooljs.modules.gateway.entity.GatewayApiTenantEntity;
import com.cooljs.modules.gateway.entity.GatewayApiVersionEntity;
import com.cooljs.modules.gateway.mapper.GatewayApiMapper;
import com.cooljs.modules.gateway.service.GatewayApiService;
import com.cooljs.modules.gateway.service.GatewayApiTenantService;
import com.cooljs.modules.gateway.service.GatewayApiVersionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * API定义
 */
@Service
public class GatewayApiServiceImpl extends BaseServiceImpl<GatewayApiMapper, GatewayApiEntity> implements GatewayApiService {

    @Resource
    private GatewayApiTenantService gatewayApiTenantService;

    @Resource
    private GatewayApiVersionService gatewayApiVersionService;

    @Override
    public Object page(JSONObject requestParams, Page<GatewayApiEntity> page, QueryWrapper<GatewayApiEntity> queryWrapper) {
        //gatewayApiTenantService.query().eq("api_code",queryWrapper.)
        Object result = super.page(requestParams, page, queryWrapper);
        IPage<GatewayApiEntity> page1 = (IPage<GatewayApiEntity>) result;
        page1.getRecords().forEach(i -> {
            QueryWrapper<GatewayApiTenantEntity> cond = new QueryWrapper<>();
            cond.eq("api_code", i.getApiCode());
            List<GatewayApiTenantEntity> apiTenantEntities = (List<GatewayApiTenantEntity>) gatewayApiTenantService.list(cond);
            QueryWrapper<GatewayApiVersionEntity> cond1 = new QueryWrapper<>();
            cond1.eq("api_code", i.getApiCode());
            List<GatewayApiVersionEntity> apiVersionEntities = (List<GatewayApiVersionEntity>) gatewayApiVersionService.list(cond1);
            i.setTenants(apiTenantEntities.stream().map(GatewayApiTenantEntity::getTenantCode).collect(Collectors.toList()));
            i.setVersions(apiVersionEntities);
        });
        return result;
    }
}
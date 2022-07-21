package com.cooljs.modules.gateway.controller.admin;

import cn.hutool.json.JSONObject;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.modules.gateway.base.BaseController;
import com.cooljs.modules.gateway.entity.GatewayApiTenantEntity;
import com.cooljs.modules.gateway.service.GatewayApiTenantService;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletRequest;

/**
 * API租户
 */
@Tag(name = "API租户", description = "API租户")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminGatewayApiTenantController extends BaseController<GatewayApiTenantService, GatewayApiTenantEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }
}
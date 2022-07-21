package com.cooljs.modules.gateway.controller.admin;

import cn.hutool.json.JSONObject;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.modules.gateway.base.BaseController;
import com.cooljs.modules.gateway.entity.GatewayTenantEntity;
import com.cooljs.modules.gateway.service.GatewayTenantService;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletRequest;

/**
 * 租户信息
 */
@Tag(name = "租户信息", description = "租户信息")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminGatewayTenantController extends BaseController<GatewayTenantService, GatewayTenantEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }
}
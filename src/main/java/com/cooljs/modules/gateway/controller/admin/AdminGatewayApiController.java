package com.cooljs.modules.gateway.controller.admin;

import cn.hutool.json.JSONObject;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.modules.gateway.base.BaseController;
import com.cooljs.modules.gateway.entity.GatewayApiEntity;
import com.cooljs.modules.gateway.service.GatewayApiService;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletRequest;

/**
 * API定义
 */
@Tag(name = "API定义", description = "API定义")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminGatewayApiController extends BaseController<GatewayApiService, GatewayApiEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }
}
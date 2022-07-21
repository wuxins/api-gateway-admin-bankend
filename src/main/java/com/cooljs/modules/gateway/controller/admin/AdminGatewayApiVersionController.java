package com.cooljs.modules.gateway.controller.admin;

import cn.hutool.json.JSONObject;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.modules.gateway.base.BaseController;
import com.cooljs.modules.gateway.entity.GatewayApiVersionEntity;
import com.cooljs.modules.gateway.service.GatewayApiVersionService;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletRequest;

/**
 * API版本信息
 */
@Tag(name = "API版本信息", description = "API版本信息")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminGatewayApiVersionController extends BaseController<GatewayApiVersionService, GatewayApiVersionEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }
}
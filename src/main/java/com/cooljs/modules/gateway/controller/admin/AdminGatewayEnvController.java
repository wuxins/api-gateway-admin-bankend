package com.cooljs.modules.gateway.controller.admin;

import cn.hutool.json.JSONObject;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.modules.gateway.base.BaseController;
import com.cooljs.modules.gateway.entity.GatewayEnvEntity;
import com.cooljs.modules.gateway.service.GatewayEnvService;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletRequest;

/**
 * 环境信息
 */
@Tag(name = "环境信息", description = "环境信息")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminGatewayEnvController extends BaseController<GatewayEnvService, GatewayEnvEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }
}
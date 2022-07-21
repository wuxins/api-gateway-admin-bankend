package com.cooljs.modules.gateway.controller.admin;

import cn.hutool.json.JSONObject;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.modules.gateway.base.BaseController;
import com.cooljs.modules.gateway.entity.GatewayUpstreamServiceEntity;
import com.cooljs.modules.gateway.service.GatewayUpstreamServiceService;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletRequest;

/**
 * 上游服务信息
 */
@Tag(name = "上游服务信息", description = "上游服务信息")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminGatewayUpstreamServiceController extends BaseController<GatewayUpstreamServiceService, GatewayUpstreamServiceEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }
}
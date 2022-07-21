package com.cooljs.modules.gateway.controller.admin;

import cn.hutool.json.JSONObject;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.modules.gateway.base.BaseController;
import com.cooljs.modules.gateway.entity.GatewayGroupEntity;
import com.cooljs.modules.gateway.service.GatewayGroupService;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletRequest;

/**
 * 分组信息
 */
@Tag(name = "分组信息", description = "分组信息")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminGatewayGroupController extends BaseController<GatewayGroupService, GatewayGroupEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }
}
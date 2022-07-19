package com.cooljs.modules.demo.controller.admin;

import cn.hutool.json.JSONObject;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.core.base.BaseController;
import com.cooljs.modules.demo.entity.DemoCrudEntity;
import com.cooljs.modules.demo.service.DemoCrudService;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试CURD
 */
@Tag(name = "测试CURD", description = "测试CURD")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminDemoCrudController extends BaseController<DemoCrudService, DemoCrudEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }
}
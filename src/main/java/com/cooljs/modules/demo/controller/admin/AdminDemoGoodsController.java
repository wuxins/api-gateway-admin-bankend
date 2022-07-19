package com.cooljs.modules.demo.controller.admin;

import cn.hutool.json.JSONObject;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.core.base.BaseController;
import com.cooljs.modules.demo.entity.DemoGoodsEntity;
import com.cooljs.modules.demo.service.DemoGoodsService;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试商品信息
 */
@Tag(name = "测试商品信息", description = "测试商品信息")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminDemoGoodsController extends BaseController<DemoGoodsService, DemoGoodsEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }
}
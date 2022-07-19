package com.cooljs.modules.base.controller.admin.sys;

import cn.hutool.json.JSONObject;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.core.base.BaseController;
import com.cooljs.modules.base.entity.sys.BaseSysMenuEntity;
import com.cooljs.modules.base.service.sys.BaseSysMenuService;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统菜单
 */
@Tag(name = "系统菜单", description = "系统菜单")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminBaseSysMenuController extends BaseController<BaseSysMenuService, BaseSysMenuEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
    }
}
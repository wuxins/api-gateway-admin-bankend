package com.cooljs.modules.space.controller.admin;

import cn.hutool.json.JSONObject;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.core.base.BaseController;
import com.cooljs.modules.space.entity.SpaceInfoEntity;
import com.cooljs.modules.space.service.SpaceInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件空间信息
 */
@Tag(name = "文件空间信息", description = "文件空间信息")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminSpaceInfoController extends BaseController<SpaceInfoService, SpaceInfoEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp().fieldEq("type", "classifyId"));
    }
}
package com.cooljs.modules.dict.controller.admin;

import cn.hutool.json.JSONObject;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.core.base.BaseController;
import com.cooljs.modules.dict.entity.DictTypeEntity;
import com.cooljs.modules.dict.service.DictTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletRequest;

/**
 * 字典类型
 */
@Tag(name = "字典类型", description = "字典类型")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminDictTypeController extends BaseController<DictTypeService, DictTypeEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }
}
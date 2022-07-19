package com.cooljs.modules.base.controller.admin.sys;

import cn.hutool.json.JSONObject;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.core.base.BaseController;
import com.cooljs.core.request.R;
import com.cooljs.modules.base.entity.sys.BaseSysUserEntity;
import com.cooljs.modules.base.service.sys.BaseSysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统用户
 */
@Tag(name = "系统用户", description = "系统用户")
@CoolRestController(api = {"add", "delete", "update", "page", "info"})
public class AdminBaseSysUserController extends BaseController<BaseSysUserService, BaseSysUserEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
    }

    @Operation(summary = "移动部门")
    @PostMapping("/move")
    public R move(@RequestAttribute JSONObject requestParams) {
        service.move(requestParams.getLong("departmentId"), requestParams.get("userIds", Long[].class));
        return R.ok();
    }

}
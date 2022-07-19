package com.cooljs.modules.base.controller.admin.sys;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.core.base.BaseController;
import com.cooljs.modules.base.entity.sys.BaseSysRoleEntity;
import com.cooljs.modules.base.service.sys.BaseSysRoleService;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统角色
 */
@Tag(name = "系统角色", description = "系统角色")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminBaseSysRoleController extends BaseController<BaseSysRoleService, BaseSysRoleEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        JSONObject tokenInfo = requestParams.getJSONObject("tokenInfo");
        boolean isAdmin = tokenInfo.getStr("username").equals("admin");
        setPageOption(createOp().keyWordLikeFields("name", "label")
                .queryWrapper(new QueryWrapper<BaseSysRoleEntity>()
                        .and(!isAdmin, qw -> qw.eq("userId", tokenInfo.get("userId")).or(w -> w.in("id", tokenInfo.get("roleIds"))))
                        .ne("label", "admin")));
    }

}
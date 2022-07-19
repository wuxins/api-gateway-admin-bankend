package com.cooljs.modules.base.controller.admin.sys;

import cn.hutool.json.JSONObject;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.core.base.BaseController;
import com.cooljs.core.request.R;
import com.cooljs.modules.base.entity.sys.BaseSysLogEntity;
import com.cooljs.modules.base.service.sys.BaseSysConfService;
import com.cooljs.modules.base.service.sys.BaseSysLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 系统日志
 */
@Tag(name = "系统日志", description = "系统日志")
@CoolRestController(api = {"page"})
public class AdminBaseSysLogController extends BaseController<BaseSysLogService, BaseSysLogEntity> {

    @Resource
    private BaseSysConfService baseSysConfService;

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp().keyWordLikeFields("b.name", "a.params", "a.ipAddr"));
    }

    @Operation(summary = "清理日志")
    @PostMapping("/clear")
    public R clear() {
        service.clear(true);
        return R.ok();
    }

    @Operation(summary = "设置日志保存时间")
    @PostMapping("/setKeep")
    public R setKeep(@RequestAttribute JSONObject requestParams) {
        baseSysConfService.updateValue("logKeep", requestParams.getStr("value"));
        return R.ok();
    }

    @Operation(summary = "获得日志报错时间")
    @PostMapping("/getKeep")
    public R getKeep() {
        return R.ok(baseSysConfService.getValue("logKeep"));
    }
}
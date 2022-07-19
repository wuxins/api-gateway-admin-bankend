package com.cooljs.modules.base.controller.app;

import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.core.request.R;
import com.cooljs.modules.base.service.sys.BaseSysLogService;
import com.cooljs.modules.base.service.sys.BaseSysParamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

/**
 * app通用接口
 */
@Tag(name = "应用通用", description = "应用通用")
@CoolRestController
public class AppBaseCommController {
    @Resource
    private BaseSysParamService baseSysParamService;

    @Resource
    private BaseSysLogService baseSysLogService;

    @Operation(summary = "根据键返回网页的参数值")
    @GetMapping("/html")
    public R html(String key) {
        return R.ok(baseSysLogService.list(null));
    }
}

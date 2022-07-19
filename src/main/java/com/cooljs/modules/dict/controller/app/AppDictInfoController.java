package com.cooljs.modules.dict.controller.app;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONObject;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.core.base.BaseController;
import com.cooljs.core.request.R;
import com.cooljs.modules.dict.entity.DictInfoEntity;
import com.cooljs.modules.dict.service.DictInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 字典信息
 */
@Tag(name = "字典信息", description = "字典信息")
@CoolRestController(api = {})
public class AppDictInfoController extends BaseController<DictInfoService, DictInfoEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

    }

    @Operation(summary = "获得字典数据", description = "获得字典数据信息")
    @PostMapping("/data")
    public R data(@RequestBody Dict body) {
        return R.ok(this.service.data(Convert.toList(String.class, body.get("types"))));
    }
}
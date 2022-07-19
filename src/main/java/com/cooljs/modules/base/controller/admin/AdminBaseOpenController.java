package com.cooljs.modules.base.controller.admin;

import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.core.eps.CoolEps;
import com.cooljs.core.file.CoolFileUpload;
import com.cooljs.core.request.R;
import com.cooljs.modules.base.dto.sys.BaseSysLoginDto;
import com.cooljs.modules.base.service.sys.BaseSysLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 系统开放接口，无需权限校验
 */
@Tag(name = "系统开放", description = "系统开放")
@CoolRestController()
public class AdminBaseOpenController {

    @Resource
    private BaseSysLoginService baseSysLoginService;

    @Resource
    private CoolEps coolEps;
    @Resource
    private CoolFileUpload coolFileUpload;

    @Operation(summary = "实体信息与路径", description = "系统所有的实体信息与路径，供前端自动生成代码与服务")
    @GetMapping("/eps")
    public R eps() {
        return R.ok(coolEps.getInfo());
    }

    @Operation(summary = "获得网页内容的参数值")
    @GetMapping("/html")
    public R html() {
        return R.ok();
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public R login(@RequestBody BaseSysLoginDto baseSysLoginDto) {
        return R.ok(baseSysLoginService.login(baseSysLoginDto));
    }

    @Operation(summary = "验证码")
    @GetMapping("/captcha")
    public R captcha(@Parameter(description = "类型：svg|base64") @RequestParam(defaultValue = "base64") String type,
                     @Parameter(description = "宽度") @RequestParam(defaultValue = "150") Integer width,
                     @Parameter(description = "高度") @RequestParam(defaultValue = "50") Integer height) {
        return R.ok(baseSysLoginService.captcha(type, width, height));
    }

    @Operation(summary = "刷新token")
    @GetMapping("/refreshToken")
    public R refreshToken(String refreshToken) {
        return R.ok(baseSysLoginService.refreshToken(refreshToken));
    }

    @Operation(summary = "文件上传")
    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.ALL_VALUE})
    public R upload(@RequestPart(value = "file", required = false) @Parameter(description = "文件") MultipartFile[] files, HttpServletRequest request) {
        return R.ok(coolFileUpload.upload(files, request));
    }

    @Operation(summary = "文件上传模式")
    @GetMapping("/uploadMode")
    public R uploadMode() {
        return R.ok(coolFileUpload.getMode());
    }
}

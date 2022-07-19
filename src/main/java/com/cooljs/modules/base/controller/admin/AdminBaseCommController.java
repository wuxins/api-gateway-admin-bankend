package com.cooljs.modules.base.controller.admin;

import cn.hutool.core.lang.Dict;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.core.eps.CoolEps;
import com.cooljs.core.file.CoolFileUpload;
import com.cooljs.core.request.R;
import com.cooljs.modules.base.entity.sys.BaseSysUserEntity;
import com.cooljs.modules.base.service.sys.BaseSysLoginService;
import com.cooljs.modules.base.service.sys.BaseSysPermsService;
import com.cooljs.modules.base.service.sys.BaseSysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 系统通用接口， 每个人都有权限操作
 */
@Tag(name = "系统通用", description = "系统通用")
@CoolRestController()
public class AdminBaseCommController {

    @Resource
    private BaseSysPermsService baseSysPermsService;

    @Resource
    private BaseSysUserService baseSysUserService;

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

    @Operation(summary = "个人信息")
    @GetMapping("/person")
    public R person(@RequestAttribute() Long adminUserId) {
        BaseSysUserEntity baseSysUserEntity = baseSysUserService.getById(adminUserId);
        baseSysUserEntity.setPassword(null);
        baseSysUserEntity.setPasswordV(null);
        return R.ok(baseSysUserEntity);
    }

    @Operation(summary = "修改个人信息")
    @PostMapping("/personUpdate")
    public R personUpdate(@RequestAttribute Long adminUserId, @RequestBody Dict body) {
        baseSysUserService.personUpdate(adminUserId, body);
        return R.ok();
    }

    @Operation(summary = "权限与菜单")
    @GetMapping("/permmenu")
    public R permmenu(@RequestAttribute() Long adminUserId) {
        return R.ok(baseSysPermsService.permmenu(adminUserId));
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

    @Operation(summary = "退出")
    @PostMapping("/logout")
    public R logout(@RequestAttribute Long adminUserId, @RequestAttribute String adminUsername) {
        baseSysLoginService.logout(adminUserId, adminUsername);
        return R.ok();
    }
}

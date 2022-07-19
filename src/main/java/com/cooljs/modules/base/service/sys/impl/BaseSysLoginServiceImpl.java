package com.cooljs.modules.base.service.sys.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cooljs.core.cache.CoolCache;
import com.cooljs.core.exception.CoolException;
import com.cooljs.core.security.jwt.JwtTokenUtil;
import com.cooljs.modules.base.dto.sys.BaseSysLoginDto;
import com.cooljs.modules.base.entity.sys.BaseSysUserEntity;
import com.cooljs.modules.base.mapper.sys.BaseSysUserMapper;
import com.cooljs.modules.base.security.CoolSecurityUtil;
import com.cooljs.modules.base.service.sys.BaseSysLoginService;
import com.cooljs.modules.base.service.sys.BaseSysPermsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Service
public class BaseSysLoginServiceImpl implements BaseSysLoginService {
    @Resource
    private CoolCache coolCache;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private CoolSecurityUtil coolSecurityUtil;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private BaseSysUserMapper baseSysUserMapper;

    @Resource
    private BaseSysPermsService baseSysPermsService;

    @Override
    public Object captcha(String type, Integer width, Integer height) {
        // 1、生成验证码 2、生成对应的ID并设置在缓存中，验证码过期时间30分钟；
        Map<String, Object> result = new HashMap<>();
        String captchaId = StrUtil.uuid();
        result.put("captchaId", captchaId);
        RandomGenerator randomGenerator = new RandomGenerator(4);
        GifCaptcha gifCaptcha = CaptchaUtil.createGifCaptcha(width, height);
        gifCaptcha.setGenerator(randomGenerator);
        gifCaptcha.setBackground(new Color(47, 52, 71));
        gifCaptcha.setMaxColor(255);
        gifCaptcha.setMinColor(254);
        result.put("data", "data:image/png;base64," + gifCaptcha.getImageBase64());
        coolCache.set("verify:img:" + captchaId, gifCaptcha.getCode(), 1800);
        return result;
    }

    @Override
    public Object login(BaseSysLoginDto baseSysLoginDto) {
        // 1、检查验证码是否正确 2、执行登录操作
        String verifyCode = coolCache.get("verify:img:" + baseSysLoginDto.getCaptchaId(), String.class);
        if (StrUtil.isNotEmpty(verifyCode) && verifyCode.equals(baseSysLoginDto.getVerifyCode())) {
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(baseSysLoginDto.getUsername(), baseSysLoginDto.getPassword());
            Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 查询用户信息并生成token
            BaseSysUserEntity sysUserEntity = baseSysUserMapper.selectOne(Wrappers.<BaseSysUserEntity>lambdaQuery().eq(BaseSysUserEntity::getUsername, baseSysLoginDto.getUsername()));
            if (sysUserEntity.getStatus() == 0) {
                throw new CoolException("用户已禁用");
            }
            Long[] roleIds = baseSysPermsService.getRoles(sysUserEntity);
            Dict tokenInfo = Dict.create()
                    .set("roleIds", roleIds)
                    .set("username", baseSysLoginDto.getUsername())
                    .set("userId", sysUserEntity.getId())
                    .set("passwordVersion", sysUserEntity.getPasswordV());
            String token = jwtTokenUtil.generateToken(tokenInfo);
            String refreshToken = jwtTokenUtil.generateRefreshToken(tokenInfo);

            return Dict.create()
                    .set("token", token)
                    .set("expire", jwtTokenUtil.getExpire())
                    .set("refreshToken", refreshToken)
                    .set("refreshExpire", jwtTokenUtil.getRefreshExpire());
        } else {
            coolCache.del("verify:img:" + baseSysLoginDto.getCaptchaId());
            throw new CoolException("验证码不正确");
        }
    }

    @Override
    public void logout(Long adminUserId, String username) {
        coolSecurityUtil.logout(adminUserId, username);
    }

    @Override
    public Object refreshToken(String refreshToken) {
        JWT jwt = jwtTokenUtil.getTokenInfo(refreshToken);
        try {
            if (jwt == null || !(Boolean) jwt.getPayload("isRefresh")) {
                throw new CoolException("错误的token");
            }

            BaseSysUserEntity baseSysUserEntity = baseSysUserMapper.selectById((Serializable) jwt.getPayload("userId"));
            Long[] roleIds = baseSysPermsService.getRoles(baseSysUserEntity);
            Dict tokenInfo = Dict.create()
                    .set("roleIds", roleIds)
                    .set("username", baseSysUserEntity.getUsername())
                    .set("userId", baseSysUserEntity.getId())
                    .set("passwordVersion", baseSysUserEntity.getPasswordV());
            String token = jwtTokenUtil.generateToken(tokenInfo);
            refreshToken = jwtTokenUtil.generateRefreshToken(tokenInfo);
            return Dict.create()
                    .set("token", token)
                    .set("expire", jwtTokenUtil.getExpire())
                    .set("refreshToken", refreshToken)
                    .set("refreshExpire", jwtTokenUtil.getRefreshExpire());
        } catch (Exception e) {
            throw new CoolException("错误的token");
        }
    }
}

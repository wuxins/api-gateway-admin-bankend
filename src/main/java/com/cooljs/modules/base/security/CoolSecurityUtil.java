package com.cooljs.modules.base.security;

import cn.hutool.json.JSONObject;
import com.cooljs.core.cache.CoolCache;
import com.cooljs.modules.base.entity.sys.BaseSysUserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Security 工具类
 */
@Component
public class CoolSecurityUtil {

    @Resource
    private CoolCache coolCache;

    /**
     * 登录的用户名
     *
     * @return 用户名
     */
    public String username() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * 获得jwt中的信息
     *
     * @param requestParams 请求参数
     * @return jwt
     */
    public JSONObject userInfo(JSONObject requestParams) {
        JSONObject tokenInfo = requestParams.getJSONObject("tokenInfo");
        if (tokenInfo != null) {
            tokenInfo.set("department", coolCache.get("admin:department:" + tokenInfo.get("userId")));
            tokenInfo.set("roleIds", coolCache.get("admin:roleIds:" + tokenInfo.get("userId")));
        }
        return tokenInfo;
    }

    /**
     * 退出登录
     *
     * @param adminUserId 用户ID
     * @param username    用户名
     */
    public void logout(Long adminUserId, String username) {
        coolCache.del("admin:department:" + adminUserId, "admin:passwordVersion:" + adminUserId, "admin:userInfo:" + adminUserId, "admin:userDetails:" + username);
    }

    /**
     * 退出登录
     *
     * @param userEntity 用户
     */
    public void logout(BaseSysUserEntity userEntity) {
        logout(userEntity.getId(), userEntity.getUsername());
    }
}

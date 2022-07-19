package com.cooljs.core.security.jwt;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTValidator;
import com.cooljs.core.comm.CoolProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 */
@Component
public class JwtTokenUtil implements Serializable {

    @Resource
    private CoolProperties coolProperties;

    public long getExpire() {
        return this.coolProperties.getToken().getExpire();
    }

    public long getRefreshExpire() {
        return this.coolProperties.getToken().getRefreshExpire();
    }

    public String getSecret() {
        return this.coolProperties.getToken().getSecret();
    }

    /**
     * 生成令牌
     *
     * @param tokenInfo 保存的用户信息
     * @return 令牌
     */
    public String generateToken(Map<String, Object> tokenInfo) {
        tokenInfo.put("isRefresh", false);
        Date expirationDate = new Date(System.currentTimeMillis() + getExpire() * 1000);
        JWT jwt = JWT.create()
                .setExpiresAt(expirationDate)
                .setKey(getSecret().getBytes())
                .setPayload("created", new Date());
        tokenInfo.forEach(jwt::setPayload);
        return jwt.sign();
    }

    /**
     * 生成令牌
     *
     * @param tokenInfo 保存的用户信息
     * @return 令牌
     */
    public String generateRefreshToken(Map<String, Object> tokenInfo) {
        tokenInfo.put("isRefresh", true);
        Date expirationDate = new Date(System.currentTimeMillis() + getRefreshExpire() * 1000);
        JWT jwt = JWT.create()
                .setExpiresAt(expirationDate)
                .setKey(getSecret().getBytes())
                .setPayload("created", new Date());
        tokenInfo.forEach(jwt::setPayload);
        return jwt.sign();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        JWT jwt = JWT.of(token);
        return jwt.getPayload("username").toString();
    }

    /**
     * 获得token信息
     *
     * @param token 令牌
     * @return token信息
     */
    public JWT getTokenInfo(String token) {
        return JWT.of(token);
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            JWTValidator.of(token).validateDate(DateUtil.date());
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 验证令牌
     *
     * @param token    令牌
     * @param username 用户
     * @return 是否有效
     */
    public Boolean validateToken(String token, String username) {
        String tokenUsername = getUsernameFromToken(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

}

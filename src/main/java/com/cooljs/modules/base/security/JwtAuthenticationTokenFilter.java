package com.cooljs.modules.base.security;

import cn.hutool.jwt.JWT;
import com.cooljs.core.cache.CoolCache;
import com.cooljs.core.security.jwt.JwtTokenUtil;
import com.cooljs.core.security.jwt.JwtUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Token过滤器
 */
@Order(1)
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private CoolCache coolCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(authToken)) {
            JWT jwt = jwtTokenUtil.getTokenInfo(authToken);
            String username = jwt.getPayload("username").toString();
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = coolCache.get("admin:userDetails:" + username, JwtUser.class);
                Integer passwordV = (Integer) jwt.getPayload("passwordVersion");
                Integer rv = coolCache.get("admin:passwordVersion:" + jwt.getPayload("userId"), Integer.class);
                if (jwtTokenUtil.validateToken(authToken, username) && Objects.equals(passwordV, rv) && userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    request.setAttribute("adminUsername", jwt.getPayload("username"));
                    request.setAttribute("adminUserId", jwt.getPayload("userId"));
                    request.setAttribute("tokenInfo", jwt);
                }
            }
        }
        chain.doFilter(request, response);
    }
}

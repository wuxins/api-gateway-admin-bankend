package com.cooljs.modules.base.filter;

import cn.hutool.json.JSONObject;
import com.cooljs.modules.base.service.sys.BaseSysLogService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(10)
public class BaseLogFilter implements Filter {
    @Resource
    private BaseSysLogService baseSysLogService;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 记录日志
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        baseSysLogService.record(request, (JSONObject) request.getAttribute("requestParams"));
        filterChain.doFilter(servletRequest, servletResponse);
    }


}

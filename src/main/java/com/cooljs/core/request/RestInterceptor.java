package com.cooljs.core.request;

import com.cooljs.core.annotation.CoolRestController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 通用方法rest接口
 */
@Component
public class RestInterceptor implements HandlerInterceptor {
    private final static String[] rests = {"add", "delete", "update", "info", "list", "page"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 判断有无通用方法
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            CoolRestController coolRestController = handlerMethod.getBeanType().getAnnotation(CoolRestController.class);
            if (null != coolRestController) {
                String[] urls = request.getRequestURI().split("/");
                String rest = urls[urls.length - 1];
                if (Arrays.asList(rests).contains(rest)) {
                    if (!Arrays.asList(coolRestController.api()).contains(rest)) {
                        response.setStatus(HttpStatus.NOT_FOUND.value());
                        return false;
                    }
                }
            }
        }
        return true;
    }
}

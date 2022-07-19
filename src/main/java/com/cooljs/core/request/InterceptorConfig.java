package com.cooljs.core.request;

import cn.hutool.core.util.StrUtil;
import com.cooljs.core.file.LocalFileProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


/**
 * 拦截器
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private RestInterceptor restInterceptor;

    @Resource
    private LocalFileProperties localFileProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(restInterceptor)
                .addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (localFileProperties != null && StrUtil.isNotEmpty(localFileProperties.getFilePath())) {
            String filePath = localFileProperties.getFilePath();
            if (filePath.contains(":")) {
                filePath = filePath.split(":")[1];
            }
            registry.addResourceHandler("/upload/**").addResourceLocations("file://" + filePath + "upload/");
        }

    }
}

package com.cooljs.core.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 文件
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cool.file.local")
public class LocalFileProperties {
    // 跟域名
    private String baseUrl;
    // 文件路径
    private String filePath;
}

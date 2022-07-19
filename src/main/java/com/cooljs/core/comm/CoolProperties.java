package com.cooljs.core.comm;

import com.cooljs.core.file.FileProperties;
import com.cooljs.core.security.jwt.TokenProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * cool的配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cool")
public class CoolProperties {
    // 是否自动导入sql
    private Boolean initSql;
    // token配置
    @NestedConfigurationProperty
    private TokenProperties token;
    // 文件配置
    @NestedConfigurationProperty
    private FileProperties file;
}


package com.hyc.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: yche
 * @Date: 2019/07/29
 * @Description
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {

    /**
     * jwt加密密码
     */
    private String secret;

    /**
     * jwt过期时间
     */
    private int expiredIn;

    /**
     * jwt刷新时间
     */
    private int refreshIn;

    /**
     * header中的名称
     */
    private String authHeader;

}

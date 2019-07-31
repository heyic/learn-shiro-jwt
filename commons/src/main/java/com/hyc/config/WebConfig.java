package com.hyc.config;

import com.hyc.constant.Consts;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yupaits
 * @date 2018/8/11
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Accept", "Content-Type", "Origin", "Authorization")
                .allowCredentials(false)
                .exposedHeaders(Consts.AUTH_HEADER_NAME);
    }
}

package com.hyc.config.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyc.config.jwt.JwtHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: yche
 * @Date: 2019/07/29
 * @Description
 */
@Configuration
public class ShiroConfig {

    private final ObjectMapper objectMapper;
    private final JwtHelper jwtHelper;
    private final RedisTemplate redisTemplate;

    @Autowired
    public ShiroConfig(ObjectMapper objectMapper, JwtHelper jwtHelper, RedisTemplate redisTemplate) {
        this.objectMapper = objectMapper;
        this.jwtHelper = jwtHelper;
        this.redisTemplate = redisTemplate;
    }


    @Bean
    public StatelessRealm statelessRealm(){
        StatelessRealm statelessRealm = new StatelessRealm();
        statelessRealm.setCachingEnabled(true);
        statelessRealm.setAuthenticationCachingEnabled(true);
        return statelessRealm;
    }

    @Bean
    public StatelessSubjectFactory subjectFactory(){
        return new StatelessSubjectFactory();
    }

    @Bean
    public DefaultSessionManager sessionManager(){
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }

    @Bean
    @DependsOn("springContextUtil")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关闭Shiro自带的session
        DefaultSubjectDAO defaultSubjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = (DefaultSessionStorageEvaluator) defaultSubjectDAO.getSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        //设置subject
        securityManager.setSubjectFactory(subjectFactory());
        //设置sessionManager
        securityManager.setSessionManager(sessionManager());
        //设置remember
        securityManager.setRememberMeManager(null);
        // 使用自定义Realm
        securityManager.setRealm(statelessRealm());
//        // 设置自定义Cache缓存
//        securityManager.setCacheManager(this.cacheManager());

        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }



    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(this.securityManager());

        //设置自定义的filter
        Map<String, Filter> filterMap = new HashMap<>();
        StatelessAuthcFilter statelessAuthcFilter = new StatelessAuthcFilter(objectMapper, jwtHelper, redisTemplate);
        filterMap.put("jwtAuthc", statelessAuthcFilter);
        shiroFilterFactoryBean.setFilters(filterMap);

        Map<String, String> filterChains = new LinkedHashMap<>();
        filterChains.put("/login", "anon");
        filterChains.put("/register", "anon");

        //swagger接口权限 开放
        filterChains.put("/swagger-ui.html", "anon");
        filterChains.put("/webjars/**", "anon");
        filterChains.put("/v2/**", "anon");
        filterChains.put("/swagger-resources/**", "anon");
        filterChains.put("/favicon.ico", "anon");

        //druid控制台接口权限开发
        filterChains.put("/druid/sql.json", "anon");


        filterChains.put("/**", "jwtAuthc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChains);
        return shiroFilterFactoryBean;
    }


    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(true)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，导致返回404。
         * 加入这项配置能解决这个bug
         */
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }


}

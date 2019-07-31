package com.hyc.config.shiro.cache;

import com.hyc.config.jwt.JwtProperties;
import com.hyc.constant.Consts;
import com.hyc.utils.SpringContextUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.Set;

/**
 * @Author: yche
 * @Date: 2019/07/30
 * @Description 授权缓存
 */
@Component
public class AuthenCustomCache<K, V> implements Cache<K, V> {

    private RedisTemplate redisTemplate;

    private JwtProperties jwtProperties;


    public AuthenCustomCache() {
        redisTemplate = SpringContextUtil.getBean("redisTemplate", RedisTemplate.class);
        jwtProperties = SpringContextUtil.getBean(JwtProperties.class);
        if(redisTemplate == null){
            throw new RuntimeException("redisTemplate is null, check redis config");
        }
        if(jwtProperties == null){
            throw new RuntimeException("jwtProperties is null, check jwt config");
        }
    }

    /**
     * 缓存的key名称获取为shiro:cache:account
     * @param key
     * @return java.lang.String
     * @date 2018/9/4 18:33
     */
    private String getKey(Object key) {
        return Consts.PREFIX_SHIRO_CACHE + "authen:" + key;
    }


    @Override
    public Object get(Object k) throws CacheException {
        return redisTemplate.opsForValue().get(getKey(k));
    }

    @Override
    public Object put(Object k, Object v) throws CacheException {
        /**
         * 设置Redis的Shiro缓存时间与jwt的过期时间保持一致
         */
        redisTemplate.opsForValue().set(getKey(k), v, Duration.ofMinutes(jwtProperties.getExpiredIn()));
        return null;
    }

    @Override
    public Object remove(Object k) throws CacheException {
        return redisTemplate.delete(getKey(k));
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set keys() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }
}

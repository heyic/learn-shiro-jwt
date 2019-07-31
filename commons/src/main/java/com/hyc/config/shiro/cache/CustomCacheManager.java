package com.hyc.config.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @Author: yche
 * @Date: 2019/07/30
 * @Description 废弃
 */
@Deprecated
public class CustomCacheManager implements CacheManager {

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new AuthorCustomCache<>();
    }

}

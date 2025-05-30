package com.common.cache.redis.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;

/**
 * redis属性服务
 * 组件需实现该接口并配置相关信息
 *
 * @author dm
 * @create 2020-09-26 18:52
 **/
public interface IRedisPropertyService {

    /**
     * 设置redis的key前缀
     *
     * @return
     */
    String getKeyPrefix();

    /**
     * 用于配置自定义的键策略
     *
     * @return
     */
    default KeyGenerator getKeyGenerator() {
        return null;
    }


    /**
     * 缓存管理器，为空时采用默认的
     *
     * @return
     */
    default CacheManager cacheManager() {
        return null;
    }

    /**
     * 解析器，为null时采用默认的
     *
     * @return
     */
    default CacheResolver cacheResolver() {
        return null;
    }

    /**
     * 自定义错误处理器，为null时采用默认的
     *
     * @return
     */
    default CacheErrorHandler errorHandler() {
        return null;
    }

}

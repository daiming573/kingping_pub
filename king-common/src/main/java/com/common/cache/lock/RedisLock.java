package com.common.cache.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Program: king
 * @Description: redisLock锁注解
 * @Author: daiming5
 * @Date: 2021-04-01 14:13
 * @Version 1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RedisLock {

    /**
     * 缓存key值，keyExpression = "#c_name+':'+#m_name+':'+#args0.getUserId()"
     * @return  String
     */
    String keyExpression() default "";

    /**
     * 缓存失效时间，单位秒
     * @return  int
     */
    int expireSeconds() default 10;

    /**
     * 重试次数
     * @return  int
     */
    int retryTimes() default 0;

    /**
     * 重试间隔
     * @return  int
     */
    int retryIntervalMillis() default 100;

}

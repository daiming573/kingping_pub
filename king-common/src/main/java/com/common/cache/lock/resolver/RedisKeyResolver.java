package com.common.cache.lock.resolver;

import com.common.cache.lock.RedisLock;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @Program: king
 * @Description: redis锁key生成方法
 * @Author: daiming5
 * @Date: 2021-04-01 14:16
 * @Version 1.0
 **/
public interface RedisKeyResolver {

    /**
     * 分布式锁key值生成
     * @param pjp   切面参数
     * @param redisLock 锁注解
     * @return  String
     */
    String resolve(ProceedingJoinPoint pjp, RedisLock redisLock);

}

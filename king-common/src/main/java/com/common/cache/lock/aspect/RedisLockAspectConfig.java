package com.common.cache.lock.aspect;

import com.common.cache.lock.RedisLock;
import com.common.cache.lock.exception.RedisLockFailureException;
import com.common.cache.lock.resolver.DefaultRedisKeyResolver;
import com.common.cache.lock.resolver.RedisKeyResolver;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @Program: king
 * @Description: redis锁注解切面处理
 * @Author: daiming5
 * @Date: 2021-04-01 14:28
 * @Version 1.0
 **/
@ConditionalOnClass(RedisTemplate.class)
@Configuration
@Aspect
@Slf4j
public class RedisLockAspectConfig implements InitializingBean {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    private RedisKeyResolver redisKeyResolver;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(redisKeyResolver == null){
            redisKeyResolver = new DefaultRedisKeyResolver();
        }
    }

    @Around("@annotation(redisLock)")
    public Object lock(ProceedingJoinPoint pjp, RedisLock redisLock) throws Throwable {
        log.debug("===> start lock...");
        int expire = redisLock.expireSeconds();
        String key = redisKeyResolver.resolve(pjp, redisLock);
        int retryTimes = redisLock.retryTimes();
        int retryIntervalMillis = redisLock.retryIntervalMillis();

        log.debug("===> redis lock key: {}", key);
        Boolean getLock = redisTemplate.opsForValue().setIfAbsent(key,1, expire, TimeUnit.SECONDS);
        log.debug("===> getLock: {}", getLock);
        int retried = 0;
        while (!getLock && retried++ < retryTimes){
            log.debug("===> retry lock times: {}", retried);
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(retryIntervalMillis));
            getLock = redisTemplate.opsForValue().setIfAbsent(key,1, expire, TimeUnit.SECONDS);
        }
        if(!getLock){
            throw new RedisLockFailureException("Method is locked up.");
        }
        Object ret;
        try {
            ret = pjp.proceed(pjp.getArgs());
        } finally {
            //To make sure lock finally unlocked.
            redisTemplate.delete(key);
            log.debug("===> release lock...");
        }
        return ret;
    }

}

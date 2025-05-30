package com.common.cache.lock.exception;

/**
 * @Program: king
 * @Description: redis分布式锁异常
 * @Author: daiming5
 * @Date: 2021-04-01 14:32
 * @Version 1.0
 **/
public class RedisLockFailureException extends RuntimeException{

    public RedisLockFailureException() {
    }

    public RedisLockFailureException(String message) {
        super(message);
    }

    public RedisLockFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisLockFailureException(Throwable cause) {
        super(cause);
    }

    public RedisLockFailureException(String message, Throwable cause,
                                     boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

package com.common.cache.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisPropertyConfig implements IRedisPropertyService {

    @Value("${redis.key.prefix:keyPrefix}")
    private String redisKeyPrefix;

    @Override
    public String getKeyPrefix() {
        return redisKeyPrefix;
    }


}

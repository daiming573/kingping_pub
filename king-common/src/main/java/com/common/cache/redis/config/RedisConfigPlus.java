package com.common.cache.redis.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;

/**
 * @Program: king
 * @Description: redis配置
 * @Author: daiming5
 * @Date: 2021-03-04 16:24
 * @Version 1.0
 **/
@ConditionalOnExpression("${redis.enable:false}==true")
@Configuration
@EnableCaching
public class RedisConfigPlus extends CachingConfigurerSupport {

    @Autowired(required = false)
    private IRedisPropertyService redisPropertyService;

    private static final Logger log = LoggerFactory.getLogger(RedisConfigPlus.class);

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                       @Autowired GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }


    @Bean
    public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule((new SimpleModule()).addSerializer(new NullValueSerializer(null)));
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        //注解 序列化中
//        objectMapper.setAnnotationIntrospector(new DisableSensitiveFieldIntrospector());
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    /**
     * 处理redis序列化问题
     *
     * @return Jackson2JsonRedisSerializer
     */
//    private Jackson2JsonRedisSerializer<Object> initJacksonSerializer() {
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        //以下替代旧版本 om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
////        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
//        //bugFix Jackson2反序列化数据处理LocalDateTime类型时出错
//        om.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
//        // java8 时间支持
//        om.registerModule(new JavaTimeModule());
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        return jackson2JsonRedisSerializer;
//    }


    /**
     * spring cache的key生产策略
     * @return  KeyGenerator
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        if (null != redisPropertyService && null != redisPropertyService.getKeyGenerator()) {
            return redisPropertyService.getKeyGenerator();
        }
        return new SimpleKeyGenerator() {
            /**
             * 对参数进行拼接后MD5
             */
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(':').append(method.getName());

                StringBuilder paramsSb = new StringBuilder();
                for (Object param : params) {
                    // 如果不指定，默认生成包含到键值中
                    if (param != null) {
                        paramsSb.append(param.toString());
                    }
                }

                if (paramsSb.length() > 0) {
                    sb.append("_").append(paramsSb);
                }
                return sb.toString();
            }
        };

    }

    @Override
    public CacheManager cacheManager() {
        if (null != redisPropertyService && null != redisPropertyService.cacheManager()) {
            return redisPropertyService.cacheManager();
        }
        return super.cacheManager();
    }

    @Override
    public CacheResolver cacheResolver() {
        if (null != redisPropertyService && null != redisPropertyService.cacheResolver()) {
            return redisPropertyService.cacheResolver();
        }
        return super.cacheResolver();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        if (null != redisPropertyService && null != redisPropertyService.errorHandler()) {
            return redisPropertyService.errorHandler();
        }
        return super.errorHandler();
    }

}

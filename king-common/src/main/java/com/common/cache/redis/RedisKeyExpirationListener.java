package com.common.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * redis过期事件监听处理
 *
 * @author daiming5
 * @version 1.0
 * @date 2020-06-01
 * @since JDK 1.8
 */
@ConditionalOnExpression("${redis.enable:false}==true")
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    Logger logger = LoggerFactory.getLogger(RedisKeyExpirationListener.class);

    public static final String KEY_PREFIX = "d";

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 监听 rediskey过期
     *
     * @param message 消息内容
     * @param pattern 匹配模式
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        if (ObjectUtils.isEmpty(message)) {
            return;
        }
        String expiredKey = String.valueOf(message);
        if (!expiredKey.contains(KEY_PREFIX)) {
            return;
        }
        logger.debug("redis key expiredKey {}", expiredKey);
        expiredKey = expiredKey.substring(KEY_PREFIX.length() + 1);

//        AppContext.getContext().publishEvent(null);
    }
}
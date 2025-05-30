
package com.common.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

/**
 * @Program: king
 * @Description: 线程池配置
 * @Author: daiming5
 * @Date: 2021-03-06 17:15
 * @Version 1.0
 **/
@Configuration
@EnableAsync
public class ThreadPoolConfig implements AsyncConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolConfig.class);

    public static final String PREFIX = "king.";

    @Resource
    private AsyncThreadPoolProperties asyncThreadPoolProperties;

    public ThreadPoolConfig() {
    }

    @Override
    @Primary
    @Bean(
            value = {"taskExecutor"},
            destroyMethod = "destroy"
    )
    public ThreadPoolTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(asyncThreadPoolProperties.getCorePoolSize());
        pool.setKeepAliveSeconds(asyncThreadPoolProperties.getKeepAliveTime());
        pool.setMaxPoolSize(asyncThreadPoolProperties.getMaxPoolSize());
        pool.setQueueCapacity(asyncThreadPoolProperties.getQueueCapacity());
        pool.setThreadNamePrefix(asyncThreadPoolProperties.getNamePrefix());
        pool.setWaitForTasksToCompleteOnShutdown(true);
        pool.setAwaitTerminationSeconds(60);
        pool.setRejectedExecutionHandler(new CallerRunsPolicy());
        pool.setTaskDecorator((runnable) -> () -> {
            try {
                runnable.run();
            } catch (Exception var2) {
                logger.error("thread error", var2);
            }

        });
        pool.initialize();
        return pool;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            logger.error(MessageFormat.format("taskExecutor execute error method={0}, params={1}", method, objects), throwable);
        };
    }
}

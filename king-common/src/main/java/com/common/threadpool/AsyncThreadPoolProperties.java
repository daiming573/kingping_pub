/*
 * The MIT License (MIT)
 * Copyright © 2019-2020 <sky>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.common.threadpool;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 线程池参数vo
 *
 * @author sky
 */
@Component
@ConfigurationProperties(prefix = ThreadPoolConfig.PREFIX + "threadpool")
public class AsyncThreadPoolProperties {
    /**
     * 默认核心线程数
     */
    private static final int CORE_POOL_SIZE = 10;
    /**
     * 默认线程池最大线程数
     */
    private static final int MAX_POOL_SIZE = 50;
    /**
     * 默认队列大小
     */
    private static final int QUEUE_CAPACITY = 10000;
    /**
     * 默认线程不被使用后存活时间
     */
    private static final int KEEP_ALIVE_TIME = 120;

    /**
     * 是否开启
     */
    @Getter
    @Setter
    private boolean enabled = true;
    /**
     * 核心线程数
     */
    @Getter
    @Setter
    private int corePoolSize = CORE_POOL_SIZE;
    /**
     * 线程池最大线程数
     */
    @Getter
    @Setter
    private int maxPoolSize = MAX_POOL_SIZE;
    /**
     * 队列大小
     */
    @Getter
    @Setter
    private int queueCapacity = QUEUE_CAPACITY;
    /**
     * 线程不被使用后存活时间
     */
    @Getter
    @Setter
    private int keepAliveTime = KEEP_ALIVE_TIME;
    /**
     * 线程名称前缀
     */
    @Getter
    @Setter
    private String namePrefix = "king-threadpool-";
    /**
     * 是否丢弃,默认丢弃
     */
    @Getter
    @Setter
    private boolean isDiscard = true;


    @Override
    public String toString() {
        return "AsyncThreadPoolProperties{" +
                "corePoolSize=" + corePoolSize +
                ", maxPoolSize=" + maxPoolSize +
                ", queueCapacity=" + queueCapacity +
                ", keepAliveTime=" + keepAliveTime +
                ", namePrefix='" + namePrefix +
                ", isDiscard=" + isDiscard +
                '}';
    }
}

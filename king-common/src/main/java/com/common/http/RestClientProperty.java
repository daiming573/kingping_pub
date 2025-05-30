package com.common.http;/**
 *
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author daiming5
 * @date create in 2020/10/10 14:11
 * @description rest客户端连接配置
 * @project rpo
 * @version 1.0
 * @since JDK 1.8
 *
 */
@Component
@PropertySource(value = {"classpath:application.properties"}, ignoreResourceNotFound = true)
public class RestClientProperty {

    @Value("${rest.client.connection.timeout:2000}")
    private Integer connectionTimeout;

    @Value("${rest.client.read.timeout:30000}")
    private Integer readTimeout;

    /**
     * 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
     */
    @Value("${rest.client.connection.request.timeout:200}")
    private Integer connectionRequestTimeout;

    /**
     * 每个主机的并发
     */
    @Value("${rest.client.default.max.per.reoute:1000}")
    private Integer defaultMaxPerRoute;

    /**
     * 整个连接池的并发
     */
    @Value(("${rest.client.max.total:1000}"))
    private Integer maxTotal;

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Integer getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public Integer getDefaultMaxPerRoute() {
        return defaultMaxPerRoute;
    }

    public void setDefaultMaxPerRoute(Integer defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

}

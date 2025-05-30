package com.common.log.rest;

import org.springframework.http.HttpRequest;

/**
 * @author daiming5
 * @version 1.0
 * @date create in 2020/10/10 11:06
 * @description 请求request holder
 * @project king
 * @since JDK 1.8
 */
public class RequestHolder {


    /**
     * request
     */
    private static final ThreadLocal<HttpRequest> REQUEST_LOCAL = new ThreadLocal<>();

    /**
     * 记录当前请求是否为业务发起方的标识
     */
    private static final ThreadLocal<Boolean> START_FLAG_LOCAL = new ThreadLocal<>();

    public static HttpRequest getRequest() {
        return REQUEST_LOCAL.get();
    }

    public static void setRequest(HttpRequest request) {
        REQUEST_LOCAL.set(request);
    }

    public static Boolean getStartFlag() {
        return START_FLAG_LOCAL.get();
    }

    public static void setStartFlag(Boolean flag) {
        START_FLAG_LOCAL.set(flag);
    }

    public static void clear() {
        REQUEST_LOCAL.remove();
        START_FLAG_LOCAL.remove();
    }

}

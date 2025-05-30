package com.common.log.dto;

import lombok.Data;

/**
 * @author daiming5
 * @version 1.0
 * @since JDK 1.8
 */
@Data
public class ReqLogDto {

    /**
     * 请求头appId
     */
    String appId;

    /**
     * 请求类型 GET POST
     */
    String httpMethod;

    /**
     * 请求方法
     */
    String method;

    /**
     * 请求方法名称
     */
    String methodName;

    /**
     * 请求头appId
     */
    String className;

    /**
     * 请求参数body
     */
    Object reqestBody;

    /**
     * 请求参数query
     */
    Object requestQuery;

    /**
     * 请求头
     */
    Object heads;

    /**
     * url地址
     */
    String url;

    /**
     * ip地址
     */
    String ip;

    /**
     * ip地址类型
     */
    String fromSource;

    /**
     * 返回
     */
    String response;


}

package com.common.errorcode;

import com.common.response.PrettyResponse;

/**
 * @author daiming5
 */
public interface IErrorCode {

    /**
     * 获取code
     *
     * @return String
     */
    String getCode();

    /**
     * 获取msg
     *
     * @return String
     */
    String getMsg();

    /**
     * 返回结构体
     * @param <T>   类型
     * @return  PrettyResponse<T>
     */
    <T> PrettyResponse<T> createResponse();

    /**
     * 返回结构体
     * @param <T>   类型
     * @return  PrettyResponse<T>
     */
    <T> PrettyResponse<T> createResponse(T data);

}
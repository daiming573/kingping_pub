package com.common.errorcode;

import com.common.response.PrettyResponse;

/**
 * <p>
 * </p>
 *
 * @author daiming5
 * @version V1.0
 */
public enum DefaultErrorCode implements IErrorCode {

    /**
     * 默认错误码
     * 0x002 标识码
     * 低两位（1-失败，3-异常，0-不区分）
     * 1-系统默认错误码，  0x00231001
     * 10-系统默认模块，11-http请求模块
     */
    SUCCESS("0", "success"),
    FAIL("0x00211000", "请求失败"),
    ERROR("0x00201001", "unknown system error"),
    VALIDATE_ERROR("0x00211002", "parameter check failed"),
    REQUEST_METHOD_ERROR("0x00201003", "the request encounter some error"),
    REQUEST_MISSING_REQUEST_PARAMETER("0x00201004", "request missing some parameter"),
    REQUEST_TYPE_MISMATCH("0x00201005", "the requestType is not match"),
    REQUEST_NOT_FOUND("0x00201006", "the request method is not exist"),
    REQUEST_TOKEN_CHECK_ERROR("0x00201007", "token check failed"),
    CSRF_FILTER_CHECK_ERROR("0x00201009", "request header contains unvalid ip"),
    DB_SABE_ERROR("0x00201010", "data sava to db error"),

    HTTP_CLIENT_ERROR("0x00231101", "系统网络请求异常，请稍后再试！"),
    HTTP_IO_ERROR("0x00231102", "系统网络数据异常，请稍后再试！"),
    HTTP_RES_NO_ERROR("0x00231103", "网络数据无返回，请稍后再试！"),
    HTTP_EXCEP__ERROR("0x00231104", "网络请求异常，请稍后再试！"),

    ;

    private final String code;

    private final String msg;

    private DefaultErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public <T> PrettyResponse<T> createResponse() {
        return new PrettyResponse<>(this.code, this.msg);
    }

    @Override
    public <T> PrettyResponse<T> createResponse(T data) {
        return new PrettyResponse<T>(this.code, this.msg, data);
    }
}

package com.common.errorcode;

import com.common.response.PrettyResponse;

/**
 * @Program: king
 * @Description:
 * @Author: daiming5
 * @Date: 2021-03-06 15:07
 * @Version 1.0
 **/
public enum WechatErrorCode implements IErrorCode {

    /**
     * 微信模块错误码
     * 0x002 标识码
     * 低两位（1-失败，3-异常，0-不区分）
     * 2-微信模块，  0x00232001
     * 20-默认模块，21-人员
     */
    WECHAT_INTERFACE_ERROR("0x00202000", "微信接口异常"),
    MINIAPP_GET_ACCESS_TOKEN_ERROR("0x00202001", "获取小程序accessToken异常"),
    MINIAPP_APPID_SECRET_ERROR("0x00202002", "小程序appId或密钥未配置"),
    MINIAPP_NOT_EXIST_ERROR("0x00202003", "小程序不存在，请在小程序管理列表配置"),

    MINIAPP_GET_USER_INFO_ERROR("0x00202101", "小程序获取用户微信信息失败"),
    ;

    private final String code;

    private final String msg;

    private WechatErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
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
    public <T> PrettyResponse<T>  createResponse() {
        return new PrettyResponse<>(this.code, this.msg);
    }

    @Override
    public <T> PrettyResponse<T> createResponse(T data) {
        return new PrettyResponse<T>(this.code, this.msg, data);
    }

}

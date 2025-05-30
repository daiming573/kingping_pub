package com.king.wechat.service;

/**
 * @author daiming
 * @version 1.0
 * @date 2019-11-24 14:46
 * @Description:
 */
public interface AccessTokenService {

    /**
     * 获取小程序token
     *
     * @param type 类型
     * @return String
     */
    String getMiniAppAccessToken(String type);

    /**
     * 刷新小程序token
     *
     * @param type      类型
     * @param appId     appId
     * @param appSecret 秘钥
     * @return String
     */
    String refreshMiniAppAccessToken(String type, String appId, String appSecret);

    /**
     * 刷新小程序token
     */
    void refreshMiniAppAccessToken();
}

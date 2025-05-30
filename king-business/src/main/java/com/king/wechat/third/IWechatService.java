package com.king.wechat.third;

import com.king.wechat.dto.resp.WechatAccessTokenResp;
import com.king.wechat.dto.resp.WechatOpenInfoResp;

import java.util.Map;

/**
 * @Program: king
 * @Description: 微信接口
 * @Author: daiming5
 * @Date: 2021-03-17 15:42
 * @Version 1.0
 **/
public interface IWechatService {

    /**
     * 获取微信accessToken
     * @param appId 应用ID
     * @param appSecret 秘钥
     * @return  WechatAccessTokenResp
     */
    WechatAccessTokenResp getAccessToken(String appId, String appSecret);
    
    /**
     * 通过code获取用户open_id
     *
     * @param code  授权code
     * @return  WechatOpenInfoResp
     */
    WechatOpenInfoResp getOpenInfo(String code, String miniAppId, String miniAppSecret);
}

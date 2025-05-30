package com.king.wechat.service;

import com.king.wechat.dto.resp.WechatOpenInfoResp;
import com.king.wechat.dto.resp.WechatUserInfoResp;

/**
 * @author daiming
 * @version 1.0
 * @date 2019-11-24
 * @Description:
 */
public interface MiniappUserService {

    /**
     * 通过code获取用户open_id
     *
     * @param code 授权code
     * @param type 小程序应用类型
     * @return WechatOpenInfoResp
     */
    WechatOpenInfoResp getOpenInfoWithEncKey(String code, String type);

    /**
     * 获取用户信息
     *
     * @param openId        用户唯一标识
     * @param sessionKey    key
     * @param rowData       返回数据
     * @param signature     验签
     * @param encryptedData 加密数据
     * @param iv            偏移量
     * @return WechatUserInfoResp
     */
    WechatUserInfoResp getUserInfo(String openId, String sessionKey, String rowData, String signature, String encryptedData, String iv);
}

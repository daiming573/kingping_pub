package com.king.wechat.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.king.wechat.dto.WechatBaseResp;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Program: king
 * @Description: 微信用户openID信息
 * @Author: daiming5
 * @Date: 2021-03-18 10:29
 * @Version 1.0
 **/
@ApiModel(value = "WechatOpenInfoResp", description = "微信用户openID信息返回体")
@Setter
@Getter
@ToString
public class WechatOpenInfoResp extends WechatBaseResp {

    /*  -1-系统繁忙，此时请开发者稍候再试, 0-请求成功	, 40029-code 无效, 45011-频率限制，每个用户每分钟100次 */

    /**
     * 用户唯一标识
     */
    @JsonProperty("openid")
    String openid;

    /**
     * 会话密钥
     */
    @JsonProperty("session_key")
    String sessionKey;

    /**
     * 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回
     */
    @JsonProperty("unionid")
    String unionid;

}

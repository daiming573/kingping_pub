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
 * @Description: accessToken返回参数
 * @Author: daiming5
 * @Date: 2021-03-17 17:10
 * @Version 1.0
 **/
@ApiModel(value = "WechatAccessTokenResp", description = "微信accessToken信息返回体")
@Setter
@Getter
@ToString
public class WechatAccessTokenResp extends WechatBaseResp {

    /**
     * 应用accessToken
     */
    @JsonProperty("access_token")
    String accessToken;

    /**
     * 有效时间 单位秒
     */
    @JsonProperty("expires_in")
    Integer expiresIn;

}

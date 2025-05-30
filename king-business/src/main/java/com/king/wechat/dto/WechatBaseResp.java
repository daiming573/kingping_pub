package com.king.wechat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Program: king
 * @Description: 微信接口公共返回体
 * @Author: daiming5
 * @Date: 2021-03-18 10:35
 * @Version 1.0
 **/
@Data
public class WechatBaseResp {

    /**
     * 错误码
     */
    @JsonProperty("errcode")
    String errCode;

    /**
     * 错误信息
     */
    @JsonProperty("errmsg")
    String errMsg;


}

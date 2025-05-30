package com.king.wechat.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Program: king
 * @Description: 用户信息返回体
 * @Author: daiming5
 * @Date: 2021-03-18 11:53
 * @Version 1.0
 **/
@Data
@ApiModel(value = "WechatUserInfoResp", description = "微信用户信息返回体")
public class WechatUserInfoResp {

    /**
     * 用户昵称
     */
    @JsonProperty("nickName")
    String nickName;
    @JsonProperty("gender")
    Integer gender;
    /**
     * 用户头像图片的 URL。URL 最后一个数值代表正方形头像大小（有 0、46、64、96、132 数值可选，
     * 0 代表 640x640 的正方形头像，46 表示 46x46 的正方形头像，剩余数值以此类推。默认132），
     * 用户没有头像时该项为空。若用户更换头像，原有头像 URL 将失效。
     */
    @JsonProperty("avatarUrl")
    String avatarUrl;
    /**
     * 用户所在国家
     */
    @JsonProperty("country")
    String country;
    @JsonProperty("province")
    String province;
    @JsonProperty("city")
    String city;
    @JsonProperty("language")
    String language;

}

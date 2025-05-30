package com.king.wechat.dto.req;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "微信配置表1", tags = {"微信字典配置表1"})
@ApiModel(value = "微信配置表", description = "微信字典配置表")
public class WxEnvReqDto {
    @ApiModelProperty(value = "配置key", name = "envKey", example = "token")
    String envKey;

    @ApiModelProperty(value = "配置value", name = "envValue", example = "avaddd111")
    String envValue;

}

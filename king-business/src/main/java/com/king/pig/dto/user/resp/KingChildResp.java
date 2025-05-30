package com.king.pig.dto.user.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author daiming5
 */
@Data
@ApiModel(value = "孩子账户信息返回参数", description = "孩子账户信息返回参数")
public class KingChildResp {

    @ApiModelProperty(value = "childId", name = "孩子ID", example = "1")
    private Integer childId;

    @ApiModelProperty(value = "孩子账户昵称", name = "昵称", example = "小宝")
    private String nickName;

    @ApiModelProperty(value = "F-女性，M-男性", name = "性别", example = "M")
    private String sex;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "生日", name = "生日", example = "2019-11-25")
    private Date birthday;

    @ApiModelProperty(value = "账户总金额，单位分", name = "账户总金额", example = "1000")
    private Integer accountTotal;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}

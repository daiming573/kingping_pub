package com.king.pig.dto.user.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Program: king
 * @Description: 用户孩子信息返回参数
 * @Author: daiming5
 * @Date: 2021-06-04 15:54
 * @Version 1.0
 **/
@Data
@ApiModel(value = "用户孩子信息返回参数", description = "用户孩子信息返回参数")
public class KingUserChildResp {

    @ApiModelProperty(value = "parentId", name = "父母ID", example = "1")
    private Integer parentId;

    @ApiModelProperty(value = "家庭ID", name = "familyId", example = "2")
    private Integer familyId;

    @ApiModelProperty(value = "用户ID", name = "userId", example = "1111ssss")
    private String userId;

    @ApiModelProperty(value = "unionId", name = "unionId", example = "unionId")
    private String unionId;

    @ApiModelProperty(value = "openId", name = "openId", example = "openId")
    private String openId;

    @ApiModelProperty(value = "身份，F-父亲，M-母亲", name = "身份", example = "F")
    private String userType;

    @ApiModelProperty(value = "F-女性，M-男性", name = "性别", example = "M")
    private String sex;

    @ApiModelProperty(value = "用户孩子列表", name = "kingUserChildList", example = "[{'id':'1','name':'三'},{'id':'2','name':'张'}]")
    List<KingChildResp> kingUserChildList;
}

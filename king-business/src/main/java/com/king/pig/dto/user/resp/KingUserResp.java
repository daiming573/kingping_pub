package com.king.pig.dto.user.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author daiming5
 */
@Data
@ApiModel(value = "用户信息返回参数", description = "用户信息返回参数")
public class KingUserResp {

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

    @ApiModelProperty(value = "是否孩子父母都绑定,未全部绑定可邀请另一方绑定(我的)，true-是，false-否", name = "是否父母都绑定", example = "true")
    private boolean parentAll;

}

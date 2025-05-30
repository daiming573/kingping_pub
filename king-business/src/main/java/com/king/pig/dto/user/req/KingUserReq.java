package com.king.pig.dto.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 81292
 */
@Data
@ApiModel(value = "用户信息请求参数", description = "用户信息请求参数")
public class KingUserReq {

    /**
     * 用户UID
     */
    @ApiModelProperty(value = "userId", name = "userId", example = "1111ssss")
    private String userId;

    @NotBlank(message = "用户unionId不能为空")
    @ApiModelProperty(value = "unionId", name = "unionId", example = "unionId")
    private String unionId;

    @ApiModelProperty(value = "openId", name = "openId", example = "openId")
    private String openId;

    @ApiModelProperty(value = "mobile", name = "手机号", example = "18855552222")
    private String mobile;

    @ApiModelProperty(value = "F-女性，M-男性", name = "性别", example = "M")
    private String sex;

    @ApiModelProperty(value = "家庭ID,绑定关系必填", name = "familyId", example = "2")
    private Integer familyId;

    @NotBlank(message = "请选择您的身份，父亲或母亲")
    @ApiModelProperty(value = "身份，F-父亲，M-母亲", name = "身份", example = "F")
    private String userType;

    @ApiModelProperty(value = "孩子账户信息", name = "孩子账户信息", example = "{'nickName':'王','sex':'M'}")
    private KingUserChildReq kingUserChildReqDto;

}

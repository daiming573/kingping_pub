package com.king.pig.dto.user.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author daiming5
 */
@Data
@ApiModel(value = "孩子账户信息请求参数", description = "孩子账户信息请求参数")
public class KingUserChildReq {

    @NotBlank(message = "孩子昵称不能为空")
    @ApiModelProperty(value = "孩子账户昵称", name = "昵称", example = "小宝")
    private String nickName;

    @NotBlank(message = "孩子性别不能为空")
    @ApiModelProperty(value = "F-女性，M-男性", name = "性别", example = "M")
    private String sex;

    @ApiModelProperty(value = "生日", name = "生日", example = "2019-11-25")
    private Date birthday;

}

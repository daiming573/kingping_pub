package com.king.pig.dto.account.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Program: king
 * @Description: 账户金额趋势返回参数
 * @Author: daiming5
 * @Date: 2021-06-04 21:56
 * @Version 1.0
 **/
@Data
public class KingAccountChatResp {
    @ApiModelProperty(value = "孩子ID", name = "childId", example = "1")
    private Integer childId;
    @ApiModelProperty(value = "交易日期", name = "currentDate", example = "2021-06-04")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date currentDate;
    @ApiModelProperty(value = "账户总金额，单位分", name = "账户总金额", example = "1000")
    private Integer accountTotal;
}

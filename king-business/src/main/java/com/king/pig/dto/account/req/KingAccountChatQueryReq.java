package com.king.pig.dto.account.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Program: king
 * @Description: 账户金额趋势请求参数
 * @Author: daiming5
 * @Date: 2021-06-04 21:49
 * @Version 1.0
 **/
@Data
@ApiModel(value = "账户金额趋势请求参数", description = "账户金额趋势请求参数")
public class KingAccountChatQueryReq {
    @ApiModelProperty(value = "income-收入,pay-支出,task-任务,reward-奖励", name = "类型", example = "pay")
    private String type;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "endDate", name = "查询开始日期", example = "2021-05-01")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "endDate", name = "查询结束日期", example = "2021-05-01")
    private Date endDate;
}

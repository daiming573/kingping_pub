package com.king.pig.dto.account.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Program: king
 * @Description: 账户交易日期返回参数
 * @Author: daiming5
 * @Date: 2021-06-04 21:21
 * @Version 1.0
 **/
@Data
@ApiModel(value = "孩子账户日期记录返回参数", description = "孩子账户日期记录返回参数")
public class KingAccountRecordDateResp {
    @ApiModelProperty(value = "孩子ID", name = "childId", example = "1")
    private Integer childId;
    @ApiModelProperty(value = "交易日期", name = "currentDate", example = "2021-06-04")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date currentDate;
    @ApiModelProperty(value = "交易记录", name = "KingChildAccountRecordList", example = "[]")
    private List<KingChildAccountRecordResp> kingChildAccountRecordList;
}

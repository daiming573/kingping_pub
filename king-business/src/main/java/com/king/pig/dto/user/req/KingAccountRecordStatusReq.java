package com.king.pig.dto.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Program: king
 * @Description: 用户账户记录状态请求参数
 * @Author: daiming5
 * @Date: 2021-06-04 22:06
 * @Version 1.0
 **/
@Data
@ApiModel(value = "用户账户记录状态请求参数", description = "用户账户记录状态请求参数")
public class KingAccountRecordStatusReq {

    @ApiModelProperty(value = "id", name = "账户记录ID", example = "1")
    private Integer id;
    @ApiModelProperty(value = "任务状态，undo-未完成，finish-已完成", name = "任务状态", example = "finish")
    private String status;
}

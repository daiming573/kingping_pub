package com.king.pig.dto.account.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Program: king
 * @Description: 孩子账户记录
 * @Author: daiming5
 * @Date: 2021-06-04 20:02
 * @Version 1.0
 **/
@Data
@ApiModel(value = "孩子账户记录返回参数", description = "孩子账户记录返回参数")
public class KingChildAccountRecordResp {

    @ApiModelProperty(value = "账户记录ID", name = "id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "孩子ID", name = "childId", example = "1")
    private Integer childId;
    @ApiModelProperty(value = "类型，income-收入，pay-支出，task-任务，reward-奖励", name = "type", example = "income")
    private String type;
    @ApiModelProperty(value = "账户收入，单位分", name = "accountIn", example = "1000")
    private Integer accountIn;
    @ApiModelProperty(value = "账户支出，单位分", name = "accountOut", example = "1000")
    private Integer accountOut;
    @ApiModelProperty(value = "任务状态，undo-未完成，finish-已完成", name = "status", example = "undo")
    private String status;
    @ApiModelProperty(value = "说明", name = "notes", example = "备注说明")
    private String notes;
    @ApiModelProperty(value = "任务创建人", name = "createUserId", example = "aaaa")
    private String createUserId;
    @ApiModelProperty(value = "任务创建人身份", name = "createUser", example = "爸爸")
    private String createUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}

package com.king.pig.dto.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author daiming5
 * @version 1.0
 * @since JDK 1.8
 */
@Data
@ApiModel(value = "用户账户记录请求参数", description = "用户账户记录请求参数")
public class KingChildAccountRecordReq {

    @ApiModelProperty(value = "id", name = "账户记录ID", example = "1")
    private Integer id;
    @NotNull
    @ApiModelProperty(value = "childId", name = "孩子ID", example = "1")
    private Integer childId;

    @NotBlank
    @ApiModelProperty(value = "income-收入,pay-支出,task-任务,reward-奖励", name = "类型", example = "pay")
    private String type;

    @ApiModelProperty(value = "账户收入，单位分", name = "账户收入", example = "1000")
    private Integer accountIn;

    @ApiModelProperty(value = "账户支出，单位分", name = "账户支出", example = "100")
    private Integer accountOut;

    @NotBlank
    @ApiModelProperty(value = "任务状态，undo-未完成，finish-已完成", name = "任务状态", example = "finish")
    private String status;

    @ApiModelProperty(value = "说明", name = "说明", example = "备注")
    private String notes;
    @ApiModelProperty(value = "任务创建人", name = "createUserId", example = "aaaa")
    private String createUserId;
}

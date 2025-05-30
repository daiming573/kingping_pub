package com.king.pig.enums;

/**
 * @Program: king
 * @Description: 账户交易记录状态
 * @Author: daiming5
 * @Date: 2021-06-04 21:06
 * @Version 1.0
 **/
public enum KingAccountStatusEnum {
    /**
     * 任务状态，undo-未完成，finish-已完成
     */
    ACCOUNT_TASK_STATUS_UNDO("undo", "未完成"),
    ACCOUNT_TASK_STATUS_FINISH("finish", "已完成"),
    ;
    private final String type;

    private final String name;

    KingAccountStatusEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

}

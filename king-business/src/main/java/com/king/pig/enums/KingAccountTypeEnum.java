package com.king.pig.enums;

/**
 * @Program: king
 * @Description: 账户类型枚举
 * @Author: daiming5
 * @Date: 2021-06-04 21:09
 * @Version 1.0
 **/
public enum  KingAccountTypeEnum {
    /**
     * 类型，income-收入，pay-支出，task-任务，reward-奖励
     */
    ACCOUNT_TYPE_INCOME("income", "收入"),
    ACCOUNT_TYPE_PAY("pay", "支出"),
    ACCOUNT_TYPE_TASK("task", "任务"),
    ACCOUNT_TYPE_REWARD("reward", "奖励"),
    ;
    private final String type;

    private final String name;

    KingAccountTypeEnum(String type, String name) {
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

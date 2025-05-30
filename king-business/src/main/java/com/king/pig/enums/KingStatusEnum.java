package com.king.pig.enums;

/**
 * @Program: king
 * @Description: 状态枚举
 * @Author: daiming5
 * @Date: 2021-06-04 15:41
 * @Version 1.0
 **/
public enum KingStatusEnum {
    /**
     * 状态0-停用，1-正常
     */
    STATUS_OFF(0, "停用"),
    STATUS_ON(1, "启用"),
    ;

    private final Integer code;

    private final String name;

    KingStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}

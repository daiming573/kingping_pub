package com.king.pig.enums;

/**
 * @Program: king
 * @Description: 删除标识枚举
 * @Author: daiming5
 * @Date: 2021-06-04 14:55
 * @Version 1.0
 **/
public enum DeleteEnum {


    /**
     * 是否删除，0-未删除，1-已删除
     */
    DELETED_NO(0, "未删除"),
    DELETED_YES(1, "已删除"),
    ;

    private final Integer code;

    private final String name;

    DeleteEnum(Integer code, String name) {
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

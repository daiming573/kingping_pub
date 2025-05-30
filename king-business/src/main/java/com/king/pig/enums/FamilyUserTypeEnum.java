package com.king.pig.enums;

import org.springframework.util.ObjectUtils;

/**
 * @Program: king
 * @Description: 家庭成员类型
 * @Author: daiming5
 * @Date: 2021-06-04 14:49
 * @Version 1.0
 **/
public enum FamilyUserTypeEnum {
    /**
     * 身份，F-父亲，M-母亲, C-孩子
     */
    FAMILY_USER_TYPE_FATHER("F", "爸爸"),
    FAMILY_USER_TYPE_MOTHER("M", "妈妈"),
    FAMILY_USER_TYPE_CHILD("C", "孩子"),

    ;

    private final String type;

    private final String name;

    FamilyUserTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据类型获取名称
     * @param type  类型
     * @return  String
     */
    public static String getNameByType(String type) {
        for (FamilyUserTypeEnum familyUserTypeEnum : FamilyUserTypeEnum.values()) {
            if (!ObjectUtils.isEmpty(type) && type.equals(familyUserTypeEnum.getType())) {
                return familyUserTypeEnum.getName();
            }
        }
        return null;
    }
}

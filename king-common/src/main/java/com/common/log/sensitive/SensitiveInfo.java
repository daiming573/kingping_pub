package com.common.log.sensitive;

import com.common.log.LogConstant;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 敏感信息注解，mode为脱敏模式，根据安全红线的要求主要有两种，全隐藏和隐藏中间30%
 * mode:full 全部隐藏
 * mode:mid 隐藏中间部分
 *
 * @author admin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveSerializer.class)
public @interface SensitiveInfo {

    /**
     * 隐藏模式，full-全部隐藏，mid-中间隐藏
     * @return  String
     */
    String mode() default LogConstant.MASK_MODE_FULL;
}

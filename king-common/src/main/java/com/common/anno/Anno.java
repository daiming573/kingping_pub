package com.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 做日志切面需要用到的注解类
 * 在需要些备注的地方加入注解
 *
 * @author admin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Anno {

    /**
     * 拦截日志 接口方法描述
     *
     * @return String
     */
    String value() default "";
}
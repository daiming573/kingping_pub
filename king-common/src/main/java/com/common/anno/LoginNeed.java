package com.common.anno;

import java.lang.annotation.*;

/**
 * 用来验证是否需要验证登录的接口
 *
 * @author admin
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginNeed {

    /**
     * 是否需要登录，默认需要登录
     * @return  boolean
     */
    boolean value() default true;
}
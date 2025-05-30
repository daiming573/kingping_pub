package com.common.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配合RestServiceInterceptor使用，安全认证注解。
 * <ul>
 *      <li>属性 valid 是否使用token校验</li>
 *      <li>属性 tamperProofingFields 防篡改字段</li>
 * </ul>
 * @author daiming5
 */
@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RestAuthAnnotation {

    /**
     * 是否校验
     *
     * @return boolean
     */
    boolean valid() default true;

    /**
     * 防篡改字段
     *
     * @return String
     */
    String[] tamperProofingFields() default {};


}
package com.common.log;

import com.common.util.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 1. Title: RequestAopLogComponent
 * 2. Description: 统一参数打印
 * 3.
 * 4. @author gejx
 * 5. @version V1.0
 * 6. @date 2019-05-25
 */
@Slf4j
@RestControllerAdvice
public class ReqAopLogComponent implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        //判断是否有此注解
        //        boolean b = methodParameter.getParameterAnnotation(RequestBody.class) != null;
        //只有为true时才会执行afterBodyRead
        //        return b;
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        RequestMapping requestMapping = parameter.getMethodAnnotation(RequestMapping.class);
        //        log.info("请求地址====>{}", StringUtils.arrayToDelimitedString(requestMapping.value(), ","));
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        RequestMapping requestMapping = parameter.getMethodAnnotation(RequestMapping.class);
        log.info("afterBodyRead 请求地址={},请求参数={}", StringUtils.arrayToDelimitedString(requestMapping.value(), ","), JsonUtil.toJson(body));
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        RequestMapping requestMapping = parameter.getMethodAnnotation(RequestMapping.class);
        log.info("handleEmptyBody 请求地址={},请求参数={}", StringUtils.arrayToDelimitedString(requestMapping.value(), ","), JsonUtil.toJson(body));
        return body;
    }
}
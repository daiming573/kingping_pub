package com.common.log;

import com.common.util.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Title: RequestAopLogComponent
 * Description: 统一返回值日志打印
 *
 * @author gejx
 * @version V1.0
 */
@Slf4j
@RestControllerAdvice
public class RespAopLogComponent implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //        if (body instanceof ApiResult) {
        //            Object o = JSON.toJSON(body);
        //            JSON.toJSONString(body);
        log.info("beforeBodyWrite 请求返回==>{}", JsonUtil.toJson(body));
        //        }
        return body;
    }
}
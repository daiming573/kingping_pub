package com.common.log.rest;/**
 *
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;

/**
 * @author daiming5
 * @date create in 2020/10/10 11:08
 * @description Resttemplate调用，异常返回处理。
 * @project rpo
 * @version 1.0
 * @since JDK 1.8
 *
 */
public class ErrorResponseHandler extends DefaultResponseErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorResponseHandler.class);

    @Override
    protected boolean hasError(HttpStatus statusCode) {
        boolean hasError = super.hasError(statusCode);
        if (!hasError) {
            //无异常，追踪日志记录接收成功，如果是业务发起方，则记录业务调用链结束
//            TraceRecordGenerator.clientReceiveSucceed();
            RequestHolder.clear();
        }
        return hasError;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpRequest request = RequestHolder.getRequest();
        String url = "";
        String method = "";
        if (request != null) {
            if (request.getMethod() != null) {
                method = request.getMethod().name();
            }
            url = request.getURI().toString();
        }
        //获取是否为业务发起方
        Boolean startFlag = RequestHolder.getStartFlag();
        RequestHolder.clear();
        try {
            super.handleError(response);
        } catch (Exception e) {
            //针对异常不同的处理机制，抛出默认错误码记录追踪个日志，如为业务发起方，则记录追踪日志结束
            if (e instanceof HttpServerErrorException) {
                logger.error(("HttpServerErrorException responseBody={}, status={}, url={}, method={}"), ((HttpServerErrorException) e).getResponseBodyAsString(), response.getStatusCode().toString(), url, method, e);
                throw e;
            } else if (e instanceof HttpClientErrorException) {
                logger.error(("HttpServerErrorException responseBody={}, status={}, url={}, method={}"), ((HttpClientErrorException) e).getResponseBodyAsString(), response.getStatusCode().toString(), url, method, e);
                throw e;
            } else {
                logger.error(("call request error, status={}, url={}, method={}"), response.getStatusCode().toString(), url, method, e);
                throw e;
            }
        }
    }

}

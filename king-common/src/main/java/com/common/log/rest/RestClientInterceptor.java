package com.common.log.rest;

import com.common.log.LogHelper;
import com.common.log.RequestIp;
import com.common.log.dto.ReqLogDto;
import com.common.util.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @Program: king
 * @Description: restTemplate 请求参数日志
 * @Author: daiming5
 * @Date: 2021-03-06 11:03
 * @Version 1.0
 *
 */
public class RestClientInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RestClientInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        RequestHolder.setRequest(request);
        //打印请求日志
        printRequestLog(request, body);

        HttpHeaders httpHeaders = request.getHeaders();
        //如果没有设置contentType，则设置默认为application/json
        MediaType contentType = httpHeaders.getContentType();
        if (contentType == null) {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        }

        //根源RestTemplate的doExecute方法在request.execute()时，如果发生了异常，则不会进入ErrorHandle中，所以需要这里捕获异常，并记录日志
        final ClientHttpResponse response;
        try {
            response = execution.execute(request, body);
        } catch (Exception e) {
            logger.error("call request error", e);
            RequestHolder.clear();
            //打印异常日志
            LogHelper.thirdExceptionResponseLog(e.getMessage());
            throw e;
        }
        printResponse(request, response);
        return response;
    }

    /**
     * 打印返回日志
     * @param request   请求
     * @param response  返回
     */
    private void printResponse(HttpRequest request, ClientHttpResponse response) {
        StringBuilder inputStringBuilder = new StringBuilder();
        try (InputStreamReader in = new InputStreamReader(response.getBody(), StandardCharsets.UTF_8)){
            BufferedReader bufferedReader = new BufferedReader(in);
            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuilder.append(line);
                inputStringBuilder.append('\n');
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            logger.error("get restTemplate response body error", e);
        }
        ReqLogDto reqLogDto = new ReqLogDto();
        reqLogDto.setHttpMethod(Objects.requireNonNull(request.getMethodValue()));
        reqLogDto.setIp(RequestIp.getRequestIp());
        reqLogDto.setUrl(Objects.toString(request.getURI()));
        reqLogDto.setHeads(request.getHeaders());
        reqLogDto.setResponse(inputStringBuilder.toString());
        LogHelper.thirdResponseLog(JsonUtil.toJson(reqLogDto));
    }

    /**
     * 打印请求日志
     * @param request   请求
     */
    private void printRequestLog(HttpRequest request, byte[] body) {
        try {
            //做好异常处理，避免记录日志造成请求不正确
            ReqLogDto reqLogDto = new ReqLogDto();
            reqLogDto.setHttpMethod(Objects.requireNonNull(request.getMethodValue()));
            reqLogDto.setIp(RequestIp.getRequestIp());
            reqLogDto.setUrl(Objects.toString(request.getURI()));
            reqLogDto.setHeads(request.getHeaders());
            reqLogDto.setReqestBody(new String(body, StandardCharsets.UTF_8));
            reqLogDto.setRequestQuery(request.getURI().getQuery());
            // 记录后台请求报文日志
            LogHelper.thirdRequestLog(JsonUtil.toJson(reqLogDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

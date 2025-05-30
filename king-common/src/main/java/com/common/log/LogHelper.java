package com.common.log;

import com.common.log.dto.LogContentDto;
import com.common.util.UUIDUtil;
import com.common.util.date.DateUtil;
import com.common.util.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;


public class LogHelper {

    /**
     * 对外HTTP接口调用
     */
    private static final Logger EXT_HTTP_LOG = LoggerFactory.getLogger(LogConstant.LOG_NAME);
    /**
     * 项目接口调用
     */
    private static final Logger INTER_LOG = LoggerFactory.getLogger(LogConstant.LOG_NAME);
    /**
     * 网页操作日志
     */
    private static final Logger WEB_LOG = LoggerFactory.getLogger(LogConstant.LOG_NAME);
    /**
     * 对外HTTP接口调用
     */
    private static final Logger THIRD_LOG = LoggerFactory.getLogger(LogConstant.LOG_NAME);

    /**
     * 第三方请求日志
     */
    private static final String THIRD_REQUEST_TIME = LogConstant.THIRD_ + LogConstant.REQUEST_TIME;
    private static final String THIRD_REQUEST = LogConstant.THIRD_ + LogConstant.REQUEST;
    private static final String THIRD_RESPONSE = LogConstant.THIRD_ + LogConstant.RESPONSE;
    private static final String THIRD_E_RESPONSE = LogConstant.THIRD_ + LogConstant.EXCEPTION_RESPONSE;

    /**
     * web网页请求日志
     */
    private static final String WEB_REQUEST_TIME = LogConstant.WEB_ + LogConstant.REQUEST_TIME;
    private static final String WEB_REQUEST = LogConstant.WEB_ + LogConstant.REQUEST;
    private static final String WEB_RESPONSE = LogConstant.WEB_ + LogConstant.RESPONSE;
    private static final String WEB_E_RESPONSE = LogConstant.WEB_ + LogConstant.EXCEPTION_RESPONSE;

    /**
     * 内部接口请求日志
     */
    private static final String INTER_REQUEST_TIME = LogConstant.INTER_ + LogConstant.REQUEST_TIME;
    private static final String INTER_REQUEST = LogConstant.INTER_ + LogConstant.REQUEST;
    private static final String INTER_RESPONSE = LogConstant.INTER_ + LogConstant.RESPONSE;
    private static final String INTER_E_RESPONSE = LogConstant.INTER_ + LogConstant.EXCEPTION_RESPONSE;

    public static void traceStart(String traceId) {
        ThreadLocalHelper.setVale(LogConstant.TRACE_ID, (ObjectUtils.isEmpty(traceId)) ? UUIDUtil.get32UUID() : traceId);
    }

    public static String getTraceId() {
        return ThreadLocalHelper.getVale(LogConstant.TRACE_ID);
    }

    /**
     * 获取日志内容
     *
     * @param logType     log类型
     * @param seq         序列号
     * @param datagram    内容
     * @param requestTime 请求返回时间
     * @return LogContentDto
     */
    private static LogContentDto getLogContent(String logType, String seq, String datagram, Long requestTime) {
        LogContentDto logContent = new LogContentDto();
        logContent.setRequestTime(DateUtil.getCurrentTimeSssStr());
        logContent.setLogType(logType);
        logContent.setLogSeq(seq);
        logContent.setDatagram(limitStr(datagram));
        if (null != requestTime) {
            long nowTime = System.currentTimeMillis();
            logContent.setIntervalTime(getTimeLow(nowTime, requestTime));
        }
        //记录接口请求序列号
        logContent.setTraceId(getTraceId());
        return logContent;
    }

    public static void thirdRequestLog(String datagram) {
        ThreadLocalHelper.setVale(THIRD_REQUEST_TIME, System.currentTimeMillis());
        LogContentDto logContent = getLogContent(THIRD_REQUEST, getLogSeq(), datagram, null);
        THIRD_LOG.info(JsonUtil.toJson(logContent));
    }

    public static void thirdResponseLog(String datagram) {
        Long extTime = ThreadLocalHelper.getVale(THIRD_REQUEST_TIME);
        LogContentDto logContent = getLogContent(THIRD_RESPONSE, ThreadLocalHelper.getSeq(), datagram, extTime);
        THIRD_LOG.info(JsonUtil.toJson(logContent));
    }

    public static void thirdExceptionResponseLog(String datagram) {
        Long extTime = ThreadLocalHelper.getVale(THIRD_REQUEST_TIME);
        LogContentDto logContent = getLogContent(THIRD_E_RESPONSE, ThreadLocalHelper.getSeq(), datagram, extTime);
        //异常 不限制
        logContent.setDatagram(datagram);
        THIRD_LOG.info(JsonUtil.toJson(logContent));
    }

    public static void webRequestLog(String datagram) {
        ThreadLocalHelper.setVale(WEB_REQUEST_TIME, System.currentTimeMillis());
        LogContentDto logContent = getLogContent(WEB_REQUEST, ThreadLocalHelper.setSeq(), datagram, null);
        WEB_LOG.info(JsonUtil.toJson(logContent));
    }

    public static void webResponseLog(String datagram) {
        long webTime = ThreadLocalHelper.getVale(WEB_REQUEST_TIME);
        LogContentDto logContent = getLogContent(WEB_RESPONSE, ThreadLocalHelper.getSeq(), datagram, webTime);
        WEB_LOG.info(JsonUtil.toJson(logContent));
    }

    public static void webExceptionResponseLog(String datagram) {
        long webTime = ThreadLocalHelper.getVale(WEB_REQUEST_TIME);
        LogContentDto logContent = getLogContent(WEB_E_RESPONSE, ThreadLocalHelper.getSeq(), datagram, webTime);
        logContent.setDatagram(datagram);
        WEB_LOG.info(JsonUtil.toJson(logContent));
    }

    public static void interRequestLog(String datagram) {
        ThreadLocalHelper.setVale(INTER_REQUEST_TIME, System.currentTimeMillis());
        //接口从前端第一次进入记录日志信息
        LogContentDto logContent = getLogContent(INTER_REQUEST, ThreadLocalHelper.setSeq(), datagram, null);
        INTER_LOG.info(JsonUtil.toJson(logContent));
    }

    public static void interResponseLog(String datagram) {
        long interTime = ThreadLocalHelper.getVale(INTER_REQUEST_TIME);
        LogContentDto logContent = getLogContent(INTER_RESPONSE, ThreadLocalHelper.getSeq(), datagram, interTime);
        INTER_LOG.info(JsonUtil.toJson(logContent));
    }

    public static void interExceptionResponseLog(String datagram) {
        long interTime = ThreadLocalHelper.getVale(INTER_REQUEST_TIME);
        LogContentDto logContent = getLogContent(INTER_E_RESPONSE, ThreadLocalHelper.getSeq(), datagram, interTime);
        //异常 不限制
        logContent.setDatagram(datagram);
        INTER_LOG.info(JsonUtil.toJson(logContent));
    }

    /**
     * 日志长度超限截取
     *
     * @param datagram 日志内容
     * @return String
     */
    private static String limitStr(String datagram) {
        return String.valueOf(datagram).length() > LogConstant.LIMIT_INT ? datagram.substring(0, LogConstant.LIMIT_INT) + "..." : datagram;
    }

    /**
     * 获取请求流水号
     *
     * @return String
     */
    private static String getLogSeq() {
        return ObjectUtils.isEmpty(ThreadLocalHelper.getSeq()) ? ThreadLocalHelper.setSeq() : ThreadLocalHelper.getSeq();
    }

    /**
     * 获取事件差值，请求时间毫秒
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @return String
     */
    private static String getTimeLow(long nowTime, long startTime) {
        //相差毫秒数
        long temp = nowTime - startTime;
        float d = (float) temp / 1000;
        //相差分钟数
        return new java.text.DecimalFormat("0.000").format(d);
    }

}



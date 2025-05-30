package com.common.log.dto;

import lombok.Data;

/**
 * @Program: king
 * @Description: 日志打印内容
 * @Author: daiming5
 * @Date: 2021-03-19 16:50
 * @Version 1.0
 **/
public class LogContentDto {

    /** 请求时间 */
    String requestTime;

    /** 日志类型 */
    String logType;

    /** 用于表示一笔业务的唯一序号 */
    String traceId;

    /** 日志流水 */
    String logSeq;

    /** 接口响应时间间隔 */
    String intervalTime;

    /** 日志内容 */
    String datagram;

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getLogSeq() {
        return logSeq;
    }

    public void setLogSeq(String logSeq) {
        this.logSeq = logSeq;
    }

    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getDatagram() {
        return datagram;
    }

    public void setDatagram(String datagram) {
        this.datagram = datagram;
    }

    @Override
    public String toString() {
        return "LogContentDto{" +
                "requestTime='" + requestTime + '\'' +
                ", logType='" + logType + '\'' +
                ", traceId='" + traceId + '\'' +
                ", logSeq='" + logSeq + '\'' +
                ", intervalTime='" + intervalTime + '\'' +
                ", datagram='" + datagram + '\'' +
                '}';
    }
}

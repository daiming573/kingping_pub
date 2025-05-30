package com.cat.bean;


import com.common.enums.EMsg;
import com.common.exception.RequestException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 业务请求返回消息基类
 */
public class FMsgResponse implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作结果返回码，0表示成功，小于0表示失败
     */
    private int code = 0;

    /**
     * 认证出错信息，如果result<0，会有相应的错误信息提示，返回数据全部用户UTF-8编码
     */
    private String msg;

    /**
     * 响应数据内容
     */
    private Object response;

    /**
     * 系统日期
     */
    private String systemDate;

    FMsgResponse() {
    }

    public FMsgResponse(EMsg emg) {
        this.setCode(emg.code());
        this.setMsg(emg.value());
    }

    public FMsgResponse(EMsg emg, String msg) {
        this.setCode(emg.code());
        this.setMsg(msg);
    }

    public FMsgResponse(EMsg emg, Object response) {
        this.setCode(emg.code());
        this.setMsg(emg.value());
        this.setResponse(response);
    }

    public FMsgResponse(int code, String msg) {
        this.setCode(code);
        this.setMsg(msg);
    }

    public FMsgResponse(int code, String msg, Object response) {
        this.setCode(code);
        this.setMsg(msg);
        this.setResponse(response);
    }

    public FMsgResponse(RequestException e) {
        this.setCode(e.getCode());
        this.setMsg(e.getMsg());
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public String getSystemDate() {
        SimpleDateFormat _format10 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        systemDate = _format10.format(new Date());
        return systemDate;
    }

    public void setSystemDate(String systemDate) {
        this.systemDate = systemDate;
    }

}

package com.common.exception;


import com.common.enums.EMsg;

/**
 * 请求异常
 */
public class RequestException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    public RequestException() {
        super();
    }

    public RequestException(int errorCode, String message) {
        super(message);
        this.setCode(errorCode);
        this.setMsg(message);
    }

    public RequestException(EMsg emsg) {
        super(emsg.value());
        this.setCode(emsg.code());
        this.setMsg(emsg.value());
    }

    public RequestException(EMsg emsg, String msg) {
        super(msg);
        this.setCode(emsg.code());
        this.setMsg(msg);
    }

    public RequestException(int errorCode, String message, Throwable cause) {
        super(message);
        this.setCode(errorCode);
        this.setMsg(message);
    }

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestException(Throwable cause) {
        super(cause);
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

}

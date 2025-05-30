package com.common.exception;


import com.common.errorcode.IErrorCode;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private IErrorCode errorCode;

    private String code;

    private Object[] params;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(IErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
        this.code = errorCode.getCode();
    }

    public BusinessException(IErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.code = errorCode.getCode();
    }

    public BusinessException(IErrorCode errorCode, Throwable cause) {
        super(errorCode.getMsg(), cause);
        this.errorCode = errorCode;
        this.code = errorCode.getCode();
    }

    public BusinessException(IErrorCode errorCode, String message, Object[] params) {
        super(message);
        this.errorCode = errorCode;
        this.code = errorCode.getCode();
        this.params = params;
    }

    public BusinessException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.setCode(code);
    }

    public BusinessException(String code, String message, Object[] params) {
        super(message);
        this.setCode(code);
        this.params = params;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public IErrorCode getErrorCode() { return errorCode; }

    public void setErrorCode(IErrorCode errorCode) { this.errorCode = errorCode; }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}

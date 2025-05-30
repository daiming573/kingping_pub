package com.common.exception;

import com.common.response.PrettyResponse;
import com.common.errorcode.DefaultErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * <p>
 * 统一异常处理
 * </p>
 *
 * @version V1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 拦截服务器内部错误N
     *
     * @param ex
     * @param body
     * @param headers
     * @param status
     * @param request
     *
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleExceptionInternal", ex);
        return new ResponseEntity<Object>(DefaultErrorCode.ERROR.createResponse(), status);
    }

    /**
     * 参数丢失
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     *
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("parameterMissing", ex);
        return new ResponseEntity<Object>(DefaultErrorCode.REQUEST_MISSING_REQUEST_PARAMETER.createResponse(), status);
    }

    /**
     * 类型不匹配
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     *
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("methodTypeNotSupported", ex);
        return new ResponseEntity<Object>(DefaultErrorCode.REQUEST_TYPE_MISMATCH.createResponse(), status);
    }

    /**
     * 接口未提供
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     *
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("methodNotSupported", ex);
        return new ResponseEntity<Object>(DefaultErrorCode.REQUEST_METHOD_ERROR.createResponse(), status);
    }

    /**
     * 找不到处理器
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     *
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handlerNotSupported", ex);
        return new ResponseEntity<Object>(DefaultErrorCode.REQUEST_NOT_FOUND.createResponse(), status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(DefaultErrorCode.VALIDATE_ERROR.getMsg(), ex);
        BindingResult result = ex.getBindingResult();
        log.error("", "errorObject", result.getObjectName());

        String errMsg = "";
        if (!ObjectUtils.isEmpty(result.getAllErrors())) {
            StringBuilder stringBuilder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                log.error("", "errorItem", error.toString());

                stringBuilder.append(error.getDefaultMessage()).append(";");
            }
            errMsg = stringBuilder.toString().substring(0, stringBuilder.length() - 1);
        }
        PrettyResponse response = DefaultErrorCode.VALIDATE_ERROR.createResponse();
        if (!ObjectUtils.isEmpty(errMsg)) {
            response.setMessage(errMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * JSON转换失败
     *
     * @param ex
     *
     * @return
     */
//    @ResponseBody
//    @ExceptionHandler(JsonExpression.class)
//    public PrettyResponse jsonException(JSONException ex) {
//        log.error("jsonException", ex);
//        //        Throwable cause = ex.getCause();
//        //        if(cause instanceof NumberFormatException){
//        //            return energyException(new EnergyException(CommonCodeWrapper.DATA_PARSE_ERROR));
//        //        }
//        return PrettyResponseWrapper.VALIDATE_ERROR.createResponse();
//    }

    /**
     * 非法参数异常捕获
     *
     * @param ex
     *
     * @return
     */
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public PrettyResponse illegalArgumentException(IllegalArgumentException ex) {
        log.error("illegalArgumentException", ex);
        return DefaultErrorCode.VALIDATE_ERROR.createResponse();
    }

    /**
     * 处理校验异常
     *
     * @param e
     *
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public PrettyResponse constraintViolationException(ConstraintViolationException e) {
        log.error(DefaultErrorCode.VALIDATE_ERROR.getMsg(), e);

        String errMsg = "";
        StringBuilder stringBuilder = new StringBuilder();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation item : constraintViolations) {
            log.error("Item:" + item.getPropertyPath().toString() + "  message:" + item.getMessage());
            stringBuilder.append(item.getMessageTemplate() + ";");
        }
        errMsg = stringBuilder.toString().substring(0, stringBuilder.length() - 1);
        PrettyResponse response = DefaultErrorCode.VALIDATE_ERROR.createResponse();
        response.setMessage(errMsg);

        return response;
    }

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public PrettyResponse handlerBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        PrettyResponse response = new PrettyResponse(e.getCode(), e.getMessage());
        String otherMsg = e.getMessage();
        if (!ObjectUtils.isEmpty(otherMsg)) {
            response.setMsg(otherMsg);
        }

        return response;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public PrettyResponse exception(Exception e) {
        log.error(DefaultErrorCode.ERROR.getMsg(), e);
        String errMsg = "";
        PrettyResponse response = DefaultErrorCode.ERROR.createResponse();
        return response;
    }
}

package com.common.response;

import com.common.response.converter.DefaultPageDataConvert;
import com.common.response.converter.IPageConverter;
import com.common.errorcode.IErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author
 * @version V1.0
 */
@SuppressWarnings("unchecked")
@ApiModel
public class PrettyResponse<T> {
    private static IPageConverter PAGE_CONVERTER = new DefaultPageDataConvert();

    @ApiModelProperty(value = "错误码")
    private String code;
    @ApiModelProperty("提示信息")
    private String msg;
    @ApiModelProperty("具体数据")
    private T data;

    public PrettyResponse() {
    }

    public PrettyResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public PrettyResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public PrettyResponse(IErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setList(List<T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        ListData<T> data = new ListData<>(list);
        this.setData((T) data);
    }

    public static void setPageConverter(IPageConverter pageConverter) {
        PAGE_CONVERTER = pageConverter;
    }

    public void setPageData(Object pageDataSrc) {
        this.setData((T) PAGE_CONVERTER.convertPageData(pageDataSrc));
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PrettyResponse<T> code(String code) {
        this.setCode(code);
        return this;
    }

    public PrettyResponse<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    public PrettyResponse<T> data(T data) {
        this.setData(data);
        return this;
    }

    public PrettyResponse<T> pageData(Object pageBean) {
        this.setPageData(pageBean);
        return this;
    }

    public PrettyResponse<T> listData(List<T> listData) {
        this.setList(listData);
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

}
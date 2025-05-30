package com.common.errorcode;

import com.common.response.PrettyResponse;

/**
 * <p>
 * </p>
 *
 */
public enum KingErrorCode implements IErrorCode {

    /**
     * 业务错误码
     * 0x002 标识码
     * 低两位（1-失败，3-异常，0-不区分）
     * 3-系统默认错误码，  0x00231001
     * 30-人员模块，31-
     */

    ADD_USER_INFO_ERROR("0x00203001", "添加用户信息失败"),
    ADD_USER_CHILD_INFO_ERROR("0x00203002", "添加孩子账户失败"),
    USER_CHILD_ACCOUNT_PAY_ERROR("0x00203003", "孩子账户支出失败，或余额不足"),
    USER_CHILD_ACCOUNT_ADD_ERROR("0x00203004", "孩子账户添加收入失败"),
    USER_PARENT_CHILD_MAP_ERROR("0x00203005", "孩子账户ID错误，孩子账户未与您绑定"),
    CHLD_ACCOUNT_ID_ERROR("0x00203006", "任务ID错误，任务不存在"),
    CHILD_PRIZE_GET_REPEAT_ERROR("0x00203007", "请勿重复领取奖励"),
    ADD_FAMILY_INFO_ERROR("0x00203008", "添加家庭失败"),
    UPDATE_USER_INFO_ERROR("0x00203009", "修改用户信息失败"),
    BIND_FAMILY_USER_ERROR("0x00203010", "绑定家庭用户失败"),
    ;

    private final String code;

    private final String msg;

    private KingErrorCode(IErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMsg());
    }

    private KingErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }


    @Override
    public <T> PrettyResponse<T> createResponse() {
        return new PrettyResponse<>(this.code, this.msg);
    }

    @Override
    public <T> PrettyResponse<T> createResponse(T data) {
        return new PrettyResponse<T>(this.code, this.msg, data);
    }

}

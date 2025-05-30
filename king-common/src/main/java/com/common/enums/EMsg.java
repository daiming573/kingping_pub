package com.common.enums;

public enum EMsg {
    /*****************系统信息************************/
    Success(0, "成功"),
    Fail(-1, "系统异常，请稍后重试"),
    Login_Fail(-2, "登录失败"),
    App_Id_NUll(-3, "AppId不能为空"),
    App_Id_Wrong(-4, "AppId不正确"),
    Aec_Wrong(-5, "解密失败"),
    Token_Invalid(-6, "您的账户在其他地方登录，请重新登录"),
    Sys_error(-7, "系统异常"),

    User_Sys_Error(-1001, "用户系统异常，请稍后重试"),
    Sys_Error(-1002, "系统异常，请稍后重试"),

    Check_Code_Error(-2001, "图片验证码不正确"),
    User_Msg_Error(-2007, "获取人员信息失败！"),

    Json_Change_Error(-4001, "Json转换异常"),

    Param_Not_Right(-200, "参数不正确"),
    Param_NULL(-201, "必要参数为空值"),
    Param_NotFount(-202, "必要参数为空值"),

    End(1, "结束");

    private int code;

    private String value;

    private EMsg(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public String value() {
        return value;
    }

    public int code() {
        return code;
    }

}

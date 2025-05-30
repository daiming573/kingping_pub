package com.cat.bean;


public class RResult {

    /**
     * S成功
     */
    public final static String MSG_SUCCESS = "S";
    /**
     * F失败
     */
    public final static String MSG_FAIL = "F";

    private static String successMsg = "处理成功.";

    private static String failMsg = "处理失败.";

    public static String paramNull = "{0}参数为空.";

    public static String recordNotExist = "记录不存在, 参数[{0}].";
    /**
     * 返回结果状态(true成功, false失败)
     */
    private boolean success = true;

    /**
     * 返回结果代码(0表示成功代码)
     */
    private String code;

    /**
     * 返回结果内容
     */
    private String note;

    /**
     * 返回结果
     *
     * @param code 代码
     * @param message 消息模板
     * @param params 消息参数
     */
    public RResult(String code, String message, Object... params) {
        setCode(code);
        if (code.equals("S")) {
            setSuccess(true);
        } else {
            setSuccess(false);
        }
        setNote(String.format(message, params));
    }

    public RResult(String code) {
        setCode(code);
        if (code.equals("S")) {
            setSuccess(true);
            setNote(successMsg);
        } else {
            setSuccess(false);
            setNote(failMsg);
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}

package com.common.log;

import java.util.regex.Pattern;

/**
 * @author daiming5
 * @version 1.0
 * @since JDK 1.8
 */
public class LogConstant {

    /**
     * 请求唯一序列号，可跨请求，返回给response的请求head中
     */
    public static final String TRACE_ID = "traceId";

    /** 应用ID */
    public static final String APP_ID = "appId";

    /** 写入的日志名称 */
    public static final String LOG_NAME = "filteLog";

    /** 日志字符串长度 */
    public static final int LIMIT_INT = 3000;

    /** 请求时间 */
    public static final String REQUEST_TIME = "request_time";


    public static final String REQUEST = "request";

    public static final String RESPONSE = "response";

    public static final String EXCEPTION_RESPONSE = "e_response";

    public static final String THIRD_ = "third_";

    public static final String WEB_ = "web_";

    public static final String INTER_ = "inter_";

    /**
     * 全部隐藏
     */
    public static final String MASK_MODE_FULL = "full";

    /**
     * 隐藏中间部分
     */
    public static final String MASK_MODE_MID = "mid";

    /**
     * IP正则表达式
     */
    public static final String IP_REGEXP = "^((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))$";

    /**
     * 手机号码正则表达式
     */
    public static final String PHONE_REGEXP = "^(\\+86|86)?1((3[0-9])|(4[5|7|9])|(5([0-3]|[5-9]))|(6[6])|(7[0|1|3|5|6|7|8])|(8[0-9])|(9[8|9]))\\d{8}$";

    /**
     * 邮箱正则表达式
     */
    public static final String EMAIL_REGEXP = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

    /**
     * 域名正则表达式,可匹配域名、完整url、带参的完整url
     */
    public static final String DNS_REGEXP = "^(?=^.{3,255}$)(http(s)?:\\/\\/)?(www\\.)?[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+(:\\d+)*(\\/\\w+(\\.)?\\w+)*([\\?&]\\w+=\\w*)*$";

    public static final Pattern IP_PATTERN = Pattern.compile(IP_REGEXP);

    public static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEXP);

    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEXP);

    public static final Pattern DNS_PATTERN = Pattern.compile(DNS_REGEXP);
}

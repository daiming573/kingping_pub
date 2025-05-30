package com.common.constant;

/**
 * @Program: king
 * @Description: 通用常量
 * @Author: daiming5
 * @Date: 2021-03-20 17:30
 * @Version 1.0
 **/
public class CommonConstant {

    /** 请求token */
    public static final String HEAD_TOKEN = "Token";

    /**  ThreadLocal缓存请求流水号 */
    public static final String REQUEST_SEQ = "request_seq";

    /** 请求IP */
    public static final String REQUEST_IP = "request_ip";

    /** ip4正则表达式*/
    public static final String IP_REGEX = "((25[0-5]|2[0-4]\\d|1\\d{2}|0?[1-9]\\d|0?0?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|0?[1-9]\\d|0?0?\\d)";

    /** 联系方式正则，支持格式示例-固话：+86-010-40020021，010-40020020 国家代码选填；手机： +86-13523458056 ，10-13523458056 ，13523458056 国家代码和区号选填*/
    public static final String REGEXP_CONTACT_TYPE_EX= "^(((\\+\\d{2}-)?0\\d{2,3}-\\d{7,8})|((\\+\\d{2}-)?(\\d{2,3}-)?([1][3,4,5,6,7,8,9][0-9]\\d{8})))$";


}

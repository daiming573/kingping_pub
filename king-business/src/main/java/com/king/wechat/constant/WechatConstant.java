package com.king.wechat.constant;

/**
 * @Program: king
 * @Description: 微信模块常量
 * @Author: daiming5
 * @Date: 2021-03-06 11:03
 * @Version 1.0
 **/
public class WechatConstant {

    /** 微信accessToken 刷新url */
    public static final String ACCESS_TOKEN_URL = "/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}";
    /** 获取用户openId接口 */
    public static final String GET_OPEN_ID_URL = "/sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type=authorization_code";

    /** accessToken后缀*/
    public static final String ACCESS_TOKEN_PREFIX = "AccessToken";
    /** 数据库一个半小时过期 */
    public static final long ACCESS_TOKEN_VALIDATE = 5400 * 1000;

    /** session key AES加解密秘钥*/
    public static final String KEY_FOR_SESSION_KEY = "king123pig456app";

}

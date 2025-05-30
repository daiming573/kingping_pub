package com.king.wechat.service;

import com.baomidou.mybatisplus.service.IService;
import com.king.wechat.entity.WxEnv;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author daiming
 * @since 2019-11-22
 */
public interface WxEnvService extends IService<WxEnv> {

    /**
     * 查询变量字典值
     * @param envKey    环境量key
     * @return  WxEnv
     */
    WxEnv getWxEnvByKey(String envKey);

    /**
     * 保存数据库
     * @param accessTokenKey  key
     * @param accessToken   token
     * @param validTime   过期时间
     * @return  boolean
     */
    boolean saveOrUpdateEvn(String accessTokenKey, String accessToken, Date validTime);

}

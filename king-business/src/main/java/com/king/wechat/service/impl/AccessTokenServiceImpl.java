package com.king.wechat.service.impl;

import com.common.errorcode.WechatErrorCode;
import com.common.exception.BusinessException;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.king.wechat.constant.WechatConstant;
import com.king.wechat.dto.resp.WechatAccessTokenResp;
import com.king.wechat.entity.WxEnv;
import com.king.wechat.entity.WxMiniappList;
import com.king.wechat.service.AccessTokenService;
import com.king.wechat.service.WxEnvService;
import com.king.wechat.service.WxMiniappListService;
import com.king.wechat.third.IWechatService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 微信accessToken有效期为2小时，通过定时任务两小时定时刷新token，并入库
 * 使用时将token取至本地缓存4分钟，缓存为空去数据库查，或数据库为空或
 *
 * @author daiming5
 */
@Slf4j
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    private WxEnvService wxEnvService;

    @Autowired
    private WxMiniappListService wxMiniappListService;

    @Autowired
    private IWechatService wechatService;

    @Autowired
    private RedisTemplate redisTemplate;

    Interner<String> interner = Interners.newWeakInterner();

    /**
     * accessToken本地缓存
     */
    Cache<String, String> accessTokenCache = Caffeine.newBuilder()
            // 数量上限
            .maximumSize(1024)
            // 过期机制 5分钟
            .expireAfterWrite(5, TimeUnit.MINUTES)
            // 弱引用value
            .weakValues()
            .build();

    /**
     * 缓存获取token
     */
//	@Cacheable(cacheNames = "usrTkn")
    @Override
    public String getMiniAppAccessToken(String type) {
        try {
            String accessToken = loadAccessTokenFromCache(type);
            if (ObjectUtils.isEmpty(accessToken)) {
                return getMiniAppAccessTokenFromDb(type);
            }
            return accessToken;
        } catch (Exception e) {
            log.error(WechatErrorCode.MINIAPP_GET_ACCESS_TOKEN_ERROR.getMsg(), e);
            throw new BusinessException(WechatErrorCode.MINIAPP_GET_ACCESS_TOKEN_ERROR);
        }
    }

    /**
     * 数据库获取
     *
     * @return String
     */
    public String getMiniAppAccessTokenFromDb(String type) {
        String accessTokenKey = createAccessTokenKey(type);
        try {
            synchronized (interner.intern(type)) {
                String accessToken = loadAccessTokenFromCache(type);
                if (!ObjectUtils.isEmpty(accessToken)) {
                    return accessToken;
                }
                // 查询数据库
                WxEnv env = wxEnvService.getWxEnvByKey(accessTokenKey);
                if (env == null || null == env.getInvalidTime() || env.getInvalidTime().before(new Date())) {
                    return null;
                }
                // token为过期
                updateAccessTokenCache(accessTokenKey, env.getEnvValue());
                return env.getEnvValue();
            }
        } catch (Exception e) {
            log.error(WechatErrorCode.MINIAPP_GET_ACCESS_TOKEN_ERROR.getMsg(), e);
            throw new BusinessException(WechatErrorCode.MINIAPP_GET_ACCESS_TOKEN_ERROR);
        }
    }

    /**
     * 刷新token
     *
     * @return String
     */
    @Override
    public String refreshMiniAppAccessToken(String type, String appId, String appSecret) {
        if (StringUtils.isAnyBlank(appId, appSecret)) {
            throw new BusinessException(WechatErrorCode.MINIAPP_APPID_SECRET_ERROR);
        }
        String accessTokenKey = createAccessTokenKey(type);
        String accessToken = null;
        synchronized (interner.intern(type)) {
            if (!ObjectUtils.isEmpty(accessToken)) {
                return accessToken;
            }
            WechatAccessTokenResp accessTokenResp = wechatService.getAccessToken(appId, appSecret);
            accessToken = accessTokenResp.getAccessToken();
            if (ObjectUtils.isEmpty(accessToken)) {
                log.debug("get access token is null");
                return null;
            }

            updateAccessTokenCache(accessTokenKey, accessToken);
            // 保存数据库
            wxEnvService.saveOrUpdateEvn(accessTokenKey, accessToken, getValidTime());
        }
        return accessToken;
    }

    /**
     * 初始化、定时刷新小程序token
     */
    @Async("taskExecutor")
    @Override
    public void refreshMiniAppAccessToken() {
        try {
            List<WxMiniappList> appList = wxMiniappListService.selectMiniappList();
            if (null != appList && appList.size() > 0) {
                for (WxMiniappList wxMiniApp : appList) {
                    // 初始化数据库的 token 到缓存中
                    if (ObjectUtils.isEmpty(getMiniAppAccessTokenFromDb(wxMiniApp.getMiniappCode()))) {
                        // 数据库的为空，或者已过期
                        refreshMiniAppAccessToken(wxMiniApp.getMiniappCode(), wxMiniApp.getAppId(), wxMiniApp.getAppSecret());
                    }
                }
            }
        } catch (Exception e) {
            log.error(WechatErrorCode.MINIAPP_GET_ACCESS_TOKEN_ERROR.getMsg(), e);
        }
    }

    /**
     * 缓存中获取accessToken
     *
     * @param type 类型
     * @return String
     */
    private String loadAccessTokenFromCache(String type) {
        String accessTokenKey = createAccessTokenKey(type);
        return accessTokenCache.getIfPresent(accessTokenKey);
    }

    /**
     * 更新缓存accessToken
     *
     * @param accessTokenKey token缓存key
     * @param accessToken    token
     */
    private void updateAccessTokenCache(String accessTokenKey, String accessToken) {
        accessTokenCache.put(accessTokenKey, accessToken);
    }

    /**
     * 生成token过期时间，数据库一个半小时过期
     *
     * @return Timestamp
     */
    private Timestamp getValidTime() {
        return new Timestamp(System.currentTimeMillis() + WechatConstant.ACCESS_TOKEN_VALIDATE);
    }

    /**
     * 根据类型获取accessToken名称
     *
     * @param type 类型
     * @return String
     */
    private String createAccessTokenKey(String type) {
        return type + WechatConstant.ACCESS_TOKEN_PREFIX;
    }

    public static void main(String[] args) {
        String appId = "wxbb59b4862d99c420";
        String secret = "b660fcb27c88c293b558c0c64e36ef1c";
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + secret;

        HttpEntity<Object> httpEntity = new HttpEntity(null);
        ParameterizedTypeReference<Object> parameTypeReference = new ParameterizedTypeReference<Object>() {
        };
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> resp = restTemplate.exchange(url, HttpMethod.GET, httpEntity, parameTypeReference);
        System.out.println(resp);
    }

}

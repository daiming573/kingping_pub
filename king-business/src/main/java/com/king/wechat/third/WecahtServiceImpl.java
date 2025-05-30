package com.king.wechat.third;

import com.common.errorcode.WechatErrorCode;
import com.common.exception.BusinessException;
import com.king.wechat.constant.WechatConstant;
import com.king.wechat.dto.resp.WechatAccessTokenResp;
import com.king.wechat.dto.resp.WechatOpenInfoResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @Program: king
 * @Description: 微信接口逻辑
 * @Author: daiming5
 * @Date: 2021-03-17 15:42
 * @Version 1.0
 **/
@Slf4j
@Service
public class WecahtServiceImpl implements IWechatService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${wechat.server.url:https://api.weixin.qq.com}")
    private String wechatServerUrl;

    @Override
    public WechatAccessTokenResp getAccessToken(String appId, String appSecret) {
        WechatAccessTokenResp resp = getForWechat(WechatConstant.ACCESS_TOKEN_URL, WechatAccessTokenResp.class, appId, appSecret);
        if (null == resp || ObjectUtils.isEmpty(resp.getAccessToken()) || !ObjectUtils.isEmpty(resp.getErrCode())) {
            throw new BusinessException(WechatErrorCode.MINIAPP_GET_ACCESS_TOKEN_ERROR);
        }
        return getForWechat(WechatConstant.ACCESS_TOKEN_URL, WechatAccessTokenResp.class, appId, appSecret);
    }


    @Override
    public WechatOpenInfoResp getOpenInfo(String code, String miniAppId, String miniAppSecret) {
        return getForWechat(WechatConstant.GET_OPEN_ID_URL, WechatOpenInfoResp.class, miniAppId, miniAppSecret, code);
    }


    /**
     * 微信接口get请求
     * @param url   接口地址
     * @param responseType  返回参数类型
     * @param uriVariables  url传参
     * @param <T>   类型
     * @return  T
     */
    private <T> T getForWechat(String url, Class<T> responseType, Object... uriVariables) {
        if (ObjectUtils.isEmpty(url)) {
            return null;
        }
//        ResponseEntity<T> resp = restTemplate.exchange(wechatServerUrl + url, HttpMethod.GET, null, responseType, uriVariables);
        ResponseEntity<T> resp = restTemplate.getForEntity(wechatServerUrl + url, responseType, uriVariables);
        if (!HttpStatus.OK.equals(resp.getStatusCode()) || ObjectUtils.isEmpty(resp.getBody())) {
            log.debug(WechatErrorCode.WECHAT_INTERFACE_ERROR.getMsg());
            throw new BusinessException(WechatErrorCode.WECHAT_INTERFACE_ERROR);
        }
        return resp.getBody();
    }
}

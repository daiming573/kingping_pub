package com.king.wechat.service.impl;

import com.common.errorcode.WechatErrorCode;
import com.common.exception.BusinessException;
import com.common.safe.AesUtils;
import com.common.util.json.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.king.wechat.constant.WechatConstant;
import com.king.wechat.dto.resp.WechatOpenInfoResp;
import com.king.wechat.dto.resp.WechatUserInfoResp;
import com.king.wechat.entity.WxMiniappList;
import com.king.wechat.service.MiniappUserService;
import com.king.wechat.service.WxMiniappListService;
import com.king.wechat.third.IWechatService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author daiming
 * @version 1.0
 * @date 2019-11-24
 * @Description:
 */
@Slf4j
@Service
public class MiniappUserServiceImpl implements MiniappUserService {

    @Autowired
    private WxMiniappListService wxMiniappListService;

    @Autowired
    private IWechatService wechatService;

    @Override
    public WechatOpenInfoResp getOpenInfoWithEncKey(String code, String type) {
        WxMiniappList wxMiniapp = wxMiniappListService.selectMiniapp(type);
        if (null == wxMiniapp) {
            throw new BusinessException(WechatErrorCode.MINIAPP_NOT_EXIST_ERROR);
        }
        WechatOpenInfoResp openInfo = wechatService.getOpenInfo(code, wxMiniapp.getAppId(), wxMiniapp.getAppSecret());
        String sessionKey = openInfo.getSessionKey();
        if (!ObjectUtils.isEmpty(sessionKey)) {
            openInfo.setSessionKey(AesUtils.getInstance().encrypt(sessionKey, WechatConstant.KEY_FOR_SESSION_KEY));
        }
        return openInfo;
    }

    @Override
    public WechatUserInfoResp getUserInfo(String openId, String sessionKey, String rowData, String signature, String encryptedData, String iv) {
        sessionKey = AesUtils.getInstance().decrypt(sessionKey, WechatConstant.KEY_FOR_SESSION_KEY);
        log.debug("sessionKey:" + sessionKey);
        rowData = new String(rowData.getBytes(), StandardCharsets.UTF_8);
        if (StringUtils.isAnyBlank(sessionKey, rowData)) {
            throw new BusinessException(WechatErrorCode.MINIAPP_GET_USER_INFO_ERROR.getCode(), "sessionKey or rowData is null");
        }
        String signature2 = DigestUtils.sha1Hex(rowData + sessionKey);
        log.debug("signature2:" + signature2);
        if (!signature2.equals(signature)) {
            log.debug("miniapp validate signature failed! signature:{},rowData:{}", signature, rowData);
            throw new BusinessException(WechatErrorCode.MINIAPP_GET_USER_INFO_ERROR.getCode(), "miniapp validate signature failed!");
        }
        String originalData = AesUtils.getInstance().decryptForWeixin(encryptedData, sessionKey, iv);
        if (originalData == null) {
            log.debug("miniapp dec encryptedData failed! encryptedData:{}, iv:{}", encryptedData, iv);
            throw new BusinessException(WechatErrorCode.MINIAPP_GET_USER_INFO_ERROR.getCode(), "miniapp dec encryptedData failed!");
        }
        TypeReference<WechatUserInfoResp> typeReference = new TypeReference<WechatUserInfoResp>() {
        };
        return JsonUtil.toPojo(originalData, typeReference);

//        return JsonUtil.json2map(originalData);
    }

}

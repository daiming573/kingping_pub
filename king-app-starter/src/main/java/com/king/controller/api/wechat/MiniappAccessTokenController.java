package com.king.controller.api.wechat;

import com.common.anno.Anno;
import com.common.errorcode.DefaultErrorCode;
import com.common.errorcode.WechatErrorCode;
import com.common.exception.BusinessException;
import com.common.response.PrettyResponse;
import com.king.wechat.entity.WxMiniappList;
import com.king.wechat.service.AccessTokenService;
import com.king.wechat.service.WxMiniappListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daiming
 * @version 1.0
 * @date 2019-11-24
 * @Description:
 */
@Slf4j
@Api(value = "微信小程序AccessToken接口", tags = "小程序AccessToken")
@RestController
@RequestMapping("/api/miniapp/accessToken/")
public class MiniappAccessTokenController {

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private WxMiniappListService wxMiniappListService;

    @RequestMapping(value = "getAccessTokenByType", method = RequestMethod.GET)
    @ApiOperation("获取去小程序AccessToken")
    @Anno("小程序获取AccessToken")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "小程序编号", dataType = "String", paramType = "query", example = "pig")})
    public PrettyResponse<String> getAccessTokenByType(@RequestParam(value = "type") String type) {
        try {
            return DefaultErrorCode.SUCCESS.createResponse(accessTokenService.getMiniAppAccessToken(type));
        } catch (BusinessException e) {
            e.printStackTrace();
            return new PrettyResponse<>(e.getCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "/refreshAccessTokenByType", method = RequestMethod.POST)
    @ApiOperation("微信刷新AccessToken")
    @Anno("微信刷新AccessToken")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "小程序编号", dataType = "string", paramType = "query", example = "pig")})
    public PrettyResponse<String> refreshAccessTokenByType(@RequestParam(value = "type") String type) {
        try {
            WxMiniappList app = wxMiniappListService.selectMiniapp(type);
            if (null == app) {
                return WechatErrorCode.MINIAPP_NOT_EXIST_ERROR.createResponse();
            }
            String token = accessTokenService.refreshMiniAppAccessToken(app.getMiniappCode(), app.getAppId(), app.getAppSecret());
            return DefaultErrorCode.SUCCESS.createResponse(token);
        } catch (BusinessException e) {
            e.printStackTrace();
            return new PrettyResponse<>(e.getCode(), e.getMessage(), e.getMessage());
        }
    }

}

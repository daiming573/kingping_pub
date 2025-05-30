package com.king.controller.api.wechat;

import com.common.anno.Anno;
import com.common.errorcode.DefaultErrorCode;
import com.common.response.PrettyResponse;
import com.king.wechat.dto.resp.WechatOpenInfoResp;
import com.king.wechat.dto.resp.WechatUserInfoResp;
import com.king.wechat.service.MiniappUserService;
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
@Api(value = "微信小程序用户信息接口", tags = "小程序用户信息")
@RestController
@RequestMapping("/api/miniapp/user/")
public class MiniappUserController {

    @Autowired
    private MiniappUserService miniappUserService;

    @RequestMapping(value = "getOpenIdByType", method = RequestMethod.GET)
    @ApiOperation("小程序获取 openid")
    @Anno("小程序获取 openid")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "小程序编号", paramType = "query", example = "pig"),
            @ApiImplicitParam(name = "code", value = "用户授权code", paramType = "query", example = "12ssssaaa")})
    public PrettyResponse<WechatOpenInfoResp> getOpenIdByType(@RequestParam(value = "code") String code,
                                                              @RequestParam(value = "type") String type) {
        return DefaultErrorCode.SUCCESS.createResponse(miniappUserService.getOpenInfoWithEncKey(code, type));
    }

    @RequestMapping(value = "getUserInfoByType", method = RequestMethod.GET)
    @ApiOperation("小程序获取 用户信息")
    @Anno("小程序获取 用户信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "小程序编号", dataType = "string", paramType = "query", example = "pig"),
            @ApiImplicitParam(name = "openId", value = "用户openId", dataType = "string", paramType = "query", example = "12ssssaaa"),
            @ApiImplicitParam(name = "sessionKey", value = "加密后的 sessionKey", dataType = "string", paramType = "query", example = "HyVFkGl5F5OQWJZZaNzBBg=="),
            @ApiImplicitParam(name = "rawData", value = "用户数据", dataType = "string", paramType = "query", example = "昵称"),
            @ApiImplicitParam(name = "signature", value = "签名", dataType = "string", paramType = "query", example = "75e81ceda165f4ffa64f4068af58c64b8f54b88c"),
            @ApiImplicitParam(name = "encryptedData", value = "加密的用户全部信息", dataType = "string", paramType = "query", example = "75e81ceda165f4ffa64f4068af58c64b8f54b88c"),
            @ApiImplicitParam(name = "iv", value = "AES 算法偏移量", dataType = "string", paramType = "query", example = "12ssssaaa")})
    public PrettyResponse<WechatUserInfoResp> userInfoByType(@RequestParam(value = "type") String type,
                                                 @RequestParam(value = "openId") String openId,
                                                 @RequestParam(value = "sessionKey") String sessionKey,
                                                 @RequestParam(value = "rawData") String rawData,
                                                 @RequestParam(value = "signature") String signature,
                                                 @RequestParam(value = "encryptedData") String encryptedData,
                                                 @RequestParam(value = "iv") String iv) {
        //rawData 用于匹配签名，具体查看 微信开发 api
        //签名，具体查看 微信开发 api
        //被加密的用户全部信息，getUserInfo 的功能就是为了解密该信息，以明文返回给前端，具体查看 微信开发 api
        //AES 算法偏移量，具体查看 微信开发 api
        WechatUserInfoResp wechatUserInfoResp = miniappUserService.getUserInfo(openId, sessionKey, rawData, signature, encryptedData, iv);
        return DefaultErrorCode.SUCCESS.createResponse(wechatUserInfoResp);
    }


}

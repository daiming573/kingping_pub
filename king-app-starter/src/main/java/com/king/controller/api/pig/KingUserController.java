package com.king.controller.api.pig;


import com.common.anno.Anno;
import com.common.anno.RestAuthAnnotation;
import com.common.errorcode.DefaultErrorCode;
import com.common.errorcode.KingErrorCode;
import com.common.response.PrettyResponse;
import com.king.pig.dto.user.req.KingUserReq;
import com.king.pig.dto.user.resp.KingUserChildResp;
import com.king.pig.dto.user.resp.KingUserResp;
import com.king.pig.service.KingFamilyChildMapService;
import com.king.pig.service.KingFamilyChildService;
import com.king.pig.service.KingFamilyUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author daiming
 * @since 2019-11-22
 */
@Api(value = "用户信息接口", tags = "用户信息")
@RestController
@RequestMapping("/api/king/user/")
public class KingUserController {

    @Autowired
    private KingFamilyUserService kingFamilyUserService;

    @Autowired
    private KingFamilyChildService kingFamilyChildService;

    @Autowired
    private KingFamilyChildMapService kingFamilyChildMapService;

    @RestAuthAnnotation(valid = false)
    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
    @ApiOperation("获取用户信息(我的)")
    @Anno("获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "unionId", value = "unionId", dataType = "String", paramType = "query", required = true, example = "1")
    })
    public PrettyResponse<KingUserResp> getUserInfo(@RequestParam @NotBlank(message = "unionId不能为空") String unionId) {
        return DefaultErrorCode.SUCCESS.createResponse(kingFamilyUserService.getUserInfo(unionId));
    }

    @RequestMapping(value = "getUserChild", method = RequestMethod.GET)
    @ApiOperation("查询用户孩子信息")
    @Anno("查询用户孩子信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "unionId", value = "unionId", dataType = "String", paramType = "query", required = true, example = "1")
    })
    public PrettyResponse<KingUserChildResp> getUserChild(@RequestParam @NotEmpty(message = "unionId不能为空") String unionId) {
        return DefaultErrorCode.SUCCESS.createResponse(kingFamilyUserService.getUserChild(unionId));
    }

    @RequestMapping(value = "addChild", method = RequestMethod.POST)
    @ApiOperation("添加孩子账户")
    @Anno("添加孩子账户")
    public PrettyResponse<Boolean> addUserChild(@Valid @RequestBody KingUserReq kingUserReq) {
        boolean add = kingFamilyUserService.addKingFamilyUser(kingUserReq);
        if (add) {
            return DefaultErrorCode.SUCCESS.createResponse(true);
        } else {
            return new PrettyResponse(KingErrorCode.ADD_USER_CHILD_INFO_ERROR);
        }
    }

    @RequestMapping(value = "deleteChild", method = RequestMethod.POST)
    @ApiOperation("删除孩子账户")
    @Anno("删除孩子账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "familyId", value = "家庭ID", dataType = "int", paramType = "query", required = true, example = "1"),
            @ApiImplicitParam(name = "childId", value = "孩子ID", dataType = "int", paramType = "query", required = true, example = "1")
    })
    public PrettyResponse<Boolean> deleteChild(@RequestParam Integer familyId, @RequestParam Integer childId) {
        if (ObjectUtils.isEmpty(familyId) || ObjectUtils.isEmpty(childId)) {
            return DefaultErrorCode.REQUEST_MISSING_REQUEST_PARAMETER.createResponse();
        }
        boolean add = kingFamilyChildMapService.deleteChild(familyId, childId);
        if (add) {
            return DefaultErrorCode.SUCCESS.createResponse(true);
        } else {
            return new PrettyResponse(KingErrorCode.ADD_USER_CHILD_INFO_ERROR);
        }
    }

}


package com.king.controller.api.pig;


import com.common.anno.Anno;
import com.common.errorcode.DefaultErrorCode;
import com.common.response.PageData;
import com.common.response.PrettyResponse;
import com.king.pig.dto.account.req.KingAccountChatQueryReq;
import com.king.pig.dto.account.resp.KingAccountChatResp;
import com.king.pig.dto.account.resp.KingAccountRecordDateResp;
import com.king.pig.dto.user.req.KingAccountRecordStatusReq;
import com.king.pig.dto.user.req.KingChildAccountRecordReq;
import com.king.pig.dto.user.resp.KingChildResp;
import com.king.pig.service.KingChildAccountRecordService;
import com.king.pig.service.KingFamilyChildService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author daiming
 * @since 2019-11-22
 */
@Api(value = "孩子账户接口", tags = "孩子账户信息")
@RestController
@RequestMapping("/api/king/account/")
public class KingChildAccountController {

    @Autowired
    private KingFamilyChildService kingUserChildService;

    @Autowired
    private KingChildAccountRecordService kingChildAccountRecordService;


    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ApiOperation("查询孩子账户")
    @Anno("查询孩子账户")
    @ApiImplicitParam(name="childId", value="孩子ID", dataType="Integer", paramType = "query", required=true, example = "1")
    public PrettyResponse<KingChildResp> get(@RequestParam Integer childId) {
        return DefaultErrorCode.SUCCESS.createResponse(kingChildAccountRecordService.getKingChildInfo(childId));
    }
    @RequestMapping(value = "getAccountChat", method = RequestMethod.GET)
    @ApiOperation("查询孩子账户金额趋势")
    @Anno("查询孩子账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="childId", value="孩子ID", dataType="int", paramType = "query", required=true, example = "1"),
    })
    public PrettyResponse<List<KingAccountChatResp>> getAccountChat(@RequestParam Integer childId,
                                                                   KingAccountChatQueryReq kingAccountChatQueryReq) {
        return DefaultErrorCode.SUCCESS.createResponse(kingChildAccountRecordService.getAccountChat(childId, kingAccountChatQueryReq));
    }

    @RequestMapping(value = "getRecord", method = RequestMethod.GET)
    @ApiOperation("查询孩子账户记录")
    @Anno("查询孩子账户记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNo", value="页码", dataType="int", paramType = "query", required=true, example = "1"),
            @ApiImplicitParam(name="pageSize", value="页大小", dataType="int", paramType = "query", required=true, example = "10"),
            @ApiImplicitParam(name="childId", value="孩子ID", dataType="int", paramType = "query", required=true, example = "1"),
            @ApiImplicitParam(name="type", value="类型，income-收入，pay-支出，task-任务，reward-奖励", dataType="string", paramType = "query", example = "income"),
    })
    public PrettyResponse<PageData<KingAccountRecordDateResp>> getRecord(@RequestParam Integer pageNo,
                                                                         @RequestParam Integer pageSize,
                                                                         @RequestParam Integer childId,
                                                                         @RequestParam String type) {
        return DefaultErrorCode.SUCCESS.createResponse(kingChildAccountRecordService.getAccountRecord(pageNo, pageSize, childId, type));
    }

    @RequestMapping(value = "addRecord", method = RequestMethod.POST)
    @ApiOperation("添加账户记录")
    @Anno("添加账户记录")
    public PrettyResponse<Boolean> addRecord(@Valid @RequestBody KingChildAccountRecordReq childAccountRecordReqDto) {
        boolean bool = kingChildAccountRecordService.addAccountRecord(childAccountRecordReqDto);
        if (bool) {
            return DefaultErrorCode.SUCCESS.createResponse(true);
        }else {
            return DefaultErrorCode.FAIL.createResponse(false);
        }
    }

    @RequestMapping(value = "doJob", method = RequestMethod.POST)
    @ApiOperation("完成任务")
    @Anno("完成任务")
    public PrettyResponse<Boolean> doJob(@Valid @RequestBody KingAccountRecordStatusReq kingAccountRecordStatusReq) {
        boolean bool = kingChildAccountRecordService.finishAccountRecord(kingAccountRecordStatusReq.getId());
        if (bool) {
            return DefaultErrorCode.SUCCESS.createResponse(true);
        }else {
            return DefaultErrorCode.FAIL.createResponse(false);
        }
    }

    @RequestMapping(value = "getPrize", method = RequestMethod.POST)
    @ApiOperation("领取奖励")
    @Anno("领取奖励")
    public PrettyResponse<Boolean> getPrize(@RequestBody KingAccountRecordStatusReq kingAccountRecordStatusReq) {
        boolean bool = kingChildAccountRecordService.getPrize(kingAccountRecordStatusReq.getId());
        if (bool) {
            return DefaultErrorCode.SUCCESS.createResponse(true);
        }else {
            return DefaultErrorCode.FAIL.createResponse(false);
        }
    }
}


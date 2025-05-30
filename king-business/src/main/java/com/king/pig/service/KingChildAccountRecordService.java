package com.king.pig.service;

import com.baomidou.mybatisplus.service.IService;
import com.common.response.PageData;
import com.king.pig.dto.account.req.KingAccountChatQueryReq;
import com.king.pig.dto.account.resp.KingAccountChatResp;
import com.king.pig.dto.account.resp.KingAccountRecordDateResp;
import com.king.pig.dto.user.req.KingChildAccountRecordReq;
import com.king.pig.dto.user.resp.KingChildResp;
import com.king.pig.entity.KingChildAccountRecord;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author daiming
 * @since 2019-11-22
 */
public interface KingChildAccountRecordService extends IService<KingChildAccountRecord> {

    /**
     * 获取孩子账户信息
     * @param childId   孩子ID
     * @return  KingChildResp
     */
    KingChildResp getKingChildInfo(Integer childId);

    /**
     * 查询账户金额趋势
     * @param childId   孩子ID
     * @param kingAccountChatQueryReq   请求参数
     * @return  List<KingAccountChatResp>
     */
    List<KingAccountChatResp> getAccountChat(Integer childId, KingAccountChatQueryReq kingAccountChatQueryReq);

    /**
     * 分页查询账户交易记录
     * @param pageNo    页码
     * @param pageSize  页大小
     * @param childId   孩子ID
     * @param type   类型，income-收入，pay-支出，task-任务，reward-奖励
     * @return  PageData<KingAccountRecordDateResp>
     */
    PageData<KingAccountRecordDateResp> getAccountRecord(Integer pageNo, Integer pageSize, Integer childId, String type);

    /**
     * 添加账户交易记录
     * @param childAccountRecordReqDto  查询参数
     * @return  boolean
     */
    boolean addAccountRecord(KingChildAccountRecordReq childAccountRecordReqDto);

    /**
     * 完成任务
     * @param id    交易记录ID
     * @return  boolean
     */
    boolean finishAccountRecord(Integer id);

    /**
     * 获取奖金
     * @param id    交易记录ID
     * @return  boolean
     */
    boolean getPrize(Integer id);
}

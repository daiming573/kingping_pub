package com.king.pig.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.king.pig.dto.account.resp.KingAccountChatResp;
import com.king.pig.dto.account.resp.KingAccountRecordDateResp;
import com.king.pig.entity.KingChildAccountRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author daiming
 * @since 2019-11-22
 */
public interface KingChildAccountRecordDao extends BaseMapper<KingChildAccountRecord> {

    /**
     * 分页查询账户日期交易记录
     * @param page  分页参数
     * @param childId   孩子ID
     * @param type  类型，income-收入，pay-支出，task-任务，reward-奖励
     * @return  Page<KingAccountRecordDateResp>
     */
    List<KingAccountRecordDateResp> getAccountDateRecordPage(Page<KingAccountRecordDateResp> page,
                                                             @Param("childId") Integer childId,
                                                             @Param("type") String type);

    /**
     * 查询指定日期的交易记录
     * @param childId   孩子ID
     * @param type  类型，income-收入，pay-支出，task-任务，reward-奖励
     * @param currentDateList   交易日期
     * @return  List<KingChildAccountRecord>
     */
    List<KingChildAccountRecord> getAccountDateRecord(@Param("childId") Integer childId,
                                                      @Param("type") String type,
                                                      @Param("currentDateList") List<Date> currentDateList);

    /**
     * 分页查询孩子账户交易记录
     * @param childId   孩子ID
     * @param type  类型，income-收入，pay-支出，task-任务，reward-奖励
     * @param startDate   开始日期
     * @param endDate   结束日期
     * @return  List<KingAccountChatResp>
     */
    List<KingAccountChatResp> getAccountDateTotalList(@Param("childId") Integer childId,
                                                      @Param("type") String type,
                                                      @Param("startDate") Date startDate,
                                                      @Param("endDate") Date endDate);
}

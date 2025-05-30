package com.king.pig.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.errorcode.KingErrorCode;
import com.common.exception.BusinessException;
import com.common.response.PageData;
import com.king.pig.constant.KingConstant;
import com.king.pig.dao.KingChildAccountRecordDao;
import com.king.pig.dto.account.req.KingAccountChatQueryReq;
import com.king.pig.dto.account.resp.KingAccountChatResp;
import com.king.pig.dto.account.resp.KingAccountRecordDateResp;
import com.king.pig.dto.account.resp.KingChildAccountRecordResp;
import com.king.pig.dto.user.req.KingChildAccountRecordReq;
import com.king.pig.dto.user.resp.KingChildResp;
import com.king.pig.entity.KingChildAccountRecord;
import com.king.pig.entity.KingFamilyChild;
import com.king.pig.entity.KingFamilyUser;
import com.king.pig.enums.FamilyUserTypeEnum;
import com.king.pig.enums.KingAccountStatusEnum;
import com.king.pig.enums.KingAccountTypeEnum;
import com.king.pig.service.KingChildAccountRecordService;
import com.king.pig.service.KingFamilyChildService;
import com.king.pig.service.KingFamilyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author daiming
 * @since 2019-11-22
 */
@Slf4j
@Service
public class KingChildAccountRecordServiceImpl extends ServiceImpl<KingChildAccountRecordDao, KingChildAccountRecord> implements KingChildAccountRecordService {

    @Autowired
    private KingFamilyChildService kingUserChildService;

    @Autowired
    private KingFamilyUserService kingFamilyUserService;

    @Override
    public KingChildResp getKingChildInfo(Integer childId) {
        KingChildResp kingChildResp = new KingChildResp();
        if (ObjectUtils.isEmpty(childId)) {
            return kingChildResp;
        }
        KingFamilyChild kingUserChild = kingUserChildService.selectById(childId);
        if (ObjectUtils.isEmpty(kingUserChild)) {
            return kingChildResp;
        }
        BeanUtils.copyProperties(kingUserChild, kingChildResp);
        return kingChildResp;
    }

    @Override
    public List<KingAccountChatResp> getAccountChat(Integer childId, KingAccountChatQueryReq kingAccountChatQueryReq) {
        return baseMapper.getAccountDateTotalList(childId, kingAccountChatQueryReq.getType(),
                kingAccountChatQueryReq.getStartDate(), kingAccountChatQueryReq.getEndDate());
    }


    @Override
    public PageData<KingAccountRecordDateResp> getAccountRecord(Integer pageNo, Integer pageSize, Integer childId,
                                                                 String type) {
        if (null  == pageNo) {
            pageNo = KingConstant.PAGE_NO_DEFAULT;
        }
        if (null  == pageSize) {
            pageSize = KingConstant.PAGE_SIZE_DEFAULT;
        }
        PageData<KingAccountRecordDateResp> pageData = new PageData<>();
        Page<KingAccountRecordDateResp> page = new Page<>(pageNo, pageSize);
        BeanUtils.copyProperties(page, pageData);
        //查询孩子账户交易记录
        List<KingAccountRecordDateResp> accountDateRecords = baseMapper.getAccountDateRecordPage(page, childId, type);
        if (ObjectUtils.isEmpty(accountDateRecords)) {
            return pageData;
        }
        List<Date> accountDateList = accountDateRecords.stream()
                .filter(e -> !ObjectUtils.isEmpty(e.getCurrentDate()))
                .map(KingAccountRecordDateResp::getCurrentDate)
                .collect(Collectors.toList());
        //查询账户交易记录
        List<KingChildAccountRecord> kingChildAccountRecords = baseMapper.getAccountDateRecord(childId, type, accountDateList);
        if (ObjectUtils.isEmpty(kingChildAccountRecords)) {
            return pageData;
        }
        Map<String, String> userTypeMap = Collections.emptyMap();
        //查询账户任务添加人
        List<String> userIds = kingChildAccountRecords.stream()
                .filter(e -> !ObjectUtils.isEmpty(e.getCreateUserId()))
                .map(KingChildAccountRecord::getCreateUserId)
                .collect(Collectors.toList());
        if (!ObjectUtils.isEmpty(userIds)) {
            List<KingFamilyUser> kingFamilyUsers = kingFamilyUserService.selectList(new EntityWrapper<KingFamilyUser>().in(KingFamilyUser.USER_ID, userIds));
            //用户身份类型
            userTypeMap = kingFamilyUsers.stream()
                    .collect(Collectors.toMap(KingFamilyUser::getUserId, KingFamilyUser::getUserType));
        }

        //封装返回信息
        for (KingAccountRecordDateResp record : accountDateRecords) {
            //查询指定日期下孩子交易记录
            List<KingChildAccountRecord> accountRecords = kingChildAccountRecords.stream()
                    .filter(e -> record.getCurrentDate().equals(e.getCurrentDate()) && record.getChildId().equals(e.getChildId()))
                    .collect(Collectors.toList());
            //任务发出 家人  身份
            record.setKingChildAccountRecordList(getAccountInfo(accountRecords, userTypeMap));
        }

        BeanUtils.copyProperties(page, pageData);
        pageData.setList(accountDateRecords);
        return pageData;
    }

    /**
     * 获取账户交易记录
     * @param accountRecords    账户交易记录
     * @param userTypeMap   交易任务发布人
     * @return  List<KingChildAccountRecordResp>
     */
    private List<KingChildAccountRecordResp> getAccountInfo(List<KingChildAccountRecord> accountRecords,
                                                            Map<String, String> userTypeMap) {
        List<KingChildAccountRecordResp> kingChildAccountRecordResps = new ArrayList<>();
        if (ObjectUtils.isEmpty(accountRecords)) {
            return kingChildAccountRecordResps;
        }
        KingChildAccountRecordResp kingChildAccountRecordResp;
        for (KingChildAccountRecord record : accountRecords) {
            kingChildAccountRecordResp = new KingChildAccountRecordResp();
            BeanUtils.copyProperties(record, kingChildAccountRecordResp);
            kingChildAccountRecordResp.setCreateUser(FamilyUserTypeEnum.getNameByType(userTypeMap.get(record.getCreateUserId())));
            kingChildAccountRecordResps.add(kingChildAccountRecordResp);
        }
        return kingChildAccountRecordResps;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addAccountRecord(KingChildAccountRecordReq childAccountRecordReqDto) {
        KingChildAccountRecord kingChildAccountRecord = new KingChildAccountRecord();
        BeanUtils.copyProperties(childAccountRecordReqDto, kingChildAccountRecord);
        Date currentTime = new Date();
        kingChildAccountRecord.setCurrentDate(currentTime);
        kingChildAccountRecord.setCreateTime(currentTime);
        kingChildAccountRecord.setUpdateTime(currentTime);
        boolean insert = this.insert(kingChildAccountRecord);
        if (insert) {
            //任务完成，修改账户总额
            String type = childAccountRecordReqDto.getType();
            Integer money = getMoney(type, childAccountRecordReqDto.getAccountOut(), childAccountRecordReqDto.getAccountIn());
            if (money <= 0 || KingAccountStatusEnum.ACCOUNT_TASK_STATUS_UNDO.getType().equals(childAccountRecordReqDto.getStatus())) {
                return true;
            }
            boolean updateTotal = kingUserChildService.updateChildAccountTotal(childAccountRecordReqDto.getChildId(), money, type);
            if (!updateTotal) {
                log.info("update child account total fail");
            }
        }
        return insert;
    }

    /**
     * 根据交易类型获取金额
     * @param type  交易类型
     * @param out   支出
     * @param in    收入
     * @return  Integer
     */
    private Integer getMoney(String type, Integer out, Integer in) {
        Integer money;
        if (KingAccountTypeEnum.ACCOUNT_TYPE_PAY.getType().equals(type)) {
            money = out;
        } else {
            money = in;
        }
        return money;
    }

    @Override
    public boolean finishAccountRecord(Integer id) {
        if (null == id) {
            return false;
        }
        KingChildAccountRecord kingChildAccountRecord = new KingChildAccountRecord();
        kingChildAccountRecord.setId(id);
        kingChildAccountRecord.setStatus(KingAccountStatusEnum.ACCOUNT_TASK_STATUS_FINISH.getType());
        return this.updateById(kingChildAccountRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean getPrize(Integer id) {
        if (null == id) {
            return false;
        }
        //查询任务记录信息
        KingChildAccountRecord accountRecord = this.selectById(id);
        if (null == accountRecord) {
            throw new BusinessException(KingErrorCode.CHLD_ACCOUNT_ID_ERROR);
        }
        //任务是否已完成
        if (KingAccountStatusEnum.ACCOUNT_TASK_STATUS_FINISH.getType().equals(accountRecord.getStatus())) {
            throw new BusinessException(KingErrorCode.CHILD_PRIZE_GET_REPEAT_ERROR);
        }
        KingChildAccountRecord kingChildAccountRecord = new KingChildAccountRecord();
        kingChildAccountRecord.setId(id);
        kingChildAccountRecord.setStatus(KingAccountStatusEnum.ACCOUNT_TASK_STATUS_FINISH.getType());
        //限制条件
        Wrapper<KingChildAccountRecord> wrapper = new EntityWrapper<KingChildAccountRecord>()
                .ne(KingChildAccountRecord.STATUS, KingAccountStatusEnum.ACCOUNT_TASK_STATUS_FINISH.getType());
        boolean bool = this.update(kingChildAccountRecord, wrapper);
        if (bool) {
            Integer money = getMoney(accountRecord.getType(), accountRecord.getAccountOut(), accountRecord.getAccountIn());
            if (money <= 0) {
                return true;
            }
            boolean updateTotal = kingUserChildService.updateChildAccountTotal(accountRecord.getChildId(), money, accountRecord.getType());
            if (!updateTotal) {
                log.info("update child account total fail");
            }
        }
        return bool;
    }

}

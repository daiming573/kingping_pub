package com.king.pig.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.exception.BusinessException;
import com.common.errorcode.KingErrorCode;
import com.king.pig.constant.KingConstant;
import com.king.pig.dao.KingFamilyChildDao;
import com.king.pig.dto.user.req.KingUserChildReq;
import com.king.pig.dto.user.resp.KingChildResp;
import com.king.pig.entity.KingFamilyChild;
import com.king.pig.enums.KingAccountTypeEnum;
import com.king.pig.service.KingFamilyChildMapService;
import com.king.pig.service.KingFamilyChildService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author daiming
 * @since 2019-11-22
 */
@Service
public class KingFamilyChildServiceImpl extends ServiceImpl<KingFamilyChildDao, KingFamilyChild> implements KingFamilyChildService {

    @Override
    public Integer addUserChild(KingUserChildReq kingUserChildReqDto) {
        KingFamilyChild kingUserChild = new KingFamilyChild();
        BeanUtils.copyProperties(kingUserChildReqDto, kingUserChild);
        kingUserChild.setAccountTotal(KingConstant.ACCOUNT_DEFAULT);
        kingUserChild.setCreateTime(new Date());
        kingUserChild.setUpdateTime(new Date());
        if (this.insert(kingUserChild)){
            //返回主键ID
            return kingUserChild.getId();
        }
        return 0;
    }

    @Override
    public List<KingChildResp> getUserChildByFamilyId(Integer familyId) {
        List<KingFamilyChild> kingUserChildList = baseMapper.getUserChildByFamilyId(familyId);
        List<KingChildResp> userChildRespDtoList = new ArrayList<>(kingUserChildList.size());
        kingUserChildList.forEach(child -> {
            KingChildResp kingUserChildRespDto = new KingChildResp();
            BeanUtils.copyProperties(child, kingUserChildRespDto);
            kingUserChildRespDto.setChildId(child.getId());
            userChildRespDtoList.add(kingUserChildRespDto);
        });
        return userChildRespDtoList;
    }

    @Override
    public boolean updateChildAccountTotal(Integer childId, Integer money, String type) {
        Integer num;
        if (KingAccountTypeEnum.ACCOUNT_TYPE_PAY.getType().equals(type)) {
            //账户支出
            num = baseMapper.reduceAccountTotal(money, childId);
            if (num <= 0) {
                throw new BusinessException(KingErrorCode.USER_CHILD_ACCOUNT_PAY_ERROR);
            }
        } else {
            //收入
            num = baseMapper.addAccountTotal(money, childId);
            if (num <= 0) {
                throw new BusinessException(KingErrorCode.USER_CHILD_ACCOUNT_ADD_ERROR);
            }
        }
        return true;
    }


}

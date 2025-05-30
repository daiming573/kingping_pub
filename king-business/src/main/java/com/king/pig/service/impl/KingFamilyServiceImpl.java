package com.king.pig.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.king.pig.constant.KingConstant;
import com.king.pig.dao.KingFamilyDao;
import com.king.pig.entity.KingFamily;
import com.king.pig.enums.KingStatusEnum;
import com.king.pig.service.KingFamilyService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author daiming
 * @since 2019-11-30
 */
@Service
public class KingFamilyServiceImpl extends ServiceImpl<KingFamilyDao, KingFamily> implements KingFamilyService {

    @Override
    public Integer addFamily() {
        KingFamily kingFamily = new KingFamily();
        kingFamily.setFamilyAccount(KingConstant.ACCOUNT_DEFAULT);
        kingFamily.setFamilyName(KingConstant.FAMILY_NAME_DEFAULT);
        kingFamily.setStatus(KingStatusEnum.STATUS_ON.getCode());
        kingFamily.setCreateTime(new Date());
        kingFamily.setUpdateTime(new Date());
        boolean insert = this.insert(kingFamily);
        if (insert) {
            return kingFamily.getId();
        }
        return 0;
    }

}

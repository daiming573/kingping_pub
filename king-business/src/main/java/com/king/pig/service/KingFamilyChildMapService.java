package com.king.pig.service;

import com.king.pig.entity.KingFamilyChildMap;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author daiming
 * @since 2019-11-30
 */
public interface KingFamilyChildMapService extends IService<KingFamilyChildMap> {

    boolean addFamilyChild(Integer familyId, Integer childId);

    boolean deleteChild(Integer familyId, Integer childId);

}

package com.king.pig.service;

import com.king.pig.entity.KingFamily;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author daiming
 * @since 2019-11-30
 */
public interface KingFamilyService extends IService<KingFamily> {

    Integer addFamily();
}

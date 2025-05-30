package com.king.pig.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.king.pig.entity.KingFamilyChild;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author daiming
 * @since 2019-11-22
 */
public interface KingFamilyChildDao extends BaseMapper<KingFamilyChild> {

    /**
     * 查询孩子家庭信息
     * @param familyId
     * @return
     */
    List<KingFamilyChild> getUserChildByFamilyId(Integer familyId);

    /**
     * 添加账户收入记录
     * @param money
     * @param childId
     * @return
     */
    Integer addAccountTotal(Integer money, Integer childId);

    /**
     * 账户支出记录
     * @param money
     * @param childId
     * @return
     */
    Integer reduceAccountTotal(Integer money, Integer childId);
}

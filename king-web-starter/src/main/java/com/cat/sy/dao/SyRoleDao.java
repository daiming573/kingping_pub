package com.cat.sy.dao;
/// ***********************import begin***********************

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cat.sy.entity.SyRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/// ***********************import end*************************

/**
 * 表名：pm_role
 *
 * @author Administrator
 */
@Repository("syRoleDao")
public interface SyRoleDao extends BaseMapper<SyRole> {

    /**
     * 保存操作历史
     *
     * @param syRole 实体
     */
    void insertHs(@Param("entity") SyRole syRole);

    /// ***********************method begin***********************

    /// ***********************method end*************************
}

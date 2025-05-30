package com.cat.sy.dao;
/// ***********************import begin***********************

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cat.sy.entity.SyUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/// ***********************import end*************************

/**
 * 表名：pm_user
 *
 * @author Administrator
 */

@Repository("syUserDao")
public interface SyUserDao extends BaseMapper<SyUser> {

    /// ***********************method begin***********************

    /**
     * 重置密码
     *
     * @param id
     * @param password
     *
     * @return
     */
    Integer updateForResetPassword(@Param("id") Integer id, @Param("password") String password);

    /**
     * 修改密码
     *
     * @param id
     * @param password
     *
     * @return
     */
    Integer updatePassword(@Param("id") Integer id, @Param("password") String password, @Param("newPassword") String newPassword);
    /// ***********************method end*************************

    /**
     * 修改用户的token
     *
     * @param id
     * @param token
     * @param tokenTime
     *
     * @return
     */
    Integer updateForToken(@Param("id") Integer id, @Param("token") String token, @Param("tokenTime") Timestamp tokenTime);

}

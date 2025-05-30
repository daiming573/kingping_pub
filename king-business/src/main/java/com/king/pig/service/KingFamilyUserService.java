package com.king.pig.service;

import com.baomidou.mybatisplus.service.IService;
import com.king.pig.dto.user.req.KingUserReq;
import com.king.pig.dto.user.resp.KingUserChildResp;
import com.king.pig.dto.user.resp.KingUserResp;
import com.king.pig.entity.KingFamilyUser;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author daiming
 * @since 2019-11-22
 */
public interface KingFamilyUserService extends IService<KingFamilyUser> {

    /**
     * 查询用户信息
     * @param unionId
     * @return  KingUserRespDto
     */
    KingUserResp getUserInfo(String unionId);

    /**
     * 查询用户孩子信息
     * @param unionId
     * @return  KingUserChildResp
     */
    KingUserChildResp getUserChild(String unionId);

    /**
     * 添加家庭孩子信息
     * @param kingUserReqDto    添加家庭孩子信息
     * @return  boolean
     */
    boolean addKingFamilyUser(KingUserReq kingUserReqDto);

}

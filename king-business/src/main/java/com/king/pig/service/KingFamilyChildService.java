package com.king.pig.service;

import com.baomidou.mybatisplus.service.IService;
import com.king.pig.dto.user.req.KingUserChildReq;
import com.king.pig.dto.user.resp.KingChildResp;
import com.king.pig.entity.KingFamilyChild;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author daiming
 * @since 2019-11-22
 */
public interface KingFamilyChildService extends IService<KingFamilyChild> {

    Integer addUserChild(KingUserChildReq kingUserChildReqDto);

    List<KingChildResp> getUserChildByFamilyId(Integer parentId);

    boolean updateChildAccountTotal(Integer childId, Integer money, String type);
}

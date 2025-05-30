package com.king.pig.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.errorcode.KingErrorCode;
import com.common.exception.BusinessException;
import com.common.util.RandomCodeUtil;
import com.common.util.UUIDUtil;
import com.king.pig.dao.KingFamilyUserDao;
import com.king.pig.dto.user.req.KingUserReq;
import com.king.pig.dto.user.resp.KingUserChildResp;
import com.king.pig.dto.user.resp.KingUserResp;
import com.king.pig.entity.KingFamilyUser;
import com.king.pig.enums.FamilyUserTypeEnum;
import com.king.pig.service.KingFamilyChildMapService;
import com.king.pig.service.KingFamilyChildService;
import com.king.pig.service.KingFamilyService;
import com.king.pig.service.KingFamilyUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

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
public class KingFamilyUserServiceImpl extends ServiceImpl<KingFamilyUserDao, KingFamilyUser> implements KingFamilyUserService {

    @Autowired
    private KingFamilyChildService kingFamilyChildService;
    @Autowired
    private KingFamilyChildMapService kingFamilyChildMapService;
    @Autowired
    private KingFamilyService kingFamilyService;

    @Override
    public KingUserResp getUserInfo(String unionId) {
        //查询家庭用户信息
        KingFamilyUser user = getUserByUnionId(unionId);
        if (null == user) {
            return null;
        }
        KingUserResp kingUserRespDto = new KingUserResp();
        BeanUtils.copyProperties(user, kingUserRespDto);

        String userType = "";
        if (FamilyUserTypeEnum.FAMILY_USER_TYPE_FATHER.getType().equals(user.getUserType())) {
            //查询妈妈是否已添加绑定
            userType = FamilyUserTypeEnum.FAMILY_USER_TYPE_MOTHER.getType();
        } else if (FamilyUserTypeEnum.FAMILY_USER_TYPE_MOTHER.getType().equals(user.getUserType())) {
            //查询爸爸是否已添加绑定
            userType = FamilyUserTypeEnum.FAMILY_USER_TYPE_FATHER.getType();
        } else {
            return kingUserRespDto;
        }
        //查询用户 配偶是否添加绑定
        List<KingFamilyUser> parentList = getUserByUserType(userType);
        //父母是否都绑定
        kingUserRespDto.setParentAll(!CollectionUtils.isEmpty(parentList));
        return kingUserRespDto;
    }

    @Override
    public KingUserChildResp getUserChild(String unionId) {
        //查询家庭用户信息
        KingFamilyUser user = getUserByUnionId(unionId);
        if (null == user) {
            return null;
        }
        KingUserChildResp kingUserChildResp = new KingUserChildResp();
        BeanUtils.copyProperties(user, kingUserChildResp);
        kingUserChildResp.setParentId(user.getId());
        kingUserChildResp.setKingUserChildList(kingFamilyChildService.getUserChildByFamilyId(user.getFamilyId()));

        return kingUserChildResp;
    }

    /**
     * 根据unionId获取用户信息
     *
     * @param unionId
     * @return KingFamilyUser
     */
    private KingFamilyUser getUserByUnionId(String unionId) {
        if (ObjectUtils.isEmpty(unionId)) {
            return null;
        }
        return this.selectOne(new EntityWrapper<KingFamilyUser>().eq(KingFamilyUser.UNION_ID, unionId));
    }

    /**
     * 根据用户类型查询家庭成员
     * @param userType  用户类型
     * @return  List<KingFamilyUser>
     */
    private List<KingFamilyUser> getUserByUserType(String userType) {
        return this.selectList(new EntityWrapper<KingFamilyUser>().eq(KingFamilyUser.USER_TYPE, userType));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addKingFamilyUser(KingUserReq kingUserReqDto) {
        KingFamilyUser oldUser = getUserByUnionId(kingUserReqDto.getUnionId());
        //添加家庭用户信息
        Integer familyId = addOrUpdateUserInfo(kingUserReqDto, oldUser);
        //添加孩子账户信息
        Integer childId = kingFamilyChildService.addUserChild(kingUserReqDto.getKingUserChildReqDto());
        if (childId <= 0) {
            throw new BusinessException(KingErrorCode.ADD_USER_CHILD_INFO_ERROR);
        }
        //添加家庭和孩子映射关系
        return kingFamilyChildMapService.addFamilyChild(familyId, childId);
    }

    /**
     * 添加或更新用户信息
     *
     * @param kingUserReq    家庭用户信息请求参数
     * @param oldUser   家庭用户信息
     * @return  Integer
     */
    private Integer addOrUpdateUserInfo(KingUserReq kingUserReq, KingFamilyUser oldUser) {
        KingFamilyUser entity = new KingFamilyUser();
        BeanUtils.copyProperties(kingUserReq, entity);
        entity.setUpdateTime(new Date());
        Integer familyId = kingUserReq.getFamilyId();
        boolean bool;
        if (null == oldUser) {
            if (ObjectUtils.isEmpty(familyId)) {
                //添加家庭信息
                familyId = addFamilyInfo();
            }
            //新增家庭用户信息
            bool = saveFamilyUser(familyId, entity);
        } else {
            //修改家庭用户信息
            familyId = ObjectUtils.isEmpty(kingUserReq.getFamilyId()) ? oldUser.getFamilyId() : kingUserReq.getFamilyId();
            entity.setId(oldUser.getId());
            entity.setFamilyId(familyId);
            bool = this.updateById(entity);
        }
        if (!bool) {
            throw new BusinessException(KingErrorCode.ADD_USER_INFO_ERROR);
        }
        return familyId;
    }

    /**
     * 添加家庭成员
     * @param familyId  家庭ID
     * @param entity    家庭成员信息
     * @return  boolean
     */
    private boolean saveFamilyUser(Integer familyId, KingFamilyUser entity) {
        entity.setUserId(UUIDUtil.get32UUID());
        entity.setFamilyId(familyId);
        entity.setCreateTime(new Date());
        boolean bool = this.insert(entity);
        if (bool) {
            //更新邀请码
            entity.setFamilyId(familyId);
            entity.setInviteCode(RandomCodeUtil.toSerialCode(entity.getId()));
            this.updateById(entity);
        }
        return bool;
    }

    /**
     * 添加家庭信息
     *
     * @return  Integer
     */
    private Integer addFamilyInfo() {
        //添加家庭信息
        Integer familyId = kingFamilyService.addFamily();
        if (familyId <= 0) {
            throw new BusinessException(KingErrorCode.ADD_FAMILY_INFO_ERROR);
        }
        //更新家庭成员 邀请码及家庭ID
        return familyId;
    }

}

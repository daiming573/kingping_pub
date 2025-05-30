package com.king.pig.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.errorcode.KingErrorCode;
import com.common.exception.BusinessException;
import com.king.pig.dao.KingFamilyChildMapDao;
import com.king.pig.entity.KingFamilyChildMap;
import com.king.pig.enums.DeleteEnum;
import com.king.pig.service.KingFamilyChildMapService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author daiming
 * @since 2019-11-22
 */
@Service
public class KingFamilyChildMapServiceImpl extends ServiceImpl<KingFamilyChildMapDao, KingFamilyChildMap> implements KingFamilyChildMapService {

    @Override
    public boolean addFamilyChild(Integer familyId, Integer childId) {
        KingFamilyChildMap entity = new KingFamilyChildMap();
        entity.setChildId(childId);
        entity.setFamilyId(familyId);
        KingFamilyChildMap parentChildMap = this.selectOne(new EntityWrapper<>(entity));
        if (null == parentChildMap) {
            entity.setIsDeleted(DeleteEnum.DELETED_NO.getCode());
            return this.insert(entity);
        }else {
            parentChildMap.setIsDeleted(DeleteEnum.DELETED_NO.getCode());
            return this.updateById(parentChildMap);
        }
    }

    @Override
    public boolean deleteChild(Integer familyId, Integer childId) {
        KingFamilyChildMap entity = new KingFamilyChildMap();
        entity.setChildId(childId);
        entity.setFamilyId(familyId);
        KingFamilyChildMap parentChildMap = this.selectOne(new EntityWrapper<>(entity));
        if (null == parentChildMap) {
            throw new BusinessException(KingErrorCode.USER_PARENT_CHILD_MAP_ERROR);
        }
        parentChildMap.setIsDeleted(DeleteEnum.DELETED_YES.getCode());
        return this.updateById(parentChildMap);
    }

    /**
     * 根据父母ID获取另一个父母是否绑定
     * @param parentId
     * @return
     */
//    @Override
//    public List<KingParentChildMap> getParentByOtherParent(Integer parentId){
//         return baseMapper.getParentByOtherParent(parentId);
//    }


}

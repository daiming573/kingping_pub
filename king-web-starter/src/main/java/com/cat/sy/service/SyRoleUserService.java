package com.cat.sy.service;
/// ***********************import begin***********************

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cat.bean.PageControlInfo;
import com.cat.bean.RResult;
import com.cat.sy.dao.SyRoleUserDao;
import com.cat.sy.entity.SyCatalogRole;
import com.cat.sy.entity.SyRoleUser;
import com.common.util.UUIDUtil;
import com.common.util.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/// ***********************import end*************************
@Slf4j
@Service("syRoleUserService")
public class SyRoleUserService extends ServiceImpl<SyRoleUserDao, SyRoleUser> {

    @Autowired
    private SyCatalogRoleService syCatalogRoleService;


    /// ***********************define end*************************

    public Integer save(SyRoleUser syRoleUser) {
        if (null == syRoleUser) {
            log.info("save, is empty ");
            return null;
        }
        syRoleUser.setUuid(UUIDUtil.get32UUID());
        syRoleUser.setCreateTime(DateUtil.getCurrentTimestamp());
        return baseMapper.insert(syRoleUser);
    }


    public boolean delete(Integer id) {
        if (null == id) {
            log.info("delete, is empty");
            return false;
        }
        SyRoleUser entity = load(id);
        if (null == entity) {
            log.info("delete record not exist.  id");
            return false;
        }
        return this.deleteById(id);
    }


    public boolean deleteByIds(String ids) {
        if (StringUtils.isBlank(ids)) {
            log.info("deleteByIds  is empty ");
            return false;
        }
        String[] array = ids.split(",");
        List<Integer> list = new ArrayList<Integer>();
        for (String id : array) {
            list.add(Integer.parseInt(id));
        }
        Integer result = baseMapper.deleteBatchIds(list);
        return result > 0;
    }


    public boolean update(SyRoleUser syRoleUser) {
        if (null == syRoleUser) {
            log.info("update is empty ");
            return false;
        }
        Integer id = syRoleUser.getId();
        if (null == id) {
            log.info("update  is empty");
            return false;
        }
        Integer result = baseMapper.updateById(syRoleUser);
        return result > 0;
    }


    public RResult update(SyRoleUser oldEntity, SyRoleUser newEntity) {
        if (null == newEntity) {
            log.info("update param is empty");
            return new RResult(RResult.MSG_FAIL, RResult.paramNull, "newEntity");
        }
        Integer result = baseMapper.updateById(newEntity);
        if (result > 0) {
            return new RResult(RResult.MSG_SUCCESS);
        }
        return new RResult(RResult.MSG_FAIL);
    }


    public SyRoleUser load(Integer id) {
        if (null == id) {
            log.info("load  is empty id");
            return null;
        }
        return baseMapper.selectById(id);
    }


    public SyRoleUser load(SyRoleUser syRoleUser) {
        if (null == syRoleUser) {
            log.info("load  is empty syRoleUser");
            return null;
        }
        return baseMapper.selectOne(syRoleUser);
    }


    public Integer loadCount(SyRoleUser syRoleUser) {
        return baseMapper.selectCount(new EntityWrapper<>(syRoleUser));
    }


    public boolean isExist(SyRoleUser syRoleUser) {
        Integer count = loadCount(syRoleUser);
        if (null == count) {
            return false;
        }
        return count.intValue() > 0;
    }


    public List<SyRoleUser> findList(SyRoleUser syRoleUser, Map<String, Object> params) {
        return baseMapper.selectList(new EntityWrapper<>(syRoleUser));
    }


    public PageControlInfo findPageList(SyRoleUser syRoleUser, Integer skip, Integer max, Map<String, Object> params) {
        Page<SyRoleUser> page = new Page<>(skip, max);
        Page<SyRoleUser> syRoleUserPage = this.selectPage(page, new EntityWrapper<>(syRoleUser));
        PageControlInfo pageControlInfo = new PageControlInfo();
        pageControlInfo.setTotalNum((int) page.getTotal());
        pageControlInfo.setSearchData(syRoleUserPage.getRecords());
        pageControlInfo.setStart(page.getCurrent());
        pageControlInfo.setMax(max);
        return pageControlInfo;
    }

    /// ***********************method begin***********************

    public boolean isExist(Integer roleId, Integer userId) {
        SyRoleUser syRoleUser = new SyRoleUser();
        syRoleUser.setRoleId(roleId);
        syRoleUser.setUserId(userId);
        Integer count = loadCount(syRoleUser);
        if (null == count) {
            return false;
        }
        return count.intValue() > 0;
    }


    public RResult saveRoleUsers(SyRoleUser entity, String userIds) {
        Integer roleId = entity.getRoleId();
        if (StringUtils.isBlank(userIds) || null == roleId) {
            log.info("saveRoleUsers  param error.");
            return new RResult(RResult.MSG_FAIL, RResult.paramNull, " ");
        }
        String[] uIds = StringUtils.split(userIds, ",");
        for (int i = 0; i < uIds.length; i++) {
            Integer userId = Integer.parseInt(uIds[i]);
            if (!isExist(entity.getRoleId(), userId)) {
                entity.setId(null);
                entity.setUserId(userId);
                save(entity);
            }
        }
        return new RResult(RResult.MSG_SUCCESS);
    }


    public RResult updateRoleUsers(SyRoleUser entity, String ids) {
        String[] idArray = StringUtils.split(ids, ",");
        for (int i = 0; i < idArray.length; i++) {
            Integer id = Integer.parseInt(idArray[i]);
            SyRoleUser syRoleUser = load(id);
            if (null != syRoleUser) {
                syRoleUser.setStatus(entity.getStatus());
                update(syRoleUser);
            }
        }
        return new RResult(RResult.MSG_SUCCESS);
    }

    public List<SyRoleUser> findUserRole(Integer userId, String status) {
        log.info("findUserRole param[userId=" + userId + "], status=" + status + "]");
        if (null == userId) {
            log.info("findUserRole param error. userId is null");
            return null;
        }
        SyRoleUser syRoleUser = new SyRoleUser();
        syRoleUser.setUserId(userId);
        syRoleUser.setStatus(status);
        return findList(syRoleUser, null);
    }

    private List<Integer> findRolesByUserId(Integer userId) {
        if (null == userId) {
            log.info("findRolesByUserId userId  is empty");
            return null;
        }

        List<Integer> roleList = new ArrayList<Integer>();
        // 用户-绑定及解除的角色
        List<SyRoleUser> syRoleUserList = findUserRole(userId, null);
        if (!CollectionUtils.isEmpty(syRoleUserList)) {
            for (int i = 0; i < syRoleUserList.size(); i++) {
                SyRoleUser syRoleUser = syRoleUserList.get(i);
                // 用户-绑定角色
                roleList.add(syRoleUser.getRoleId());
            }
        }

        return roleList;
    }


    public Integer saveRoleUser(Integer userId, Integer roleId, Integer operateUserId) {
        if (null == userId || null == roleId || null == operateUserId) {
            return null;
        }
        if (isExist(roleId, userId)) {
            return null;
        }
        SyRoleUser syRoleUser = new SyRoleUser();
        syRoleUser.setCreateUserId(operateUserId);
        syRoleUser.setUserId(userId);
        syRoleUser.setRoleId(roleId);
        syRoleUser.setStatus("B");
        return save(syRoleUser);
    }


    public String getCatalogPowerByUserId(Integer userId) {

        List<Integer> roleIdList = findRolesByUserId(userId);
        // 用户目录权限
        String catalogPower = ",";
        if (CollectionUtils.isEmpty(roleIdList)) {
            return catalogPower;
        }
        for (int i = 0; i < roleIdList.size(); i++) {
            SyCatalogRole syCatalogRole = syCatalogRoleService.loadByRoleId(roleIdList.get(i));
            if (null != syCatalogRole) {
                catalogPower += syCatalogRole.getPowerNodeIds() + ",";
            }
        }
        return catalogPower;
    }
    /// ***********************method end*************************

}

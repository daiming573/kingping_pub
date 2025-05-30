package com.cat.sy.service;
/// ***********************import begin***********************

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cat.bean.PageControlInfo;
import com.cat.bean.RResult;
import com.cat.sy.dao.SyCatalogRoleDao;
import com.cat.sy.entity.SyCatalogRole;
import com.common.util.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/// ***********************import end*************************
@Slf4j
@Service("syCatalogRoleService")
public class SyCatalogRoleService extends ServiceImpl<SyCatalogRoleDao, SyCatalogRole> {

    /// ***********************define begin***********************
    /// ***********************define end*************************

    public Integer save(SyCatalogRole syCatalogRole) {
        if (null == syCatalogRole) {
            log.info("save  is empty syCatalogRole");
            return null;
        }
        this.saveBefore(syCatalogRole);
        syCatalogRole.setCreateTime(DateUtil.getCurrentTimestamp());
        return baseMapper.insert(syCatalogRole);
    }


    public boolean delete(Integer id) {
        if (null == id) {
            log.info("delete  is empty ");
            return false;
        }
        SyCatalogRole entity = load(id);
        this.deleteBefore(entity);
        if (null == entity) {
            log.info("delete record not exist. id=" + id);
            return false;
        }
        return this.deleteById(id);
    }


    public boolean deleteByIds(String ids) {
        if (StringUtils.isBlank(ids)) {
            log.info("deleteByIds ids is empty ");
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


    public boolean update(SyCatalogRole syCatalogRole) {
        if (null == syCatalogRole) {
            log.info("update  is empty syCatalogRole");
            return false;
        }
        Integer id = syCatalogRole.getId();
        if (null == id) {
            log.info("update syCatalogRole.id is empty");
            return false;
        }
        Integer result = baseMapper.updateById(syCatalogRole);
        return result > 0;
    }


    public RResult update(SyCatalogRole oldEntity, SyCatalogRole newEntity) {
        if (null == newEntity) {
            log.info("update param is empty");
            return new RResult(RResult.MSG_FAIL, RResult.paramNull, "entity");
        }
        Integer result = baseMapper.updateById(newEntity);
        if (result > 0) {
            return new RResult(RResult.MSG_SUCCESS);
        }

        return new RResult(RResult.MSG_FAIL);
    }


    public SyCatalogRole load(Integer id) {
        if (null == id) {
            log.info("load {0} is empty id");
            return null;
        }
        return baseMapper.selectById(id);
    }


    public SyCatalogRole load(SyCatalogRole syCatalogRole) {
        if (null == syCatalogRole) {
            log.info("load syCatalogRole is empty ");
            return null;
        }
        return baseMapper.selectOne(syCatalogRole);
    }


    public Integer loadCount(SyCatalogRole syCatalogRole) {
        return baseMapper.selectCount(new EntityWrapper<>(syCatalogRole));
    }


    public boolean isExist(SyCatalogRole syCatalogRole) {
        Integer count = loadCount(syCatalogRole);
        if (null == count) {
            return false;
        }
        return count.intValue() > 0;
    }


    public List<SyCatalogRole> findList(SyCatalogRole syCatalogRole, Map<String, Object> params) {
        return baseMapper.selectList(new EntityWrapper<>(syCatalogRole));
    }

    public PageControlInfo findPageList(SyCatalogRole syCatalogRole, Integer skip, Integer max, Map<String, Object> params) {
        Page<SyCatalogRole> page = new Page<>(skip, max);
        Page<SyCatalogRole> syCatalogRolePage = this.selectPage(page, new EntityWrapper<>(syCatalogRole));
        PageControlInfo pageControlInfo = new PageControlInfo();
        pageControlInfo.setTotalNum((int) page.getTotal());
        pageControlInfo.setSearchData(syCatalogRolePage.getRecords());
        pageControlInfo.setStart(page.getCurrent());
        pageControlInfo.setMax(max);
        return pageControlInfo;
    }

    /// ***********************method begin***********************

    /**
     * 保存之前处理
     *
     * @param syCatalogRole 实体
     */
    private void saveBefore(SyCatalogRole syCatalogRole) {

    }


    /**
     * 删除之前处理
     *
     * @param syCatalogRole 实体
     */
    private void deleteBefore(SyCatalogRole syCatalogRole) {

    }

    /**
     * 删除之后处理
     *
     * @param syCatalogRole 实体
     */
    private void deleteAfter(SyCatalogRole syCatalogRole) {

    }


    public SyCatalogRole loadByRoleId(Integer roleId) {

        SyCatalogRole syCatalogRole = new SyCatalogRole();
        syCatalogRole.setRoleId(roleId);
        syCatalogRole.setIsValid("Y");
        List<SyCatalogRole> list = findList(syCatalogRole, null);
        if (null != list && list.size() > 0) {
            //一个角色只有一个权限
            return list.get(0);
        }
        return null;
    }

    /// ***********************method end*************************

}

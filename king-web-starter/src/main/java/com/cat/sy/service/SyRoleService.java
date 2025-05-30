package com.cat.sy.service;

/// ***********************import begin***********************

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cat.bean.PageControlInfo;
import com.cat.bean.RResult;
import com.cat.sy.dao.SyRoleDao;
import com.cat.sy.entity.SyRole;
import com.common.util.UUIDUtil;
import com.common.util.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/// ***********************import end*************************
@Slf4j
@Service("syRoleService")
public class SyRoleService extends ServiceImpl<SyRoleDao, SyRole> {

    // / ***********************define begin***********************
    // / ***********************define end*************************

    public Integer save(SyRole syRole) {
        if (null == syRole) {
            log.info("save {0} is empty  syRole");
            return null;
        }
        syRole.setUuid(UUIDUtil.get32UUID());
        syRole.setCreateTime(DateUtil.getCurrentTimestamp());
        return baseMapper.insert(syRole);
    }


    public boolean delete(Integer id) {
        if (null == id) {
            log.info("delete  id is empty ");
            return false;
        }
        SyRole entity = load(id);
        if (null == entity) {
            log.info("deleterecord not exist.");
            return false;
        }
        return this.deleteById(id);
    }


    public boolean deleteByIds(String ids) {
        if (StringUtils.isBlank(ids)) {
            log.info("deleteByIds ids is empty");
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


    public boolean update(SyRole syRole) {
        if (null == syRole) {
            log.info("update SyRole is empty");
            return false;
        }
        Integer id = syRole.getId();
        if (null == id) {
            log.info("update SyRole.id is empty");
            return false;
        }
        Integer result = baseMapper.updateById(syRole);

        return result > 0;
    }


    public RResult update(SyRole oldEntity, SyRole newEntity) {
        if (null == newEntity) {
            log.info("update param is empty");
            return new RResult(RResult.MSG_FAIL, RResult.paramNull, "newEntity");
        }

        Integer result = baseMapper.updateById(newEntity);
        if (result > 0) {
            return new RResult(RResult.MSG_SUCCESS);
        } else {
            return new RResult(RResult.MSG_FAIL);
        }
    }


    public SyRole load(Integer id) {
        if (null == id) {
            log.info("load id is empty");
            return null;
        }
        return baseMapper.selectById(id);
    }


    public SyRole load(SyRole syRole) {
        if (null == syRole) {
            log.info("load  syRole is empty");
            return null;
        }
        return baseMapper.selectOne(syRole);
    }


    public Integer loadCount(SyRole SyRole) {
        return baseMapper.selectCount(new EntityWrapper<>(SyRole));
    }


    public boolean isExist(SyRole SyRole) {
        Integer count = loadCount(SyRole);
        if (null == count) {
            return false;
        }
        return count.intValue() > 0;
    }


    public List<SyRole> findList(SyRole syRole, Map<String, Object> params) {
        return baseMapper.selectList(new EntityWrapper<>(syRole));
    }


    public PageControlInfo findPageList(SyRole syRole, Integer skip, Integer max, Map<String, Object> params) {
        Page<SyRole> page = new Page<>(skip, max);
        Page<SyRole> syRolePage = this.selectPage(page, new EntityWrapper<>(syRole));
        PageControlInfo pageControlInfo = new PageControlInfo();
        pageControlInfo.setTotalNum((int) page.getTotal());
        pageControlInfo.setSearchData(syRolePage.getRecords());
        pageControlInfo.setStart(page.getCurrent());
        pageControlInfo.setMax(max);
        return pageControlInfo;
    }


    //	public PageControlInfo findPageHsList(SyRole syRole, Integer skip, Integer max, Map<String, Object> params) {
    //		return baseMapper.findPageHsList(syRole, skip, max, params);
    //	}


    // / ***********************method begin***********************

    public SyRole loadByCode(String code) {
        SyRole syRole = new SyRole();
        syRole.setCode(code);
        return baseMapper.selectOne(syRole);
    }
    // / ***********************method end*************************

}

package com.cat.sy.service;

/// ***********************import begin***********************

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cat.bean.PageControlInfo;
import com.cat.sy.dao.SyUserDao;
import com.cat.sy.entity.SyCatalogRole;
import com.cat.sy.entity.SyRoleUser;
import com.cat.sy.entity.SyUser;
import com.common.safe.DesUtils;
import com.common.util.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/// ***********************import end*************************
@Slf4j
@Service("syUserService")
public class SyUserService extends ServiceImpl<SyUserDao, SyUser> {

    // / ***********************define begin***********************
    @Autowired
    private SyRoleUserService syRoleUserService;

    @Autowired
    private SyCatalogRoleService syCatalogRoleService;

    // / ***********************define end*************************

    public Integer save(SyUser syUser) {
        if (null == syUser) {
            log.info("save syUser is empty");
            return null;
        }
        syUser.setCreateTime(DateUtil.getCurrentTimestamp());
        syUser.setPassword(MD5Encoder.encode(syUser.getPassword().getBytes()));
        return baseMapper.insert(syUser);
    }

    public boolean delete(Integer id) {
        if (null == id) {
            log.info("delete  is empty ");
            return false;
        }
        SyUser entity = load(id);
        if (null == entity) {
            log.info("delete record not exist.");
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
        List<Long> list = new ArrayList<Long>();
        for (String id : array) {
            list.add(Long.valueOf(id));
        }
        Integer result = baseMapper.deleteBatchIds(list);
        return result > 0;
    }

    public boolean update(SyUser syUser) {
        if (null == syUser) {
            log.info("update syUser is empty");
            return false;
        }
        return this.updateById(syUser);
    }

    public SyUser load(Integer id) {
        if (null == id) {
            log.info("load  id is empty");
            return null;
        }
        return baseMapper.selectById(id);
    }

    public SyUser load(SyUser syUser) {
        if (null == syUser) {
            log.info("load  is empty");
            return null;
        }
        return baseMapper.selectOne(syUser);
    }

    public Integer loadCount(SyUser syUser) {
        EntityWrapper<SyUser> entityWrapper = new EntityWrapper<>();
        entityWrapper.setEntity(syUser);
        return baseMapper.selectCount(entityWrapper);
    }

    public boolean isExist(SyUser syUser) {
        Integer count = loadCount(syUser);
        if (null == count) {
            return false;
        }
        return count.intValue() > 0;
    }

    public List<SyUser> findList(SyUser syUser, Map<String, Object> params) {
        return baseMapper.selectList(new EntityWrapper<>(syUser));
    }

    public PageControlInfo findPageList(SyUser syUser, Integer skip, Integer max, Map<String, Object> params) {
        Page<SyUser> page = new Page<>(skip, max);
        Page<SyUser> syUserPage = this.selectPage(page, new EntityWrapper<>(syUser));
        PageControlInfo pageControlInfo = new PageControlInfo();
        pageControlInfo.setTotalNum((int) page.getTotal());
        pageControlInfo.setSearchData(syUserPage.getRecords());
        pageControlInfo.setStart(page.getCurrent());
        pageControlInfo.setMax(max);
        return pageControlInfo;
    }

    // / ***********************method begin***********************

    public List<Integer> findRolesByUserId(Integer userId) {
        if (null == userId) {
            log.info("findRolesByUserId userId  is empty");
            return null;
        }

        List<Integer> roleList = new ArrayList<Integer>();
        // 根据用户I的 获取-绑定及解除的角色,
        List<SyRoleUser> syRoleUserList = syRoleUserService.findUserRole(userId, null);
        if (!CollectionUtils.isEmpty(syRoleUserList)) {
            // 判断List不为空，避免出现空指针
            for (int i = 0; i < syRoleUserList.size(); i++) {
                SyRoleUser syRoleUser = syRoleUserList.get(i);
                // 用户-绑定角色，根据用户的状态来绑定对应权限
                if (StringUtils.equals("B", syRoleUser.getStatus())) {
                    roleList.add(syRoleUser.getRoleId());
                }
            }
        }

        return roleList;
    }

    public String getCatalogPowerByUserId(Integer userId) {
        List<Integer> roleIdList = findRolesByUserId(userId);
        // 用户目录权限
        String catalogPower = ",";
        if (CollectionUtils.isEmpty(roleIdList)) {
            return catalogPower;
        }
        for (int i = 0; i < roleIdList.size(); i++) {
            // 一个角色只有一个权限
            SyCatalogRole syCatalogRole = syCatalogRoleService.loadByRoleId(roleIdList.get(i));
            if (null != syCatalogRole) {
                catalogPower += syCatalogRole.getPowerNodeIds();
            }
        }
        return catalogPower + ",";
    }

    public SyUser loadByloginPassport(String userName) {
        if (StringUtils.isBlank(userName)) {
            log.info("loadByloginPassport  is empty ");
            return null;
        }
        SyUser entity = new SyUser();
        entity.setUserName(userName);
        return baseMapper.selectOne(entity);
    }

    /**
     * 关闭状态
     */
    private static final int Close_Status = 0;

    /**
     * 开启状态
     */
    private static final int Open_Status = 1;

    public void set(int i) {
        if (i == Close_Status) {
            // 做处理
        } else if (i == Open_Status) {
            // 做处理
        }
    }

    public boolean resetPassword(Integer userId, String password) {
        if (null == userId || 0 == userId) {
            return false;
        }
        return baseMapper.updateForResetPassword(userId, MD5Encoder.encode(password.getBytes())) > 0;
    }

    public Integer updatePassword(Integer userId, String password, String newPassword) {
        password = MD5Encoder.encode(password.getBytes());
        newPassword = MD5Encoder.encode(newPassword.getBytes());
        return baseMapper.updatePassword(userId, password, newPassword);
    }

    private static final long tokenTime = 30 * 60 * 1000;
    // / ***********************method end*************************

    /**
     * 修改用户token
     *
     * @param id
     *
     * @return
     */
    public String updateToken(Integer id) {
        long outTime = new Date().getTime() + tokenTime;
        Timestamp t = new Timestamp(outTime);
        String token = getUserToken(id + "");
        baseMapper.updateForToken(id, token, t);
        return token;
    }

    public SyUser loadUserByToken(String token) {
        SyUser loadUser = new SyUser();
        loadUser.setToken(token);
        return baseMapper.selectOne(loadUser);
    }

    private String getUserToken(String userId) {
        String time = DateUtil.getCurrentTimeSssStr();
        String r = String.valueOf((int) (Math.random() * 10000));
        try {
            return DesUtils.encrypt("@" + time + "@" + userId + "@", "1232123220191116");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

}

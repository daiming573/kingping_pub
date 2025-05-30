package com.king.wechat.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.king.wechat.dao.WxEnvDao;
import com.king.wechat.entity.WxEnv;
import com.king.wechat.service.WxEnvService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author daiming
 * @since 2019-11-22
 */
@Service
public class WxEnvServiceImpl extends ServiceImpl<WxEnvDao, WxEnv> implements WxEnvService {

    @Override
    public WxEnv getWxEnvByKey(String envKey) {
        if (ObjectUtils.isEmpty(envKey)) {
            return null;
        }
        WxEnv wxEnv = new WxEnv();
        wxEnv.setEnvKey(envKey);
        return baseMapper.selectOne(wxEnv);
    }

    @Override
    public boolean saveOrUpdateEvn(String accessTokenKey, String accessToken, Date validTime) {
        WxEnv loadKey = getWxEnvByKey(accessTokenKey);
        if (null == loadKey) {
            return saveEvn(accessTokenKey, accessToken, validTime);
        } else {
            return updateKeyInvalidTime(loadKey, accessToken, validTime);
        }
    }

    /**
     * 添加key
     * @param envKey    key
     * @param accessToken   token
     * @param invalidTime   过期时间
     * @return  boolean
     */
    public boolean saveEvn(String envKey, String accessToken, Date invalidTime) {
        WxEnv saveEnv = new WxEnv();
        saveEnv.setEnvKey(envKey);
        saveEnv.setEnvValue(accessToken);
        saveEnv.setInvalidTime(invalidTime);
        saveEnv.setCreateTime(new Date());
        saveEnv.setUpdateTime(new Date());
        return this.insert(saveEnv);
    }

    /**
     * 更新key过期时间
     * @param wxEnv key记录
     * @param accessToken   token
     * @param invalidTime   过期时间
     * @return  boolean
     */
    public boolean updateKeyInvalidTime(WxEnv wxEnv, String accessToken, Date invalidTime) {
        if (null == wxEnv) {
            return false;
        }
        wxEnv.setEnvValue(accessToken);
        wxEnv.setInvalidTime(invalidTime);
        wxEnv.setUpdateTime(new Date());
        return this.updateById(wxEnv);
    }

}

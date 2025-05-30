package com.king.wechat.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.king.wechat.dao.WxMiniappListDao;
import com.king.wechat.entity.WxMiniappList;
import com.king.wechat.service.WxMiniappListService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author daiming
 * @since 2019-11-24
 */
@Service
public class WxMiniappListServiceImpl extends ServiceImpl<WxMiniappListDao, WxMiniappList> implements WxMiniappListService {

    @Override
    public List<WxMiniappList> selectMiniappList() {
        WxMiniappList wxMiniappList = new WxMiniappList();
        wxMiniappList.setStatus(WxMiniappList.STATUS_ON);
        return this.selectList(new EntityWrapper<>(wxMiniappList));
    }

    @Override
    public WxMiniappList selectMiniapp(String type) {
        if (ObjectUtils.isEmpty(type)) {
            return null;
        }
        WxMiniappList wxMiniappList = new WxMiniappList();
        wxMiniappList.setStatus(WxMiniappList.STATUS_ON);
        wxMiniappList.setMiniappCode(type);
        return this.selectOne(new EntityWrapper<>(wxMiniappList));
    }
}

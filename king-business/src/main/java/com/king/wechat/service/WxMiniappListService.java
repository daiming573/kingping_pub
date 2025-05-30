package com.king.wechat.service;

import com.baomidou.mybatisplus.service.IService;
import com.king.wechat.entity.WxMiniappList;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author daiming
 * @since 2019-11-24
 */
public interface WxMiniappListService extends IService<WxMiniappList> {

    /**
     * 查询小程序列表
     * @return  List<WxMiniappList>
     */
    List<WxMiniappList> selectMiniappList();

    /**
     * 查询小程序
     * @param type  类型
     * @return  List<WxMiniappList>
     */
    WxMiniappList selectMiniapp(String type);
}

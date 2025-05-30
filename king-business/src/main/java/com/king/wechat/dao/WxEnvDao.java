package com.king.wechat.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.king.wechat.entity.WxEnv;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author daiming
 * @since 2019-11-22
 */
public interface WxEnvDao extends BaseMapper<WxEnv> {

    List<WxEnv> getWxEnvByCode(WxEnv wxEnv);
}

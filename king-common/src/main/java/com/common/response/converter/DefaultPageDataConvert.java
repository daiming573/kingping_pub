package com.common.response.converter;

import com.baomidou.mybatisplus.plugins.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author daiming5
 * @version V1.0
 */
public class DefaultPageDataConvert implements IPageConverter {
    public DefaultPageDataConvert() {
    }

    @Override
    public Object convertPageData(Object pageDataSrc) {
        if (pageDataSrc instanceof Page) {
            Page pageBean = (Page) pageDataSrc;
            Map<String, Object> pageData = new HashMap<>(5);
            pageData.put("list", pageBean.getRecords());
            pageData.put("total", pageBean.getTotal());
            pageData.put("pageSize", pageBean.getSize());
            pageData.put("pageNo", pageBean.getCurrent());
            pageData.put("totalPage", pageBean.getPages());
            return pageData;
        } else {
            return pageDataSrc;
        }
    }
}
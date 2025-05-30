package com.cat.sy.dao;
/// ***********************import begin***********************

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cat.sy.entity.SyCatalogNode;

import java.util.Map;


/// ***********************import end*************************

/**
 * 表名：sy_catalog_node
 *
 * @author Administrator
 */
public interface SyCatalogNodeDao extends BaseMapper<SyCatalogNode> {

    /// ***********************method begin***********************

    Map<String, Object> save(SyCatalogNode syCatalogNode);

    /// ***********************method end*************************
}

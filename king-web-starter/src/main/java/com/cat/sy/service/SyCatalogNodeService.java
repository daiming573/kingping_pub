package com.cat.sy.service;
/// ***********************import begin***********************

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cat.bean.PageControlInfo;
import com.cat.bean.RResult;
import com.cat.bean.tree.easyUI.IconTree;
import com.cat.sy.dao.SyCatalogNodeDao;
import com.cat.sy.entity.SyCatalogNode;
import com.common.exception.RequestException;
import com.common.util.UUIDUtil;
import com.common.util.date.DateUtil;
import com.common.util.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/// ***********************import end*************************
@Slf4j
@Service("syCatalogNodeService")
public class SyCatalogNodeService extends ServiceImpl<SyCatalogNodeDao, SyCatalogNode> {

    private static String IconJson = "";

    /// ***********************define begin***********************
    /// ***********************define end*************************

    public Integer save(SyCatalogNode syCatalogNode) {
        if (null == syCatalogNode) {
            log.info("save syCatalogNode  is empty");
            return null;
        }
        syCatalogNode.setUuid(UUIDUtil.get32UUID());
        syCatalogNode.setCreateTime(DateUtil.getCurrentTimestamp());
        Map<String, Object> map = baseMapper.save(syCatalogNode);
        return Integer.parseInt(map.get("id").toString());
    }


    public boolean delete(Integer id) {
        if (null == id) {
            log.info("delete id is empty");
            return false;
        }
        SyCatalogNode entity = load(id);
        if (null == entity) {
            log.info("delete record not exist.");
            return false;
        }
        return this.deleteById(id);
    }


    @Transactional(rollbackFor = Exception.class)
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


    public boolean update(SyCatalogNode syCatalogNode) {
        if (null == syCatalogNode) {
            log.info("update  is empty  syCatalogNode");
            return false;
        }
        Integer id = syCatalogNode.getId();
        if (null == id) {
            log.info("update  is empty syCatalogNode.id");
            return false;
        }
        Integer result = baseMapper.updateById(syCatalogNode);
        return result > 0;
    }


    public RResult update(SyCatalogNode oldEntity, SyCatalogNode newEntity) {
        if (null == newEntity) {
            log.info("update param is empty");
            return new RResult(RResult.MSG_FAIL, RResult.paramNull, "entity");
        }
        boolean result = this.updateById(newEntity);
        if (result) {
            return new RResult(RResult.MSG_SUCCESS);
        }
        return new RResult(RResult.MSG_FAIL);
    }


    public SyCatalogNode load(Integer id) {
        if (null == id) {
            log.info("load id is empty");
            return null;
        }
        return baseMapper.selectById(id);
    }


    public SyCatalogNode load(SyCatalogNode syCatalogNode) {
        if (null == syCatalogNode) {
            log.info("load  is empty syCatalogNode");
            return null;
        }
        return baseMapper.selectOne(syCatalogNode);
    }


    public Integer loadCount(SyCatalogNode syCatalogNode) {
        return baseMapper.selectCount(new EntityWrapper<>(syCatalogNode));
    }


    public boolean isExist(SyCatalogNode syCatalogNode) {
        Integer count = loadCount(syCatalogNode);
        if (null == count) {
            return false;
        }
        return count.intValue() > 0;
    }


    public List<SyCatalogNode> findList(SyCatalogNode syCatalogNode, Integer skip, Integer max, Map<String, Object> params) {
        return baseMapper.selectList(new EntityWrapper<>(syCatalogNode));
    }


    public PageControlInfo findPageList(SyCatalogNode syCatalogNode, Integer skip, Integer max, Map<String, Object> params) {
        Page<SyCatalogNode> page = new Page<>(skip, max);
        Page<SyCatalogNode> syCatalogNodePage = this.selectPage(page, new EntityWrapper<>(syCatalogNode));
        PageControlInfo pageControlInfo = new PageControlInfo();
        pageControlInfo.setTotalNum((int) page.getTotal());
        pageControlInfo.setSearchData(syCatalogNodePage.getRecords());
        pageControlInfo.setStart(page.getCurrent());
        pageControlInfo.setMax(max);
        return pageControlInfo;
    }

    /// ***********************method begin***********************

    public List<SyCatalogNode> findNode1(String linkCatalog) {
      /*List<SyCatalogNode> list = baseMapper.findNode(linkCatalog);
      if(RList.isBlank(list)){
         log.info( "findNode list is empty.");
         return null;
      }
      return list;*/
        return null;
    }


    public List<SyCatalogNode> findChildrensNode(String catalogId) {
        log.info("findChildrensNode params: [catalogId=" + catalogId + "]");
        EntityWrapper<SyCatalogNode> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("catalog_id", catalogId);
        List<SyCatalogNode> list = baseMapper.selectList(entityWrapper.orderBy("DISP_ORDER"));
        if (CollectionUtils.isEmpty(list)) {
            log.info("findChildrensNode list is empty.");
            return null;
        }
        return list;
    }


    public List<SyCatalogNode> findChildNodes(String parentId) {
        EntityWrapper<SyCatalogNode> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("parent_id", parentId);
        entityWrapper.eq("is_valid", "Y");
        // 只显示有效的
        List<SyCatalogNode> list = baseMapper.selectList(entityWrapper.orderBy("DISP_ORDER"));
        if (CollectionUtils.isEmpty(list)) {
            log.info("findChildNodes list is empty.");
            return null;
        }
        return list;
    }


    public List<SyCatalogNode> findAllChildNodes(String parentId) {
        EntityWrapper<SyCatalogNode> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("parent_id", parentId);
        // 全部显示
        List<SyCatalogNode> list = baseMapper.selectList(entityWrapper.orderBy("DISP_ORDER"));
        if (CollectionUtils.isEmpty(list)) {
            log.info("findChildNodes list is empty.");
            return null;
        }
        return list;
    }


    public SyCatalogNode findNodeById(Integer id) {
        // TODO Auto-generated method stub
        return baseMapper.selectById(id);
    }

    /**
     * 遍历子节点
     *
     * @param path 根路径
     *
     * @return
     */
    public List<IconTree> getFile(StringBuffer buff, String path) {
        try {
            List<IconTree> list = new ArrayList<IconTree>();
            File file = new File(path);
            String name = file.getName();
            //获取所有的文件和文件夹
            File[] fs = file.listFiles();
            for (File f2 : fs) {
                if (f2.isFile()) {
                    //是文件
                    list.add(creatTree(buff, name, f2));
                }
            }
            return list;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }


    private IconTree creatTree(StringBuffer buff, String headName, File file) {
        IconTree t = new IconTree();
        String name = headName + "-" + file.getName();
        if (file.isDirectory()) {
            t.setState("closed");
            name = file.getName();
        } else {
            name = headName + "-" + file.getName();
            name = StringUtils.left(name, name.indexOf("."));
            t.setIconCls(name);
            if (null != buff) {
                buff.append(".").append(name).append("{ background: url(\"../images/sys/").append(headName).append("/").append(file.getName()).append("\") no-repeat center center; }\r\n");
            }
        }
        t.setCode(name);
        t.setId(name);
        t.setText(name);
        return t;
    }

    /// ***********************method end*************************


    public String getAllIconImgJson(String basePath, String cssFilePath) {
        if (StringUtils.isNotBlank(IconJson)) {
            return IconJson;
        }
        try {
            IconJson = getIconJson(basePath, cssFilePath);
        } catch (RequestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return IconJson;
    }

    private String getIconJson(String basePath, String cssFilePath) throws RequestException {
        File file = new File(basePath);
        if (file.isFile()) {
            return "";
        }
        List<IconTree> tree = new ArrayList<IconTree>();
        StringBuffer cssBuffer = new StringBuffer();
        //获取所有子文件夹
        File[] rootFile = file.listFiles();
        for (File childFFile : rootFile) {
            if (childFFile.isDirectory()) {
                //是文件夹 创建节点
                IconTree pTree = creatTree(null, "", childFFile);
                //遍历子文件
                List<IconTree> list = getFile(cssBuffer, childFFile.getAbsolutePath());
                if (null != list) {
                    pTree.setChildren(list);
                }
                tree.add(pTree);
            }
        }
        try {
            createCssStyle(cssBuffer.toString(), cssFilePath);
        } catch (Exception e) {
            // TODO: handle exception
        }
        //      return   RJson.getJsonArray(tree).toString();
        return JsonUtil.toJson(tree);
    }

    private void createCssStyle(String cssInfo, String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();// 创建父目录
            file.createNewFile();
        }
        FileWriter desWriter = new FileWriter(file);
        desWriter.write(cssInfo);
        desWriter.close();
    }
}


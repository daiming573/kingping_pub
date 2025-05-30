package com.cat.sy.action;

/// ***********************import begin***********************

import com.cat.bean.EOperation;
import com.cat.bean.RResult;
import com.cat.bean.tree.Node;
import com.cat.bean.tree.easyUI.Tree;
import com.cat.sy.action.base.WebBaseAction;
import com.cat.sy.entity.SyCatalogNode;
import com.cat.sy.entity.SyCatalogRole;
import com.cat.sy.entity.SyUser;
import com.cat.sy.service.SyCatalogNodeService;
import com.cat.sy.service.SyCatalogRoleService;
import com.common.exception.RequestException;
import com.common.reflect.RReflectUtils;
import com.common.util.json.JsonUtil;
import com.mchange.v2.beans.BeansUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/// ***********************import end*************************
@Slf4j
@RequestMapping("sy/catalogNode")
@Controller
@Scope("prototype")
public class SyCatalogNodeAction extends WebBaseAction {

    @Autowired
    private SyCatalogNodeService syCatalogNodeService;

    @Autowired
    private SyCatalogRoleService syCatalogRoleService;
    /**
     * 请求访问路径
     */
    private static final String requestPath = "sy/catalog/";

    // / ***********************define begin***********************

    /**
     * 跳转目录树页面
     */
    @RequestMapping("goSyCatalogTree")
    public String goSyCatalogTree() {
        setAttributes();
        return requestPath + "syCatalogTree";
    }

    /**
     * 跳转目录树页面
     */
    @RequestMapping("goSyCatalogNodeForm")
    public String goSyCatalogNodeForm() {
        setAttributes();
        return requestPath + "syCatalogNodeForm";
    }

    // / ***********************define end*************************

    /**
     * 介绍页面
     */
    @RequestMapping("goIntroduce")
    public String goIntroduce() {
        setAttributes();
        return requestPath + "introduce";
    }

    /**
     * 新增页面
     */
    @RequestMapping("goAdd")
    public String goAdd() {
        setAttributes();
        request.setAttribute("operate", EOperation.Add.value());
        return requestPath + "syCatalogForm";
    }


    /**
     * 查看页面
     */
    @RequestMapping("goView")
    public String goView() {
        setAttributes();
        request.setAttribute("operate", EOperation.View.value());
        return requestPath + "syCatalogForm";
    }

    /**
     * 编辑页面
     */
    @RequestMapping("goEdit")
    public String goEdit() {
        setAttributes();
        request.setAttribute("operate", EOperation.Update.value());
        return requestPath + "syCatalogForm";
    }


    // / ***********************method begin***********************


    /**
     * 查询目录节点
     *
     * @throws RequestException
     */
    @RequestMapping("findCatalogTreeNode")
    public void findCatalogTreeNode() throws RequestException {
        String id = getRequestParameter("id");
        if (StringUtils.isBlank(id)) {
            id = "0";
        }

        List<SyCatalogNode> nodes = syCatalogNodeService.findAllChildNodes(id);
        if (CollectionUtils.isEmpty(nodes)) {
            return;
        }
        List<Node> list = new ArrayList<Node>();
        for (SyCatalogNode node : nodes) {
            Node n = new Node();
            BeanUtils.copyProperties(node, n);
            list.add(n);
        }
        getTreeJson(list);
    }

    /**
     * 查询目录节点
     *
     * @throws RequestException
     */
    @RequestMapping("findAllTree")
    public void findAllTree() throws RequestException {
        String rootId = getRequestParameter("rootId");
        // 角色节点
        String roleId = getRequestParameter("roleId");

        String id = getRequestParameter("id");

        if (StringUtils.isNotBlank(id)) {
            //查询全部的树节点，但是是异步的话，后面有文件夹为空的情况，就会继续加载，防止继续加载全部树回去，做限制
            return;
        }
        if (StringUtils.isAnyBlank(rootId, roleId)) {
            return;
        }
        List<Tree> list = new ArrayList<Tree>();

        SyCatalogRole syCatalogRole = syCatalogRoleService.loadByRoleId(Integer.parseInt(roleId));
        String nodeIds = "";
        if (null != syCatalogRole) {
            nodeIds = syCatalogRole.getNodeIds();
        }
        list = getTree(rootId, nodeIds);
        writeAllTree(list);
    }

    private void writeAllTree(List<Tree> list) throws RequestException {
        //		resultArray = RJson.getJsonStrArray(list);
        //		resultArray.join("{}");
        //		String json = resultArray.toString();
        getPrintWriter().write(JsonUtil.toJson(list));
    }

    private List<Tree> getTree(String id, String nodeIds) {
        List<Tree> list = new ArrayList<Tree>();
        List<SyCatalogNode> nodes = syCatalogNodeService.findChildNodes(id);
        addChilds(nodes, list, nodeIds);
        return list;
    }

    private void addChilds(List<SyCatalogNode> nodes, List<Tree> list, String nodeIds) {
        if (CollectionUtils.isEmpty(nodes)) {
            return;
        }
        for (SyCatalogNode node : nodes) {
            Node n = new Node();
            BeanUtils.copyProperties(node, n);
            Tree tree = new Tree();
            tree.setId(n.getId());
            tree.setText(n.getLabel());
            tree.setCode(n.getCode());
            tree.setIconCls(n.getIconName());
            tree.setUrl(n.getDoInvoke());
            tree.setLinkCatalog(n.getLinkCatalog());
            tree.setTypeName(n.getTypeName());
            tree.setRedirectUrl(n.getDoRedirect());
            tree.setOuterCatalog(n.getOuterCatalog());
            tree.setIntroduceUrl(n.getDoIntroduce());
            if (StringUtils.contains(nodeIds, String.valueOf(n.getId()))) {
                tree.setChecked(true);
            }
            if ("Y".equals(node.getHasChild())) {
                tree.setState("closed");
                tree.setChildren(getTree("" + n.getId(), nodeIds));
            }
            list.add(tree);
        }
    }

    /**
     * 获取一条目录节点
     *
     * @throws RequestException
     */
    @RequestMapping("getCatalogTreeNode")
    public void getCatalogTreeNode() throws RequestException {
        String id = getRequestParameter("id");
        log.info("getCatalogTreeNode id= " + id);
        if (StringUtils.isBlank(id)) {
            return;
        }
        SyCatalogNode syCatalogNode = syCatalogNodeService.findNodeById(Integer.parseInt(id));
        if (null == syCatalogNode) {
            return;
        }
        //		result = RJson.getJsonStr(syCatalogNode);
        getPrintWriter().write(JsonUtil.toJson(syCatalogNode));
    }

    /**
     * 删除目录节点
     */
    @RequestMapping("doDeleteCatalogNode")
    public void doDeleteCatalogNode() {
        try {
            String ids = getRequestParameter("ids");
            if (StringUtils.isBlank(ids)) {
                printWriterJson(new RResult(RResult.MSG_FAIL));
                return;
            }
            String[] values = StringUtils.split(ids, ",");
            boolean bool = false;
            if (values.length > 1) {
                bool = syCatalogNodeService.deleteByIds(ids);
            } else {
                bool = syCatalogNodeService.delete(Integer.parseInt(ids));
            }
            if (bool) {
                printWriterJson(new RResult(RResult.MSG_SUCCESS));
            } else {
                printWriterJson(new RResult(RResult.MSG_FAIL));
            }
        } catch (Exception e) {
            log.error("doDeleteCatalogNode exception", e);
        }
    }

    /**
     * 保存/更新目录节点
     */
    @RequestMapping("doSaveCatalogNode")
    public void doSaveCatalogNode() {
        try {
            SyUser pmUser = getSessionUser();
            String id = getRequestParameter("id");
            if (StringUtils.isBlank(id) || id.equals("0")) {
                Map<String, String[]> map = getRequestParameterMap();
                SyCatalogNode entity = new SyCatalogNode();
                RReflectUtils.getObjectListForMap(entity, map);
                entity.setCreateUserId(pmUser.getId());
                saveCatalogNode(entity);
                return;
            }
            updateCatalogNode(id, pmUser);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("doSaveCatalogNode exception", e);
            printWriterJson(new RResult(RResult.MSG_FAIL));
        }
    }


    /**
     * 保存目录节点逻辑
     *
     * @throws RequestException
     */
    private void saveCatalogNode(SyCatalogNode entity) throws RequestException {
        Integer id = syCatalogNodeService.save(entity);
        if (id > 0) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("code", "S");
            map.put("success", "true");
            map.put("note", "处理成功");
            map.put("id", id + "");
            //			result = RJson.getJsonStr(map);
            getPrintWriter().write(JsonUtil.toJson(map));
            return;
        }
        log.info("save save fail");
        printWriterJson(new RResult(RResult.MSG_FAIL));
    }

    /**
     * 修改目录节点逻辑
     *
     * @throws RequestException
     */
    private void updateCatalogNode(String id, SyUser pmUser) throws RequestException {
        SyCatalogNode oldEntity = syCatalogNodeService.load(Integer.parseInt(id));
        if (null == oldEntity) {
            log.info("update update fail record not exist, param[id= " + id);
            printWriterJson(new RResult(RResult.MSG_FAIL, RResult.paramNull));
            return;
        }
        SyCatalogNode newEntity = new SyCatalogNode();
        RReflectUtils.getObjectForObject(newEntity, oldEntity);
        Map<String, String[]> map = getRequestParameterMap();
        RReflectUtils.getObjectListForMap(newEntity, map);
        RResult info = syCatalogNodeService.update(oldEntity, newEntity);

        try {
            printWriterJson(info);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 保存/更新目录节点
     */
    @RequestMapping("doGerIconJson")
    public void doGerIconJson() {
        try {
            String path = request.getSession().getServletContext().getRealPath("/images/sys");
            String cssPath = request.getSession().getServletContext().getRealPath("/css/my_icon.css");
            String json = syCatalogNodeService.getAllIconImgJson(path, cssPath);
            getPrintWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("doGerIconJson exception", e);
        }
    }
    // / ***********************method end*************************

}

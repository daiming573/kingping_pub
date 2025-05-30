package com.cat.sy.action;

import com.cat.bean.tree.Node;
import com.cat.sy.action.base.WebBaseAction;
import com.cat.sy.entity.SyCatalogNode;
import com.cat.sy.entity.SyUser;
import com.cat.sy.service.SyCatalogNodeService;
import com.cat.sy.service.SyRoleUserService;
import com.common.anno.LoginNeed;
import com.common.enums.EMsg;
import com.common.exception.RequestException;
import com.common.http.HttpClientUtil;
import com.common.reflect.RReflectUtils;
import com.common.util.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RequestMapping("sy/main")
@Controller
@Scope("prototype")
public class MainAction extends WebBaseAction {

    @Autowired
    private SyCatalogNodeService syCatalogNodeService;

    @Autowired
    private SyRoleUserService syRoleUserService;

    @Autowired
    private HttpClientUtil httpClientUtil;

    @RequestMapping("goBaiduMap")
    public String goBaiduMap() {
        setAttributes();
        return "sy/main/baiduMap";
    }

    @RequestMapping("extCenter")
    @LoginNeed(false)
    public String extCenter() {
        String id = getRequestParameter("id");
        String introduceUrl = getRequestParameter("introduceUrl");
        String token = getRequestParameter("token");
        if (StringUtils.isBlank(token)) {
            System.out.println("TOKEN IS NULL....................");
            return null;
        }
        request.setAttribute("id", id);
        request.setAttribute("introduceUrl", introduceUrl);
        request.setAttribute("token", token);
        return "sy/main/centertoken";
    }

    /**
     * 兼容多种
     *
     * @return
     */
    @RequestMapping("centertoken")
    @LoginNeed(false)
    public String centertoken() {
        String id = getRequestParameter("id");
        String introduceUrl = getRequestParameter("introduceUrl");
        String token = getRequestParameter("token");
        if (StringUtils.isBlank(token)) {
            System.out.println("TOKEN IS NULL....................");
            return null;
        }
        request.setAttribute("id", id);
        request.setAttribute("introduceUrl", introduceUrl);
        request.setAttribute("token", token);
        return "sy/main/centertoken";
    }

    @RequestMapping("center")
    public String center() {
        String id = getRequestParameter("id");
        String introduceUrl = getRequestParameter("introduceUrl");
        request.setAttribute("id", id);
        request.setAttribute("introduceUrl", introduceUrl);
        return "sy/main/center";
    }

    /**
     * 介绍页面
     *
     * @return
     */
    @RequestMapping("goIntroduce")
    public String goIntroduce() {
        return "sy/main/introduce";
    }

    /**
     * 加载主菜单
     *
     * @throws RequestException
     */
    @RequestMapping(value = "loadMenu")
    public void loadMenu() throws RequestException {
        String id = getRequestParameter("id");
        List<SyCatalogNode> nodes = syCatalogNodeService.findChildNodes(id);
        if (CollectionUtils.isEmpty(nodes)) {
            return;
        }
        List<Node> list = new ArrayList<Node>();
        SyUser syUser = getSessionUser();
        String catalogPower = syRoleUserService.getCatalogPowerByUserId(syUser.getId());
        for (SyCatalogNode node : nodes) {
            if (StringUtils.contains(catalogPower, String.valueOf(node.getId()))) {
                Node n = new Node();
                RReflectUtils.getObjectForObject(n, node);
                list.add(n);
            }
        }
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        getTreeJson(list);
    }

    /**
     * 异步树
     *
     * @throws RequestException
     */
    @RequestMapping(value = "loadSubMenu")
    public void loadSubMenu() throws RequestException {
        String parentId = getRequestParameter("parentId");
        String id = getRequestParameter("id");
        log.info("loadSubMenu param: [parentId=" + parentId + ", id= " + id + "]");

        if (StringUtils.isBlank(parentId)) {
            return;
        }
        List<SyCatalogNode> nodes = null;
        if (null == id) {
            nodes = syCatalogNodeService.findChildNodes(parentId);
        } else {
            nodes = syCatalogNodeService.findChildNodes(id);
        }
        if (CollectionUtils.isEmpty(nodes)) {
            return;
        }
        SyUser pmUser = getSessionUser();
        String catalogPower = syRoleUserService.getCatalogPowerByUserId(pmUser.getId());
        List<Node> list = new ArrayList<Node>();
        for (SyCatalogNode node : nodes) {
            if (StringUtils.contains(catalogPower, String.valueOf(node.getId()))) {
                Node n = new Node();
                RReflectUtils.getObjectForObject(n, node);
                list.add(n);
            }
        }
        getTreeJson(list);
    }

    /**
     * 加载后台管理菜单
     *
     * @throws RequestException
     */
    @RequestMapping("loadTabs")
    public void loadTabs() throws RequestException {

        // 根目录的ID 为0
        List<SyCatalogNode> nodes = syCatalogNodeService.findChildNodes("0");
        if (CollectionUtils.isEmpty(nodes)) {
            return;
        }
        List<Node> data = new ArrayList<Node>();
        SyUser pmUser = getSessionUser();
        String catalogPower = syRoleUserService.getCatalogPowerByUserId(pmUser.getId());
        for (SyCatalogNode node : nodes) {
            if (StringUtils.contains(catalogPower, String.valueOf(node.getId()))) {
                Node n = new Node();
                RReflectUtils.getObjectForObject(n, node);
                data.add(n);
            }
        }
        getTreeJson(data);
    }

    /**
     * 待处理页面
     */
    @RequestMapping("doPending")
    public String doPending() {
        String typeCd = getRequestParameter("typeCd");
        request.setAttribute("typeCd", typeCd);
        return "sy/main/pending";
    }

    private void setSessionForUser(Map<String, Object> syUser, String token) {
        SyUser u = new SyUser();
        try {
            Integer id = Integer.parseInt(syUser.get("id").toString());
            u.setId(id);
        } catch (NumberFormatException e) {
        }
        u.setMobile(String.valueOf(syUser.get("mobile")));
        u.setUserName(String.valueOf(syUser.get("userName")));
        u.setUserId(String.valueOf(syUser.get("userId")));
        request.getSession().setAttribute("syUser", u);
        request.getSession().setAttribute("token", token);
    }
}

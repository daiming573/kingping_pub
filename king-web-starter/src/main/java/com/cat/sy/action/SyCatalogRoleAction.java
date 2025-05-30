package com.cat.sy.action;
/// ***********************import begin***********************

import com.cat.bean.EOperation;
import com.cat.bean.PageControlInfo;
import com.cat.bean.RResult;
import com.cat.sy.action.base.WebBaseAction;
import com.cat.sy.entity.SyCatalogRole;
import com.cat.sy.entity.SyUser;
import com.cat.sy.service.SyCatalogRoleService;
import com.common.exception.RequestException;
import com.common.reflect.RReflectUtils;
import com.common.util.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/// ***********************import end*************************
@Slf4j
@RequestMapping("sy/CatalogRole")
@Controller
@Scope("prototype")
public class SyCatalogRoleAction extends WebBaseAction {

    @Autowired
    private SyCatalogRoleService syCatalogRoleService;
    /**
     * 请求访问路径
     */
    private static final String requestPath = "sy/catalog/";
    /// ***********************define begin***********************

    /**
     * 角色选择目录
     */
    @RequestMapping("goRoleForm")
    public String goRoleForm() {
        setAttributes();
        return requestPath + "roleForm";
    }

    /**
     * 角色绑定目录
     */
    @RequestMapping("goRoleCatalog")
    public String goRoleCatalog() {
        setAttributes();
        String roleId = String.valueOf(this.getRequestParameter("roleId"));
        if (StringUtils.isNotBlank(roleId)) {
            SyCatalogRole syCatalogRole = syCatalogRoleService.loadByRoleId(Integer.parseInt(roleId));
            if (null != syCatalogRole) {
                request.setAttribute("nodeIds", syCatalogRole.getNodeIds());
                request.setAttribute("id", syCatalogRole.getId());
            }
        }
        return requestPath + "roleCatalogForm";
    }
    /// ***********************define end*************************

    /**
     * 介绍页面
     */
    @RequestMapping("goIntroduce")
    public String goIntroduce() {
        setAttributes();
        return requestPath + "introduce";
    }

    /**
     * 主页面
     */
    @RequestMapping("goMain")
    public String goMain() {
        setAttributes();
        return requestPath + "syCatalogRoleList";
    }

    /**
     * 新增页面
     */
    @RequestMapping("goAdd")
    public String goAdd() {
        setAttributes();
        request.setAttribute("operate", EOperation.Add.value());
        return requestPath + "syCatalogRoleForm";
    }

    /**
     * 查看页面
     */
    @RequestMapping("goView")
    public String goView() {
        setAttributes();
        request.setAttribute("operate", EOperation.View.value());
        return requestPath + "syCatalogRoleForm";
    }

    /**
     * 编辑页面
     */
    @RequestMapping("goEdit")
    public String goEdit() {
        setAttributes();
        request.setAttribute("operate", EOperation.Update.value());
        return requestPath + "syCatalogRoleForm";
    }

    /**
     * 历史列表页面
     *
     * @return
     */
    @RequestMapping("goViewHistory")
    public String goViewHistory() {
        setAttributes();
        return requestPath + "syCatalogRoleHsList";
    }

    /**
     * 历史页面
     *
     * @return
     */
    @RequestMapping("goViewHistoryForm")
    public String goViewHistoryForm() {
        setAttributes();
        request.setAttribute("operate", EOperation.History.value());
        return requestPath + "syCatalogRoleForm";
    }

    /**
     * 保存/更新
     *
     * @throws RequestException
     */
    @RequestMapping("doSave")
    public void doSave() throws RequestException {
        try {
            SyUser pmUser = getSessionUser();
            String id = getRequestParameter("id");
            if (StringUtils.isBlank(id) || id.equals("0")) {
                Map<String, String[]> map = getRequestParameterMap();
                SyCatalogRole entity = new SyCatalogRole();
                RReflectUtils.getObjectListForMap(entity, map);
                entity.setCreateUserId(pmUser.getId());
                save(entity);
                return;
            }
            update(id, pmUser);
        } catch (Exception e) {
            printWriterJson(new RResult(RResult.MSG_FAIL));
        }
    }

    /**
     * 保存逻辑
     *
     * @throws RequestException
     */
    private void save(SyCatalogRole entity) throws RequestException {
        Integer id = syCatalogRoleService.save(entity);
        if (id > 0) {
            printWriterJson(new RResult(RResult.MSG_SUCCESS));
            return;
        }
        log.info("save   save fail");
        printWriterJson(new RResult(RResult.MSG_FAIL));
    }

    /**
     * 修改逻辑
     *
     * @throws RequestException
     */
    private void update(String id, SyUser pmUser) throws RequestException {
        SyCatalogRole oldEntity = syCatalogRoleService.load(Integer.parseInt(id));
        if (null == oldEntity) {
            log.info("update fail record not exist, param[id={0}]" + id);
            printWriterJson(new RResult(RResult.MSG_FAIL, RResult.paramNull));
            return;
        }
        SyCatalogRole newEntity = new SyCatalogRole();
        RReflectUtils.getObjectForObject(newEntity, oldEntity);
        Map<String, String[]> map = getRequestParameterMap();
        RReflectUtils.getObjectListForMap(newEntity, map);

        RResult info = syCatalogRoleService.update(oldEntity, newEntity);
        printWriterJson(info);
    }

    /**
     * 删除
     */
    @RequestMapping("doDelete")
    public void doDelete() {
        try {
            String ids = getRequestParameter("ids");
            if (StringUtils.isBlank(ids)) {
                printWriterJson(new RResult(RResult.MSG_FAIL, RResult.paramNull));
                return;
            }
            String[] values = StringUtils.split(ids, ",");
            boolean bool = false;
            if (values.length > 1) {
                bool = syCatalogRoleService.deleteByIds(ids);
            } else {
                bool = syCatalogRoleService.delete(Integer.parseInt(ids));
            }
            if (bool) {
                printWriterJson(new RResult(RResult.MSG_SUCCESS));
            } else {
                printWriterJson(new RResult(RResult.MSG_FAIL));
            }
        } catch (Exception e) {
            log.error("doDelete exception", e);
        }
    }

    /**
     * 根据ID加载记录
     *
     * @throws RequestException
     */
    @RequestMapping("load")
    public void load() throws RequestException {
        String id = getRequestParameter("id");
        log.info("load id= " + id);
        if (StringUtils.isBlank(id)) {
            printWriterJson(new RResult(RResult.MSG_FAIL, RResult.paramNull));
            log.info("load  is empty id");
            return;
        }
        SyCatalogRole entity = syCatalogRoleService.load(Integer.parseInt(id));
        //      result = RJson.getJsonStr(entity);
        getPrintWriter().write(JsonUtil.toJson(entity));
    }

    /**
     * 列表查询
     */
    @RequestMapping("findPageList")
    public void findPageList() {
        try {
            Map<String, String[]> map = getRequestParameterMap();
            SyCatalogRole entity = new SyCatalogRole();
            RReflectUtils.getObjectListForMap(entity, map);
            PageControlInfo pageInfo = syCatalogRoleService.findPageList(entity, getPage(), getEnd(), loadOsearch());
            printWriterJson(pageInfo);
        } catch (Exception e) {
            log.error("findPageList Exception", e);
        }
    }
    /// ***********************method begin***********************

    /// ***********************method end*************************

}

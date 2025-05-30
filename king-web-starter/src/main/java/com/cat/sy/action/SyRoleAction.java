package com.cat.sy.action;

/// ***********************import begin***********************

import com.cat.bean.EOperation;
import com.cat.bean.PageControlInfo;
import com.cat.bean.RResult;
import com.cat.sy.action.base.WebBaseAction;
import com.cat.sy.entity.SyRole;
import com.cat.sy.entity.SyUser;
import com.cat.sy.service.SyRoleService;
import com.common.exception.RequestException;
import com.common.reflect.RReflectUtils;
import com.common.util.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/// ***********************import end*************************
@Slf4j
@RequestMapping("sy/role")
@Controller
@Scope("prototype")
public class SyRoleAction extends WebBaseAction {

    @Autowired
    private SyRoleService syRoleService;
    /**
     * 请求访问路径
     */
    private static final String requestPath = "sy/role/";

    // / ***********************define begin***********************
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
     * 主页面
     */
    @RequestMapping("goMain")
    public String goMain() {
        setAttributes();
        return requestPath + "syRoleList";
    }

    /**
     * 新增页面
     */
    @RequestMapping("goAdd")
    public String goAdd() {
        setAttributes();
        request.setAttribute("operate", EOperation.Add.value());
        return requestPath + "syRoleForm";
    }

    /**
     * 查看页面
     */
    @RequestMapping("goView")
    public String goView() {
        setAttributes();
        request.setAttribute("operate", EOperation.View.value());
        return requestPath + "syRoleForm";
    }

    /**
     * 编辑页面
     */
    @RequestMapping("goEdit")
    public String goEdit() {
        setAttributes();
        request.setAttribute("operate", EOperation.Update.value());
        return requestPath + "syRoleForm";
    }

    /**
     * 历史列表页面
     *
     * @return
     */
    @RequestMapping("goViewHistory")
    public String goViewHistory() {
        setAttributes();
        return requestPath + "syRoleHsList";
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
        return requestPath + "syRoleForm";
    }

    /**
     * 保存/更新
     */
    @RequestMapping("doSave")
    public void doSave() {
        try {
            SyUser syUser = getSessionUser();
            String id = getRequestParameter("id");
            if (StringUtils.isBlank(id) || id.equals("0")) {
                Map<String, String[]> map = getRequestParameterMap();
                SyRole entity = new SyRole();
                RReflectUtils.getObjectListForMap(entity, map);
                save(entity);
                return;
            }
            update(id, syUser);
        } catch (Exception e) {
            log.error("doSave exception", e);
        }
    }

    /**
     * 保存逻辑
     *
     * @throws RequestException
     */
    private void save(SyRole entity) throws RequestException {
        if (null != isExitsCode(entity.getCode())) {
            printWriterJson(new RResult(RResult.MSG_FAIL, "角色代码重复"));
            return;
        }
        Integer id = syRoleService.save(entity);
        if (id > 0) {
            printWriterJson(new RResult(RResult.MSG_SUCCESS));
            return;
        }
        log.info(" save fail");
        printWriterJson(new RResult(RResult.MSG_FAIL));
    }

    private SyRole isExitsCode(String code) {
        SyRole role = new SyRole();
        role.setCode(code);
        return syRoleService.load(role);
    }

    /**
     * 修改逻辑
     *
     * @throws RequestException
     */
    private void update(String id, SyUser syUser) throws RequestException {
        SyRole oldEntity = syRoleService.load(Integer.parseInt(id));
        if (null == oldEntity) {
            log.info("update update fail record not exist, param[id=" + id + "]");
            printWriterJson(new RResult(RResult.MSG_FAIL, RResult.recordNotExist, id));
            return;
        }
        SyRole newEntity = new SyRole();
        RReflectUtils.getObjectForObject(newEntity, oldEntity);
        Map<String, String[]> map = getRequestParameterMap();
        RReflectUtils.getObjectListForMap(newEntity, map);

        SyRole syRole = isExitsCode(newEntity.getCode());
        if (null != syRole && !id.equalsIgnoreCase(String.valueOf(syRole.getId()))) {
            // 根据角色代码获取到相同的，并且ID 不相同
            printWriterJson(new RResult(RResult.MSG_FAIL, "角色代码重复"));
            return;
        }

        RResult info = syRoleService.update(oldEntity, newEntity);
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
                printWriterJson(new RResult(RResult.MSG_FAIL, RResult.recordNotExist));
                log.info("doDelete  is empty ids");
                return;
            }
            String[] values = StringUtils.split(ids, ",");
            boolean bool = false;
            if (values.length > 1) {
                bool = syRoleService.deleteByIds(ids);
            } else {
                bool = syRoleService.delete(Integer.parseInt(ids));
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
        log.info("load id=" + id);
        if (StringUtils.isBlank(id)) {
            printWriterJson(new RResult(RResult.MSG_FAIL, RResult.recordNotExist));
            log.info("load id is empty ");
            return;
        }
        SyRole entity = syRoleService.load(Integer.parseInt(id));
        //		result = RJson.getJsonStr(entity);
        getPrintWriter().write(JsonUtil.toJson(entity));
    }

    /**
     * 列表查询
     */
    @RequestMapping("findList")
    public void findList() {
        try {
            Map<String, String[]> map = getRequestParameterMap();
            SyRole entity = new SyRole();
            RReflectUtils.getObjectListForMap(entity, map);
            Map<String, Object> search = loadOsearch();
            List<SyRole> list = syRoleService.findList(entity, search);
            printWriterJson(list);
        } catch (Exception e) {
            log.error("findList Exception", e);
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping("findPageList")
    public void findPageList() {
        try {
            Map<String, String[]> map = getRequestParameterMap();
            SyRole entity = new SyRole();
            RReflectUtils.getObjectListForMap(entity, map);
            Map<String, Object> search = loadOsearch();
            if (!ObjectUtils.isEmpty(search.get("text"))) {
                entity.setLabel((String)search.get("text"));
            }
            PageControlInfo pageInfo = syRoleService.findPageList(entity, getPage(), getEnd(), null);
            printWriterJson(pageInfo);
        } catch (Exception e) {
            log.error("findPageList Exception", e);
        }
    }

}

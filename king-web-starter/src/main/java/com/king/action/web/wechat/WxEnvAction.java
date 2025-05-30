package com.king.action.web.wechat;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cat.bean.EOperation;
import com.cat.bean.PageControlInfo;
import com.cat.bean.RResult;
import com.cat.sy.action.base.WebBaseAction;
import com.common.exception.RequestException;
import com.common.reflect.RReflectUtils;
import com.common.util.json.JsonUtil;
import com.king.wechat.entity.WxEnv;
import com.king.wechat.service.WxEnvService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author daiming
 * @version 1.0
 * @date 2019-11-24
 * @Description:
 */
@Slf4j
@RequestMapping("wx/env")
@Controller
@Scope("prototype")
public class WxEnvAction extends WebBaseAction {

    @Autowired
    private WxEnvService wxEnvService;

    /**
     * 请求访问路径
     */
    private static final String requestPath = "wx/env/";

    /**
     * 主页面
     */
    @RequestMapping("goMain")
    public String goMain() {
        setAttributes();
        return requestPath + "wxEnvList";
    }

    /**
     * 新增页面
     */
    @RequestMapping("goAdd")
    public String goAdd() {
        setAttributes();
        request.setAttribute("operate", EOperation.Add.value());
        return requestPath + "wxEnvForm";
    }

    /**
     * 查看页面
     */
    @RequestMapping("goView")
    public String goView() {
        setAttributes();
        request.setAttribute("operate", EOperation.View.value());
        return requestPath + "wxEnvForm";
    }

    /**
     * 编辑页面
     */
    @RequestMapping("goEdit")
    public String goEdit() {
        setAttributes();
        request.setAttribute("operate", EOperation.Update.value());
        return requestPath + "wxEnvForm";
    }

    /**
     * 保存/更新
     */
    @RequestMapping("doSave")
    public void doSave() {
        try {
            String id = getRequestParameter("id");
            if (StringUtils.isBlank(id) || id.equals("0")) {
                Map<String, String[]> map = getRequestParameterMap();
                WxEnv entity = new WxEnv();
                RReflectUtils.getObjectListForMap(entity, map);
                save(entity);
                return;
            }
            update(id);
        } catch (Exception e) {
            log.error("doSave exception", e);
        }
    }

    /**
     * 保存逻辑
     *
     * @throws RequestException
     */
    private void save(WxEnv entity) throws RequestException {
        if (null != isExitsCode(entity.getEnvKey())) {
            printWriterJson(new RResult(RResult.MSG_FAIL, "key已存在"));
            return;
        }
        boolean id = wxEnvService.insert(entity);
        if (id) {
            printWriterJson(new RResult(RResult.MSG_SUCCESS));
            return;
        }
        log.info(" save fail");
        printWriterJson(new RResult(RResult.MSG_FAIL));
    }

    private WxEnv isExitsCode(String code) {
        WxEnv role = new WxEnv();
        role.setEnvKey(code);
        return wxEnvService.selectOne(new EntityWrapper<>(role));
    }

    /**
     * 修改逻辑
     *
     * @throws RequestException
     */
    private void update(String id) throws RequestException {
        WxEnv oldEntity = wxEnvService.selectById(id);
        if (null == oldEntity) {
            log.info("update update fail record not exist, param[id=" + id + "]");
            printWriterJson(new RResult(RResult.MSG_FAIL, RResult.recordNotExist, id));
            return;
        }
        WxEnv newEntity = new WxEnv();
        RReflectUtils.getObjectForObject(newEntity, oldEntity);
        Map<String, String[]> map = getRequestParameterMap();
        RReflectUtils.getObjectListForMap(newEntity, map);

        WxEnv WxEnv = isExitsCode(newEntity.getEnvKey());
        if (null != WxEnv && !id.equalsIgnoreCase(String.valueOf(WxEnv.getId()))) {
            // 根据角色代码获取到相同的，并且ID 不相同
            printWriterJson(new RResult(RResult.MSG_FAIL, "key重复"));
            return;
        }
        boolean update = wxEnvService.updateById(newEntity);
        if (update) {
            printWriterJson(new RResult(RResult.MSG_SUCCESS));
            return;
        }
        log.info(" save fail");
        printWriterJson(new RResult(RResult.MSG_FAIL));
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
                bool = wxEnvService.deleteBatchIds(Arrays.asList(ids.split(",")));
            } else {
                bool = wxEnvService.deleteById(ids);
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
        WxEnv entity = wxEnvService.selectById(id);
        getPrintWriter().write(JsonUtil.toJson(entity));
    }

    /**
     * 列表查询
     */
    @RequestMapping("findList")
    public void findList() {
        try {
            Map<String, String[]> map = getRequestParameterMap();
            WxEnv entity = new WxEnv();
            RReflectUtils.getObjectListForMap(entity, map);
            Map<String, Object> search = loadOsearch();
            List<WxEnv> list = wxEnvService.selectList(new EntityWrapper<>(entity));
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
            WxEnv entity = new WxEnv();
            RReflectUtils.getObjectListForMap(entity, map);
            Map<String, Object> search = loadOsearch();
            Page<WxEnv> page = new Page<>(getPage(), getEnd());
            Page<WxEnv> syCatalogRolePage = wxEnvService.selectPage(page, new EntityWrapper<>(entity).orderBy(WxEnv.CREATE_TIME, false));
            PageControlInfo pageInfo = new PageControlInfo();
            pageInfo.setTotalNum((int) page.getTotal());
            pageInfo.setSearchData(syCatalogRolePage.getRecords());
            pageInfo.setStart(page.getCurrent());
            pageInfo.setMax(page.getSize());
            printWriterJson(pageInfo);
        } catch (Exception e) {
            log.error("findPageList Exception", e);
        }
    }
}

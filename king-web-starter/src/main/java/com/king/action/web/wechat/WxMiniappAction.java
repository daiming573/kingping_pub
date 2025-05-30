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
import com.king.wechat.entity.WxMiniappList;
import com.king.wechat.service.WxMiniappListService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author daiming
 * @version 1.0
 * @date 2019-11-30
 * @Description:
 */
@Slf4j
@RequestMapping("wx/miniapp")
@Controller
@Scope("prototype")
public class WxMiniappAction extends WebBaseAction {

    @Autowired
    private WxMiniappListService wxMiniappListService;

    /**
     * 请求访问路径
     */
    private static final String requestPath = "wx/miniapp/";


    /**
     * 主页面
     */
    @RequestMapping("goMain")
    public String goMain() {
        setAttributes();
        return requestPath + "wxMiniappList";
    }

    /**
     * 新增页面
     */
    @RequestMapping("goAdd")
    public String goAdd() {
        setAttributes();
        request.setAttribute("operate", EOperation.Add.value());
        return requestPath + "wxMiniappForm";
    }

    /**
     * 查看页面
     */
    @RequestMapping("goView")
    public String goView() {
        setAttributes();
        request.setAttribute("operate", EOperation.View.value());
        return requestPath + "wxMiniappForm";
    }

    /**
     * 编辑页面
     */
    @RequestMapping("goEdit")
    public String goEdit() {
        setAttributes();
        request.setAttribute("operate", EOperation.Update.value());
        return requestPath + "wxMiniappForm";
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
                WxMiniappList entity = new WxMiniappList();
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
    private void save(WxMiniappList entity) throws RequestException {
        if (null != isExitsCode(entity.getMiniappCode())) {
            printWriterJson(new RResult(RResult.MSG_FAIL, "编号已存在"));
            return;
        }
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        boolean id = wxMiniappListService.insert(entity);
        if (id) {
            printWriterJson(new RResult(RResult.MSG_SUCCESS));
            return;
        }
        log.info(" save fail");
        printWriterJson(new RResult(RResult.MSG_FAIL));
    }

    private WxMiniappList isExitsCode(String code) {
        WxMiniappList entity = new WxMiniappList();
        entity.setMiniappCode(code);
        return wxMiniappListService.selectOne(new EntityWrapper<>(entity));
    }

    /**
     * 修改逻辑
     *
     * @throws RequestException
     */
    private void update(String id) throws RequestException {
        WxMiniappList oldEntity = wxMiniappListService.selectById(id);
        if (null == oldEntity) {
            log.info("update update fail record not exist, param[id=" + id + "]");
            printWriterJson(new RResult(RResult.MSG_FAIL, RResult.recordNotExist, id));
            return;
        }
        WxMiniappList newEntity = new WxMiniappList();
        RReflectUtils.getObjectForObject(newEntity, oldEntity);
        Map<String, String[]> map = getRequestParameterMap();
        RReflectUtils.getObjectListForMap(newEntity, map);

        WxMiniappList wxMiniapp = isExitsCode(newEntity.getMiniappCode());
        if (null != wxMiniapp && !id.equalsIgnoreCase(String.valueOf(wxMiniapp.getId()))) {
            // 根据角色代码获取到相同的，并且ID 不相同
            printWriterJson(new RResult(RResult.MSG_FAIL, "编号重复"));
            return;
        }
        newEntity.setUpdateTime(new Date());
        boolean update = wxMiniappListService.updateById(newEntity);
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
                bool = wxMiniappListService.deleteBatchIds(Arrays.asList(ids.split(",")));
            } else {
                bool = wxMiniappListService.deleteById(Integer.parseInt(ids));
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
        WxMiniappList entity = wxMiniappListService.selectById(id);
        getPrintWriter().write(JsonUtil.toJson(entity));
    }

    /**
     * 列表查询
     */
    @RequestMapping("findList")
    public void findList() {
        try {
            Map<String, String[]> map = getRequestParameterMap();
            WxMiniappList entity = new WxMiniappList();
            RReflectUtils.getObjectListForMap(entity, map);
            Map<String, Object> search = loadOsearch();
            List<WxMiniappList> list = wxMiniappListService.selectList(new EntityWrapper<>(entity));
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
            WxMiniappList entity = new WxMiniappList();
            RReflectUtils.getObjectListForMap(entity, map);
            EntityWrapper<WxMiniappList> ew = new EntityWrapper<>(entity);
            if (ObjectUtils.isEmpty(entity.getMiniappName())){
                entity.setMiniappName(null);
                ew.like(WxMiniappList.MINIAPP_NAME, entity.getMiniappName());
            }
            Map<String, Object> search = loadOsearch();
            Page<WxMiniappList> page = new Page<>(getPage(), getEnd());
            Page<WxMiniappList> syCatalogRolePage = wxMiniappListService.selectPage(page, ew.orderBy(WxMiniappList.CREATE_TIME, false));
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

package com.king.action.web.pig.account;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cat.bean.EOperation;
import com.cat.bean.PageControlInfo;
import com.cat.bean.RResult;
import com.cat.sy.action.base.WebBaseAction;
import com.common.exception.RequestException;
import com.common.reflect.RReflectUtils;
import com.common.util.json.JsonUtil;
import com.king.pig.entity.KingChildAccountRecord;
import com.king.pig.service.KingChildAccountRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("king/record")
@Controller
public class KingAccountRecordAction extends WebBaseAction {

    @Autowired
    private KingChildAccountRecordService kingChildAccountRecordService;

    /**
     * 请求访问路径
     */
    private static final String requestPath = "king/record/";

    /**
     * 主页面
     */
    @RequestMapping("goMain")
    public String goMain() {
        setAttributes();
        return requestPath + "kingAccountList";
    }

    /**
     * 新增页面
     */
    @RequestMapping("goAdd")
    public String goAdd() {
        setAttributes();
        request.setAttribute("operate", EOperation.Add.value());
        return requestPath + "kingAccountForm";
    }

    /**
     * 查看页面
     */
    @RequestMapping("goView")
    public String goView() {
        setAttributes();
        request.setAttribute("operate", EOperation.View.value());
        return requestPath + "kingAccountForm";
    }

    /**
     * 编辑页面
     */
    @RequestMapping("goEdit")
    public String goEdit() {
        setAttributes();
        request.setAttribute("operate", EOperation.Update.value());
        return requestPath + "kingAccountForm";
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
                KingChildAccountRecord entity = new KingChildAccountRecord();
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
    private void save(KingChildAccountRecord entity) throws RequestException {
        boolean id = kingChildAccountRecordService.insert(entity);
        if (id) {
            printWriterJson(new RResult(RResult.MSG_SUCCESS));
            return;
        }
        log.info(" save fail");
        printWriterJson(new RResult(RResult.MSG_FAIL));
    }

    /**
     * 修改逻辑
     *
     * @throws RequestException
     */
    private void update(String id) throws RequestException {
        KingChildAccountRecord oldEntity = kingChildAccountRecordService.selectById(id);
        if (null == oldEntity) {
            log.info("update update fail record not exist, param[id=" + id + "]");
            printWriterJson(new RResult(RResult.MSG_FAIL, RResult.recordNotExist, id));
            return;
        }
        KingChildAccountRecord newEntity = new KingChildAccountRecord();
        RReflectUtils.getObjectForObject(newEntity, oldEntity);
        Map<String, String[]> map = getRequestParameterMap();
        RReflectUtils.getObjectListForMap(newEntity, map);
        boolean update = kingChildAccountRecordService.updateById(newEntity);
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
                bool = kingChildAccountRecordService.deleteBatchIds(Arrays.asList(ids.split(",")));
            } else {
                bool = kingChildAccountRecordService.deleteById(Integer.parseInt(ids));
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
        KingChildAccountRecord entity = kingChildAccountRecordService.selectById(id);
        getPrintWriter().write(JsonUtil.toJson(entity));
    }

    /**
     * 列表查询
     */
    @RequestMapping("findList")
    public void findList() {
        try {
            Map<String, String[]> map = getRequestParameterMap();
            KingChildAccountRecord entity = new KingChildAccountRecord();
            RReflectUtils.getObjectListForMap(entity, map);
            Map<String, Object> search = loadOsearch();
            List<KingChildAccountRecord> list = kingChildAccountRecordService.selectList(new EntityWrapper<>(entity));
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
            KingChildAccountRecord entity = new KingChildAccountRecord();
            RReflectUtils.getObjectListForMap(entity, map);
            Map<String, Object> search = loadOsearch();
            Page<KingChildAccountRecord> page = new Page<>(getPage(), getEnd());
            Page<KingChildAccountRecord> syCatalogRolePage = kingChildAccountRecordService.selectPage(page, new EntityWrapper<>(entity).orderBy(KingChildAccountRecord.CREATE_TIME, false));
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

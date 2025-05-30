package com.king.action.web.pig.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cat.bean.EOperation;
import com.cat.bean.PageControlInfo;
import com.cat.bean.RResult;
import com.cat.sy.action.base.WebBaseAction;
import com.common.exception.RequestException;
import com.common.reflect.RReflectUtils;
import com.common.util.json.JsonUtil;
import com.king.pig.entity.KingFamilyUser;
import com.king.pig.service.KingFamilyUserService;
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
@RequestMapping("king/user")
@Controller
public class KingUserAction extends WebBaseAction {

    @Autowired
    private KingFamilyUserService kingUserService;

    /**
     * 请求访问路径
     */
    private static final String requestPath = "king/user/";

    /**
     * 主页面
     */
    @RequestMapping("goMain")
    public String goMain() {
        setAttributes();
        return requestPath + "kingUserList";
    }

    /**
     * 新增页面
     */
    @RequestMapping("goAdd")
    public String goAdd() {
        setAttributes();
        request.setAttribute("operate", EOperation.Add.value());
        return requestPath + "kingUserForm";
    }

    /**
     * 查看页面
     */
    @RequestMapping("goView")
    public String goView() {
        setAttributes();
        request.setAttribute("operate", EOperation.View.value());
        return requestPath + "kingUserForm";
    }

    /**
     * 编辑页面
     */
    @RequestMapping("goEdit")
    public String goEdit() {
        setAttributes();
        request.setAttribute("operate", EOperation.Update.value());
        return requestPath + "kingUserForm";
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
                KingFamilyUser entity = new KingFamilyUser();
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
    private void save(KingFamilyUser entity) throws RequestException {
        boolean id = kingUserService.insert(entity);
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
        KingFamilyUser oldEntity = kingUserService.selectById(id);
        if (null == oldEntity) {
            log.info("update update fail record not exist, param[id=" + id + "]");
            printWriterJson(new RResult(RResult.MSG_FAIL, RResult.recordNotExist, id));
            return;
        }
        KingFamilyUser newEntity = new KingFamilyUser();
        RReflectUtils.getObjectForObject(newEntity, oldEntity);
        Map<String, String[]> map = getRequestParameterMap();
        RReflectUtils.getObjectListForMap(newEntity, map);

        boolean update = kingUserService.updateById(newEntity);
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
                bool = kingUserService.deleteBatchIds(Arrays.asList(ids.split(",")));
            } else {
                bool = kingUserService.deleteById(Integer.parseInt(ids));
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
        KingFamilyUser entity = kingUserService.selectById(id);
        getPrintWriter().write(JsonUtil.toJson(entity));
    }

    /**
     * 列表查询
     */
    @RequestMapping("findList")
    public void findList() {
        try {
            Map<String, String[]> map = getRequestParameterMap();
            KingFamilyUser entity = new KingFamilyUser();
            RReflectUtils.getObjectListForMap(entity, map);
            Map<String, Object> search = loadOsearch();
            List<KingFamilyUser> list = kingUserService.selectList(new EntityWrapper<>(entity));
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
            KingFamilyUser entity = new KingFamilyUser();
            RReflectUtils.getObjectListForMap(entity, map);
            Map<String, Object> search = loadOsearch();
            Page<KingFamilyUser> page = new Page<>(getPage(), getEnd());
            Page<KingFamilyUser> syCatalogRolePage = kingUserService.selectPage(page, new EntityWrapper<>(entity).orderBy(KingFamilyUser.CREATE_TIME, false));
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

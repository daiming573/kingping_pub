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
import com.king.pig.entity.KingFamilyChild;
import com.king.pig.entity.KingFamilyChildMap;
import com.king.pig.service.KingFamilyChildMapService;
import com.king.pig.service.KingFamilyChildService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author daiming
 * @version 1.0
 * @date 2019-11-24
 * @Description:
 */
@Slf4j
@RequestMapping("king/child")
@Controller
public class KingUserChildAction extends WebBaseAction {

    @Autowired
    private KingFamilyChildService kingFamilyChildService;

    @Autowired
    private KingFamilyChildMapService kingFamilyChildMapService;

    /**
     * 请求访问路径
     */
    private static final String requestPath = "king/child/";

    /**
     * 主页面
     */
    @RequestMapping("goMain")
    public String goMain() {
        setAttributes();
        request.removeAttribute("id");
        return requestPath + "kingUserChildList";
    }

    /**
     * 新增页面
     */
    @RequestMapping("goAdd")
    public String goAdd() {
        setAttributes();
        request.setAttribute("operate", EOperation.Add.value());
        return requestPath + "kingUserChildForm";
    }

    /**
     * 查看页面
     */
    @RequestMapping("goView")
    public String goView() {
        setAttributes();
        request.setAttribute("operate", EOperation.View.value());
        return requestPath + "kingUserChildForm";
    }

    /**
     * 编辑页面
     */
    @RequestMapping("goEdit")
    public String goEdit() {
        setAttributes();
        request.setAttribute("operate", EOperation.Update.value());
        return requestPath + "kingUserChildForm";
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
                KingFamilyChild entity = new KingFamilyChild();
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
    private void save(KingFamilyChild entity) throws RequestException {
        boolean id = kingFamilyChildService.insert(entity);
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
        KingFamilyChild oldEntity = kingFamilyChildService.selectById(id);
        if (null == oldEntity) {
            log.info("update update fail record not exist, param[id=" + id + "]");
            printWriterJson(new RResult(RResult.MSG_FAIL, RResult.recordNotExist, id));
            return;
        }
        KingFamilyChild newEntity = new KingFamilyChild();
        RReflectUtils.getObjectForObject(newEntity, oldEntity);
        Map<String, String[]> map = getRequestParameterMap();
        RReflectUtils.getObjectListForMap(newEntity, map);

        boolean update = kingFamilyChildService.updateById(newEntity);
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
                bool = kingFamilyChildService.deleteBatchIds(Arrays.asList(ids.split(",")));
            } else {
                bool = kingFamilyChildService.deleteById(Integer.parseInt(ids));
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
        KingFamilyChild entity = kingFamilyChildService.selectById(id);
        getPrintWriter().write(JsonUtil.toJson(entity));
    }

    /**
     * 列表查询
     */
    @RequestMapping("findList")
    public void findList() {
        try {
            Map<String, String[]> map = getRequestParameterMap();
            KingFamilyChild entity = new KingFamilyChild();
            RReflectUtils.getObjectListForMap(entity, map);
            Map<String, Object> search = loadOsearch();
            List<KingFamilyChild> list = kingFamilyChildService.selectList(new EntityWrapper<>(entity));
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
            KingFamilyChild entity = new KingFamilyChild();
            RReflectUtils.getObjectListForMap(entity, map);
            Map<String, Object> search = loadOsearch();
            EntityWrapper<KingFamilyChild> ew = new EntityWrapper<>(entity);
            if (null != entity.getFamilyId()){
                List<KingFamilyChildMap> familyChildList = kingFamilyChildMapService.selectList(new EntityWrapper<KingFamilyChildMap>().eq(KingFamilyChildMap.FAMILY_ID, entity.getFamilyId()));
                if (CollectionUtils.isEmpty(familyChildList)){
                    printWriterJson(new PageControlInfo());
                    return;
                }
                ew.in(KingFamilyChild.ID, familyChildList.stream().map(KingFamilyChildMap::getChildId).collect(Collectors.toList()));
            }
            ew.orderBy(KingFamilyChild.CREATE_TIME, false);
            Page<KingFamilyChild> page = kingFamilyChildService.selectPage(new Page<>(getPage(), getEnd()), ew);
            PageControlInfo pageInfo = new PageControlInfo();
            pageInfo.setTotalNum((int) page.getTotal());
            pageInfo.setSearchData(page.getRecords());
            pageInfo.setStart(page.getCurrent());
            pageInfo.setMax(page.getSize());
            printWriterJson(pageInfo);
        } catch (Exception e) {
            log.error("findPageList Exception", e);
        }
    }
}

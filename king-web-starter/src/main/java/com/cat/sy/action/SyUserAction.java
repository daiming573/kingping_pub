package com.cat.sy.action;

/// ***********************import begin***********************

import com.cat.bean.EOperation;
import com.cat.bean.Message;
import com.cat.bean.PageControlInfo;
import com.cat.bean.RResult;
import com.cat.sy.action.base.WebBaseAction;
import com.cat.sy.entity.SyUser;
import com.cat.sy.service.SyUserService;
import com.common.anno.Anno;
import com.common.anno.LoginNeed;
import com.common.enums.EMsg;
import com.common.exception.RequestException;
import com.common.reflect.RReflectUtils;
import com.common.util.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/// ***********************import end*************************
@Slf4j
@RequestMapping("sy/user")
@Controller
@Scope("prototype")
public class SyUserAction extends WebBaseAction {

    @Autowired
    private SyUserService syUserService;

    @Autowired
    private Environment environment;

    /**
     * 请求访问路径
     */
    private static final String requestPath = "sy/user/";

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
        return requestPath + "syUserList";
    }

    /**
     * 新增页面
     */
    @RequestMapping("goAdd")
    public String goAdd() {
        setAttributes();
        request.setAttribute("operate", EOperation.Add.value());
        return requestPath + "syUserForm";
    }

    /**
     * 查看页面
     */
    @RequestMapping("goView")
    public String goView() {
        setAttributes();
        request.setAttribute("operate", EOperation.View.value());
        return requestPath + "syUserForm";
    }

    /**
     * 编辑页面
     */
    @RequestMapping("goEdit")
    public String goEdit() {
        setAttributes();
        request.setAttribute("operate", EOperation.Update.value());
        return requestPath + "syUserForm";
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
                SyUser entity = new SyUser();
                RReflectUtils.getObjectListForMap(entity, map);
                entity.setCreateUserId(syUser.getId());
                save(entity);
                return;
            }
            update(id, syUser);
        } catch (Exception e) {
            log.error("doSave Exception", e);
        }
    }

    /**
     * 保存逻辑
     *
     * @throws RequestException
     */
    private void save(SyUser entity) throws RequestException {
        Integer id = syUserService.save(entity);
        if (id > 0) {
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
    private void update(String id, SyUser syUser) throws RequestException {
        SyUser oldEntity = syUserService.load(Integer.parseInt(id));
        if (null == oldEntity) {
            log.info("update update fail record not exist, param[id=" + id + "]");
            printWriterJson(new RResult(RResult.MSG_FAIL, RResult.recordNotExist, id));
            return;
        }
        SyUser newEntity = new SyUser();
        RReflectUtils.getObjectForObject(newEntity, oldEntity);
        Map<String, String[]> map = getRequestParameterMap();
        RReflectUtils.getObjectListForMap(newEntity, map);

        if (!oldEntity.getPassword().equals(newEntity.getPassword())) {
            newEntity.setPassword(MD5Encoder.encode(newEntity.getPassword().getBytes()));// 修改了密码，进行加密处理
        }

        boolean info = syUserService.update(newEntity);
        if (info) {
            printWriterJson(new RResult(RResult.MSG_SUCCESS));
        } else {
            printWriterJson(new RResult(RResult.MSG_FAIL));
        }
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
                bool = syUserService.deleteByIds(ids);
            } else {
                bool = syUserService.delete(Integer.parseInt(ids));
            }
            if (bool) {
                printWriterJson(new RResult(RResult.MSG_SUCCESS));
            } else {
                printWriterJson(new RResult(RResult.MSG_FAIL));
            }
        } catch (Exception e) {
            log.error("doDelete Exception", e);
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
        SyUser entity = syUserService.load(Integer.parseInt(id));
        // result = RJson.getJsonStr(entity);
        getPrintWriter().write(JsonUtil.toJson(entity));
    }

    @RequestMapping("findList")
    public void findList() {
        try {
            Map<String, String[]> map = getRequestParameterMap();
            SyUser entity = new SyUser();
            RReflectUtils.getObjectListForMap(entity, map);
            List<SyUser> pageInfo = syUserService.findList(entity, loadOsearch());
            printWriterJson(pageInfo);
        } catch (Exception e) {
            log.error("findPageList Exception", e);
        }
    }

    /**
     * 列表查询
     */
    @Anno("列表查询A")
    @RequestMapping("findPageList")
    public void findPageList() {
        try {
            Map<String, String[]> map = getRequestParameterMap();
            SyUser entity = new SyUser();
            RReflectUtils.getObjectListForMap(entity, map);
            PageControlInfo pageInfo = syUserService.findPageList(entity, getPage(), getEnd(), loadOsearch());
            printWriterJson(pageInfo);
        } catch (Exception e) {
            log.error("findPageList Exception", e);
        }
    }

    /**
     * 登录
     *
     * @return
     */
    @LoginNeed(false)
    @RequestMapping(value = "login")
    public String login() {
        String userName = getRequestParameter("userName");
        String password = getRequestParameter("password");
        String checkCode = getRequestParameter("checkCode");
        String randCode = (String) session.getAttribute("randCode");
        SyUser syUser = null;
        log.info("login in userName=" + userName + ",password=" + password);
        // 已经在系统页面内，不通过登录页面进入
        if (StringUtils.isAnyBlank(userName, password, checkCode, randCode)) {
            syUser = (SyUser) session.getAttribute("syUser");
            if (null == syUser) {
                return "sy/main/logout";
            }
            return "sy/main/main";// 进入主页面
        }
        // 通过登录页面进入系统，登录校验
        try {
            syUser = checkLogin(userName, password, checkCode);
        } catch (RequestException e) {
            session.setAttribute("message", e.getMessage());
            return "sy/main/logout";
        }
        if (null == syUser) {
            session.setAttribute("message", "error");
            return "sy/main/logout";
        }
        // 校验弱密码
        if (!checkPassword(password)) {
            session.setAttribute("message", Message.Update_Password.toString());
            return "sy/main/logout";
        }

        syUser.setIdNumber(null);
        syUser.setPassword(null);

        session.setAttribute("syUser", syUser);
        session.removeAttribute("randCode");
        session.removeAttribute("message");
        return "sy/main/main";

    }

    /**
     * 校验是弱密码
     *
     * @return
     */
    private boolean checkPassword(String password) {
        String regex = "^(?![^a-z]+$)(?![^A-Z]+$)(?![^0-9]+$).{8,16}$";
        if (!password.matches(regex)) {
            return false;
        }
        return true;
    }

    private SyUser getSyUser(String userName, String password) {
        SyUser syUser = new SyUser();
        syUser.setUserName(userName);
        syUser.setPassword(MD5Encoder.encode(password.getBytes()));
        return syUserService.load(syUser);
    }

    /**
     * 登录验证
     *
     * @param userName 用户名
     * @param password 密码
     * @param checkCode 验证码
     *
     * @return
     */
    public SyUser checkLogin(String userName, String password, String checkCode) {
        String randCode = (String) session.getAttribute("randCode");
        if (StringUtils.isBlank(randCode)) {
            throw new RequestException(EMsg.Fail, Message.LOGIN_RANDCODE.toString());
        }
        if (!checkCode.equals(randCode)) {
            throw new RequestException(EMsg.Fail, Message.LOGIN_RANDCODE.toString());
        }

        SyUser syUser = getSyUser(userName, password);
        if (null == syUser) {
            throw new RequestException(EMsg.Fail, Message.LOGIN_FAILURE.toString());
        }

        if (!SyUser.status_yes.equalsIgnoreCase(syUser.getStatus())) {
            throw new RequestException(EMsg.Fail, Message.User_status.toString());
        }
        return syUser;
    }

    /**
     * 登录错误
     *
     * @return
     */
    @LoginNeed(false)
    @RequestMapping("logerror")
    public String logerror() {
        session.removeAttribute("syUser");
        session.removeAttribute("randCode");
        return "sy/main/logout";
    }

    /**
     * 登出
     *
     * @return
     */
    @LoginNeed(false)
    @RequestMapping("logout")
    public String logout() {
        session.removeAttribute("syUser");
        session.removeAttribute("randCode");
        session.removeAttribute("message");
        return "sy/main/logout";
    }

    /**
     * 重置用户密码
     */
    @RequestMapping("resetPassword")
    public void resetPassword() {
        try {
            String id = getRequestParameter("id");
            if (StringUtils.isBlank(id)) {
                printWriterJson(new RResult(RResult.MSG_FAIL, RResult.recordNotExist));
                log.info("resetPassword  is empty id");
                return;
            }

            String p = environment.getProperty("resetPassword");//RSystemConfig.getValue("resetPassword");
            if (StringUtils.isBlank(p)) {
                p = "Abc123++";
            }

            boolean f = syUserService.resetPassword(Integer.parseInt(id), p);
            if (f) {
                printWriterJson(new RResult(RResult.MSG_SUCCESS, "重置后密码:" + p + ",请尽快修改密码"));
            } else {
                printWriterJson(new RResult(RResult.MSG_FAIL));
            }
        } catch (Exception e) {
            log.error("resetPassword Exception", e);
        }
    }

    /**
     * 修改用户密码
     */
    @RequestMapping("updatePassword")
    public void updatePassword() {
        try {
            SyUser sy = getSessionUser();
            if (null == sy) {
                return;
            }
            String password = getRequestParameter("password");
            String newPassword = getRequestParameter("newPassword");
            String new2Password = getRequestParameter("new2Password");

            if (StringUtils.isAnyBlank(password, newPassword, new2Password)) {
                printWriterJson(new RResult(RResult.MSG_FAIL, RResult.paramNull));
                log.info("resetPassword  is empty id");
                return;
            }

            if (!checkPassword(newPassword)) {
                printWriterJson(new RResult(RResult.MSG_FAIL, "新密码必须长度为8至16位，且必须包含大写字母，小写字母和数字"));
                return;
            }

            if (!newPassword.trim().equals(new2Password.trim())) {
                printWriterJson(new RResult(RResult.MSG_FAIL, "两次输入密码不一致"));
                return;
            }

            Integer f = syUserService.updatePassword(sy.getId(), password, newPassword);
            if (f > 0) {
                printWriterJson(new RResult(RResult.MSG_SUCCESS));
            } else {
                printWriterJson(new RResult(RResult.MSG_FAIL, "旧密码不正确"));
            }
        } catch (Exception e) {
            log.error("updatePassword Exception", e);
        }
    }

    /**
     * 修改用户密码
     */
    @RequestMapping("updatePasswordNoLogin")
    @LoginNeed(false)
    public void updatePasswordNoLogin() {
        try {

            String userName = getRequestParameter("userName");
            String password = getRequestParameter("oldPassword");
            String newPassword = getRequestParameter("newPassword");

            if (StringUtils.isAnyBlank(password, newPassword, userName)) {
                printWriterJson(new RResult(RResult.MSG_FAIL, RResult.paramNull));
                log.info("resetPassword  is empty id");
                return;
            }

            if (!checkPassword(newPassword)) {
                printWriterJson(new RResult(RResult.MSG_FAIL, "新密码必须长度为8至16位，且必须包含大写字母，小写字母和数字"));
                return;
            }

            SyUser syUser = getSyUser(userName, password);
            if (null == syUser) {
                printWriterJson(new RResult(RResult.MSG_FAIL, "用户名或密码不正确"));
                return;
            }

            Integer f = syUserService.updatePassword(syUser.getId(), password, newPassword);
            if (f > 0) {
                printWriterJson(new RResult(RResult.MSG_SUCCESS));
            } else {
                printWriterJson(new RResult(RResult.MSG_FAIL, "旧密码不正确"));
            }
        } catch (Exception e) {
            log.error("updatePasswordNoLogin Exception", e);
        }
    }


}

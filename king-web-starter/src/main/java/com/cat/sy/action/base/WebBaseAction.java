package com.cat.sy.action.base;

import com.cat.bean.PageControlInfo;
import com.cat.bean.RResult;
import com.cat.bean.tree.Node;
import com.cat.bean.tree.easyUI.FEasyUITree;
import com.cat.bean.tree.easyUI.Tree;
import com.cat.sy.entity.SyUser;
import com.common.exception.RequestException;
import com.common.util.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 基础action
 *
 * @author ZZM
 */
@Slf4j
public class WebBaseAction {

    /**
     * 请求
     */
    protected HttpServletRequest request;
    /**
     * 响应
     */
    protected HttpServletResponse response;
    /**
     * 会话
     */
    protected HttpSession session;
    // 分页行数
    public static final String PAGE_ROWS = "rows";

    // 分页总条数
    public static final String PAGE_TOTAL = "total";

    // 其他查询条件
    public static final String OSEARCH = "osearch";
    // 是否检查版本
    public static final String ISCHECKOVER = "isCheckOver";

    /**
     * 每页显示的记录数
     */
    public int rows = 20;

    /**
     * 当前第几页
     */
    public int page = 1;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    /**
     * 设置分页参数
     *
     * @param page 分页页数
     * @param rows 一页显示总记录数
     */
    @ModelAttribute
    public void setPageParams(String page, String rows) {
        if (!ObjectUtils.isEmpty(page)) {
            this.page = Integer.valueOf(page);
        }
        if (!ObjectUtils.isEmpty(rows)) {
            this.rows = Integer.valueOf(rows);
        }
    }

    /**
     * 页面起始条数
     *
     * @return
     */
    public int getStart() {
        return (page - 1) * rows + 1;
    }

    /**
     * 分页条数
     *
     * @return
     */
    public int getEnd() {
        return rows;
    }

    /**
     * 根据参数名称，获取参数值
     *
     * @param name 参数名字
     *
     * @return String
     */
    public String getRequestParameter(String name) {
        String value = request.getParameter(name);
        if (!ObjectUtils.isEmpty(value)) {
            //不为空，才去做trim
            value = value.trim();
        }
        return value;
    }

    /**
     * 获取请求的所有参数Map集合
     *
     * @return Map
     */
    public Map<String, String[]> getRequestParameterMap() {
        Map<String, String[]> map = request.getParameterMap();
        return map;
    }

    /**
     * 设置所有参数Map集合
     *
     * @return Map
     */
    public void setAttributes() {
        Map<String, String[]> map = request.getParameterMap();
        for (Entry<String, String[]> entry : map.entrySet()) {
            String[] value = entry.getValue();
            request.setAttribute(entry.getKey(), value[0]);
        }
        // request.setAttribute("fastdfsUrl",
        // RSystemConfig.getValue("fastdfs.url"));
    }

    /**
     * 写JSON数据
     *
     * @param pageInfo
     *
     * @throws RequestException
     */
    public void printWriterJson(PageControlInfo pageInfo) throws RequestException {
        getPrintWriter().write(formatJosn(pageInfo));
    }

    public void printWriterJson(RResult rResult) throws RequestException {
        getPrintWriter().write(JsonUtil.toJson(rResult));
    }

    public void printWriterJson(List<?> list) throws RequestException {
        if (CollectionUtils.isEmpty(list)) {
            getPrintWriter().write("{}");
            return;
        }
        getPrintWriter().write(JsonUtil.toJson(list));
    }

    public void printWriterJson(Object object) {
        getPrintWriter().write(JsonUtil.toJson(object));
    }

    /**
     * 获取写对象
     *
     * @return PrintWriter
     */
    public PrintWriter getPrintWriter() {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            out = response.getWriter();
        } catch (IOException e) {
            log.error("out is exception", e);
        }
        return out;
    }

    /**
     * 将PageControlInfo对象格式成json分页对象
     *
     * @param pageInfo 分页控制对象
     *
     * @throws RequestException
     */
    public String formatJosn(PageControlInfo pageInfo) throws RequestException {
        List<?> list = pageInfo.getSearchData();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(PAGE_ROWS, null == list ? "" : list);
        data.put(PAGE_TOTAL, pageInfo.getTotalNum());
        return JsonUtil.toJson(data);
    }

    /**
     * 实体中不存在的参数，加载到map中，查询用到
     *
     * @return Map<String, Object>
     */
    public Map<String, Object> loadOsearch() {
        Map<String, String[]> map = getRequestParameterMap();
        Map<String, Object> osearch = new HashMap<String, Object>();
        for (Entry<String, String[]> entry : map.entrySet()) {
            if (StringUtils.startsWith(entry.getKey(), OSEARCH)) {
                String k = StringUtils.substring(entry.getKey(), entry.getKey().indexOf(OSEARCH + "."));
                String[] value = entry.getValue();
                osearch.put(k, value[0] == "" ? null : value[0]);
            }
        }
        // 排序字段
        String sort = getRequestParameter("sort");
        String order = getRequestParameter("order");
        if (!StringUtils.isAnyBlank(sort, order)) {
            String[] sorts = sort.split(",");
            String[] orders = order.split(",");
            for (int i = 0; i < sorts.length; i++) {
                osearch.put(sorts[i] + "_order", orders[i]);
            }
        }
        return osearch;
    }

    public SyUser getSessionUser() {
        return (SyUser) session.getAttribute("syUser");
    }

    public void getTreeJson(List<Node> list) throws RequestException {
        FEasyUITree easyUI = new FEasyUITree();
        List<Tree> l = easyUI.getTree(list);
        getPrintWriter().write(JsonUtil.toJson(l));
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

}

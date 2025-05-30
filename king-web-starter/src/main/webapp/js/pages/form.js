var editIndex = undefined;

/**
 * grid点中行记录索引
 * @param index        列表索引
 */
function onClickRow(index) {
    if (editIndex != index) {
        editIndex = index;
    }
}

/**
 * 字符串转日期
 * @param date        Data    日期
 * @returns String
 */
function date_format(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}

/**
 * 字符转日期
 * @param s            String    字符串日期[yyyy-mm-dd]
 * @returns Date
 */
function date_parse(s) {
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0], 10);
    var m = parseInt(ss[1], 10);
    var d = parseInt(ss[2], 10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
        return new Date(y, m - 1, d);
    } else {
        return new Date();
    }
}

/**
 * 表格重新加载
 * @param gridId    String    表格ID
 */
function grid_reload(gridId) {
    $("#" + gridId).datagrid('reload');
    tab_mask_hide();
}

/**
 * 获取选中的记录
 * @param gridId    String    表格ID
 * @param o            Object    页面对象
 * @returns
 */
function grid_select_record(gridId, o) {
    if (undefined == o || null == o) {
        return $('#' + gridId).datagrid('getSelected');
    } else {
        return o.$('#' + gridId).datagrid('getSelected');
    }
}

/**
 * 表格重新加载
 * @param gridId    String    表格ID
 */
var editIndex = undefined;

function grid_endEditing(gridId) {
    if (editIndex == undefined) {
        return true;
    }
    if ($('#' + gridId).datagrid('validateRow', editIndex)) {
        $('#' + gridId).datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}

/**
 * ajax
 * @param url    String    请求地址
 * @param data    String    JSON数据
 * @param model    String    处理结束后的模式(0不处理,1关闭tab刷新父页面，2关闭窗口,3关闭tab不刷新父页面)
 * @param toTabIndex    Integer 转向tab页面索引
 */
function form_ajax(url, data, toTabIndex, model) {
    if (undefined == model || null == model) {
        model = 1;
    }
    var t = (undefined == toTabIndex || null == toTabIndex) ? parent[0] : parent[toTabIndex];
    $.ajax({
        type: "POST",
        url: url,
        cacheLength: 0,
        dataType: 'json',
        data: data,
        success: function (info) {
            $.messager.alert('提示信息', info.note, "info", function () {
                var index = t.tab_get_selected_index();
                if (0 == model) {
                    parent[0].bsMainLoad();
                } else if (1 == model) {
                    //重新加载上一页,关闭当前页面
                    parent[index - 1].bsMainLoad();
                    t.tab_close_after(index);
                } else if (2 == model) {
                    //重新加载上一页
                    parent[index - 1].bsMainLoad();
                } else if (3 == model) {
                    //关闭当前页
                    t.tab_close_after(index);
                }
            });
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.messager.alert('处理异常提示', errorThrown);
        }
    });
}

/**
 * 列表单个提交
 * @param o
 * @param url
 * @param gridId
 * @param toTabIndex    Integer 转向tab页面索引
 */
function grid_save_single(o, url, gridId, toTabIndex) {
    o.click(function () {
        if (this.disabled == true) {
            return;
        }
        $('#' + gridId).datagrid('acceptChanges');
        var row = grid_select_record(gridId);
        if (undefined == row || null == row) {
            $.messager.alert('提示信息', '请选择记录');
            return;
        }
        form_ajax(url, row, toTabIndex, -1);
    });
}

/**
 * 列表批量提交
 * @param o
 * @param url
 * @param gridId
 * @param toTabIndex    Integer 转向tab页面索引
 */
function grid_save_batch(o, url, gridId, toTabIndex) {
    o.click(function () {
        if (this.disabled == true) {
            return;
        }
        var rows = $('#' + gridId).datagrid('getSelections');
        if (undefined == rows || null == rows) {
            $.messager.alert('提示信息', '请选择记录');
            return;
        }
        form_ajax(url, "data=" + JSON.stringify(rows), toTabIndex, 1);
    });
}

function grid_get_values(gridId, key) {
    var obj = $('#' + gridId);
    var rows = obj.datagrid('getSelections');
    var pk = key;
    if (undefined == pk || null == pk) {
        pk = "id";
    }
    var ids = [];
    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        ids.push(row[pk]);
    }
    ids.join(',');
    return ids;
}

/**
 * 列表选中记录进行删除
 * @param gridId1    String    第一个列表id
 * @param rows1        Ojbect    第一个列表ROWS对象
 * @param rows2        Ojbect    第二个列表ROWS对象
 * @param code        String    对比代码
 */
function grid_delrows_select_impl(gridId) {
    var rows = $('#' + gridId).datagrid('getSelections');
    if (undefined == rows || null == rows) {
        $.messager.alert('提示信息', '请删除选择记录');
        return;
    }
    var copyRows = [];
    for (var j = 0; j < rows.length; j++) {
        copyRows.push(rows[j]);
    }
    for (var i = 0; i < copyRows.length; i++) {
        var index = $('#' + gridId).datagrid('getRowIndex', copyRows[i]);
        if (index != -1) {
            $('#' + gridId).datagrid('deleteRow', index);
        }
    }
}

/**
 * 两个列表对比删除重复
 * @param gridId1    String    第一个列表id
 * @param rows1        Ojbect    第一个列表ROWS对象
 * @param rows2        Ojbect    第二个列表ROWS对象
 * @param code        String    对比代码
 */
function grid_delrows_compare_impl(gridId1, rows1, rows2, code) {
    var copyRows = [];
    for (var j = 0; j < rows1.length; j++) {
        copyRows.push(rows1[j]);
    }
    if (rows2.length > 0) {
        for (var i = 0; i < copyRows.length; i++) {
            for (var j = 0; j < rows2.length; j++) {
                if (copyRows[i][code] == rows2[j][code]) {
                    var index = $('#' + gridId1).datagrid('getRowIndex', copyRows[i]);
                    if (index != -1) {
                        $('#' + gridId1).datagrid('deleteRow', index);
                        break;
                    }
                }
            }
        }
    }
}

/**
 * 清除表单所有控件的值
 * @param o            Object    按键对象
 * @param formid    String    表单ID
 */
function form_clear(o, formId) {
    o.click(function () {
        if (this.disabled == true) {
            return;
        }
        $('#' + formId).form('clear');
    });
}

/**
 * 提交事件
 * @param o                Object    按键对象
 * @param formId        String    表单编号
 * @param url            String    提交URL
 * @param toTabIndex    Integer 转向tab页面索引
 * @param mode            String  提示(M提示)
 */
function form_submit(o, formId, url, toTabIndex, mode) {
    o.click(function () {
        if (this.disabled == true) {
            return;
        }
        var t = (undefined == toTabIndex || null == toTabIndex) ? parent[0] : parent[toTabIndex];
        form_submit_impl(formId, url, t, mode);
    });
}

function form_submit_impl(formId, url, toTabIndex, mode) {
    $('#' + formId).form('submit', {
        url: url,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var dt = toJson(data);
            if (true == dt.success) {
                if (undefined == mode || null == mode) {
                    //重新加载父页面
                    toTabIndex.bsMainLoad();
                    //关闭当前tab
                    var index = toTabIndex.tab_get_selected_index();
                    toTabIndex.tab_close_after(index);
                }
                if ('M' == mode) {
                    $.messager.alert('提示信息', dt.note);
                }
            } else {
                $.messager.alert('提示信息', dt.note);
            }
        }, error: function (message) {
            $.messager.alert('提示信息', '处理失败');
        }
    });
}

/**
 * 关闭tab页
 * @param o                Object        按键对象
 * @param toTabIndex    Integer        tab页
 */
function form_close_tab(o, toTabIndex) {
    o.click(function () {
        var t = (undefined == toTabIndex || null == toTabIndex) ? parent[0] : parent[toTabIndex];
        var index = t.tab_get_selected_index();
        t.tab_close_after(index);
    });
}

/**
 * 表单查询
 * @param o            Object    按键对象
 * @param formId    String    表单ID
 * @param gridId    String    表格ID
 */
function form_search(o, formId, gridId) {
    o.click(function () {
        var queryParams = $('#' + formId).form('getJsonPack');
        var g = $('#' + gridId);
        g.datagrid('options').queryParams = queryParams;
        grid_reload(gridId);
    });
}


/**
 * 导出
 * @param o            Object    按键对象
 * @param formId    String    seach表单ID
 * @param gridId    String    表格ID
 * @param url    String    导出地址
 */
function form_export_do(o, formId, gridId, url) {
    o.click(function () {
        var uri = "";
        var fileds = $('#' + gridId).datagrid('getColumnFields');
        var queryParams = $('#' + formId).form('getJsonPack');
        if (uri.indexOf("?") > 0) {
            uri = url + "&" + serializeObject(queryParams);
        } else {
            uri = url + "?" + serializeObject(queryParams);
        }
        uri = uri + "&heads=" + fileds;
        if (window.confirm('确定导出？')) {
            window.open(uri);
        }
    });
}

/**
 * 是否选择表格行
 * @param gridId    String    表格ID
 * @returns    boolean (true已选,false未选)
 */
function form_grid_isSelect(gridId) {
    var row = grid_select_record(gridId);
    if (row == undefined) {
        $.messager.alert('提示信息', '请选择需要修改的记录');
        return false;
    }
    return true;
}

/**
 * 构建工具栏
 * @param ctlObj    Array    按键数组
 * @returns    JSON
 */
function form_toolbar(buttons) {
    var addBtn = {text: '新增', iconCls: 'icon-add', id: 'addBtn'};
    var updateBtn = {text: '修改', iconCls: 'form-edit', id: 'updateBtn'};
    var deleteBtn = {text: '删除', iconCls: 'form-delete', id: 'deleteBtn'};
    var viewBtn = {text: '查看', iconCls: 'form-view', id: 'viewBtn'};
    var toolbar = [addBtn, updateBtn, deleteBtn, viewBtn];
    if (null != buttons) {
        for (var i = 0; i < buttons.length; i++) {
            toolbar.push(buttons[i]);
        }
    }
    return toolbar;
}

/**
 * 表单对象控制
 * @param ctlObj        String    按键对象控制(控件ID1,控件ID2)
 * @param status        String    按键状态(hide隐藏，show显示， disable禁用， enable可用)
 */
function form_obj_control(ctlObj, status) {
    if (null == ctlObj || "" == ctlObj) {
        return;
    }
    var co = ctlObj.split(',');
    for (var i = 0; i < co.length; i++) {
        if (status == 'disable') {
            $('#' + co[i]).linkbutton('disable');
            $('#' + co[i])[0].disabled = true;
        } else if (status == 'enable') {
            $('#' + co[i]).linkbutton('enable');
            $('#' + co[i])[0].disabled = false;
        } else if (status == 'hide') {
            $('#' + co[i]).hide();
        } else if (status == 'show') {
            $('#' + co[i]).show();
        }

    }

}

/**
 * 控制按键是否显示
 * @param ctlObj        JSON    按键对象控制json(['控件ID1','-','控件ID2'])
 * @param status        String    按键状态(hide隐藏，show显示， disable禁用， enable可用)
 */
function form_toolbar_control(ctlObj, status) {
    if (null == ctlObj || "" == ctlObj) {
        return;
    }
    for (var i = 0; i < ctlObj.length; i++) {
        if (ctlObj[i] != "-") {
            if (status == 'hide') {
                $('#' + ctlObj).hide();
            } else if (status == 'disable') {
                $('#' + ctlObj[i]).linkbutton('disable');
                $('#' + ctlObj[i])[0].disabled = true;
            } else if (status == 'show') {
                $('#' + ctlObj[i]).show();
            }
        }
    }
}

/**
 * 表格工具栏新增处理
 * @param o            Object    按键对象
 * @param url        String    按键事件地址
 * @param page    Integer    关闭Tabs索引(从0开始, -1不关闭)
 */
function form_add(o, url, page) {
    o.click(function () {
        if (this.disabled == true) {
            return;
        }
        var title = this.text;
        if (undefined == title) {//IE8
            title = this.innerText;
        }
        form_add_impl(title, url, page);
    });
}

function form_add_impl(title, url, page) {
    tab_close_after(page);
    tab_add_parent(title, 'icon-add', url);
}

/**
 * 表格工具栏更新处理
 * @param o            Object    按键对象
 * @param url        String    按键事件地址
 * @param gridId    String    表格ID
 * @param page    Integer    关闭Tabs索引(从0开始, -1不关闭)
 */
function form_update(o, setUrl, gridId, page, columns) {
    o.click(function () {
        if (this.disabled == true) {
            return;
        }
        var row = grid_select_record(gridId);
        if (row == undefined) {
            $.messager.alert('提示信息', '请选择需要修改的记录');
            return false;
        }
        var rows = $('#' + gridId).datagrid('getSelections');
        if (rows.length > 1) {
            $.messager.alert('提示信息', '请选择单条记录修改');
            return false;
        }
        var url = setUrl;
        if (!RString_contains(url, '?')) {
            url = url + '?1=1';
        }
        ;
        if (undefined == columns || null == columns) {
            if (row.id != undefined) {

                url += "&id=" + row.id;
            }
        } else {
            var keys = columns.split(',');
            for (var i = 0; i < keys.length; i++) {
                var key = keys[i];
                url += '&' + key + '=' + row[key];
            }
            url += '&d=' + Date.parse(new Date());
        }

        var title = this.text;
        if (undefined == title) {//IE8
            title = this.innerText;
        }
        form_update_impl(title, url, page);
    });
}

// 跳转更新页面
function form_update_impl(title, url, page) {
    tab_close_after(page);
    tab_add_parent(title, 'form-edit', url);
}

/**
 * 表格工具栏删除处理
 * @param o            Object    按键对象
 * @param url        String    按键事件地址
 * @param gridId    String    表格ID
 * @param pkey        String    主键key(示例:"id")
 */
function form_delete(o, url, gridId, pkey) {
    o.click(function () {
        if (this.disabled == true) {
            return;
        }
        var obj = $('#' + gridId);
        var rows = obj.datagrid('getSelections');
        if (null == rows || '' == rows) {
            $.messager.alert('提示信息', '请选择需要删除的记录');
            return;
        }
        form_delete_impl(rows, url, pkey, obj);
    });
}

/**
 * 查看
 * @param rows    Object    记录对象
 * @param url    String    请求地址
 * @param pkey    String    主键key(示例:"id")
 * @param obj    Object    列表对象
 * @param mode    String  提示(M提示)
 */
function form_delete_impl(rows, url, pkey, obj, mode) {
    $.messager.confirm('提示信息', '你确定删除?', function (r) {
        if (r) {
            var ids = [];
            var pk = pkey;
            if (undefined == pk || null == pk) {
                pk = "id";
            }
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                ids.push(row[pk]);
            }
            ids.join(',');
            $.ajax({
                type: "post", url: url, data: "ids=" + ids,
                success: function (data) {
                    var dt = toJson(data);
                    if (true == dt.success) {
                        if (undefined == mode || null == mode) {
                            $.messager.alert('提示信息', '成功删除[' + rows.length + "条]记录。");
                            obj.datagrid('reload');
                            tab_mask_hide();
                        } else if ("M" == mode) {
                            $.messager.alert('提示信息', dt.note);
                        }
                    } else {
                        $.messager.alert('提示信息', dt.note);
                    }
                }, error: function (message) {
                    $.messager.alert('提示信息', '处理失败');
                }
            });
        }
    });
}

/**
 * 查看
 * @param o            Object    按键对象
 * @param url        String    请求地址
 * @param gridId    String    表格id
 * @param icon        String    图标id
 * @param sheet    Integer    关闭Tabs索引(从0开始, -1不关闭)
 * @param columns    String    传到ACTION的字段集合(示例:"id,name")
 */
function form_view(o, url, gridId, icon, sheet, columns) {
    o.click(function () {
        if (this.disabled == true) {
            return;
        }
        var title = this.text;
        if (undefined == title) {//IE8
            title = this.innerText;
        }
        var row = grid_select_record(gridId);
        if (undefined == row || null == row) {
            $.messager.alert('提示信息', '请选择记录');
            return;
        }
        var rows = $('#' + gridId).datagrid('getSelections');
        if (rows.length > 1) {
            $.messager.alert('提示信息', '请选择单条记录');
            return false;
        }
        form_view_impl(title, url, row, icon, sheet, columns);
    });
}

/**
 * 简单查看
 * @param o
 * @param url
 * @param icon
 * @param sheet
 */
function form_simple_view(o, url, icon, sheet, columns) {
    o.click(function () {
        if (this.disabled == true) {
            return;
        }
        var title = this.text;
        if (undefined == title) {//IE8
            title = this.innerText;
        }
        form_view_impl(title, url, null, icon, sheet, null);
    });
}

// 查看实现
function form_view_impl(title, url, row, icon, sheet, columns) {
    tab_close_after(sheet);
    var vIcon = 'form-view';
    if (null != icon) {
        vIcon = icon;
    }
    var reqUrl = form_build_req_url(url, row, columns);
    tab_add_parent(title, vIcon, reqUrl);
}

/**
 * 根据不同操作加载数据，并对表单控件进行控制
 * @param formId    String    表单对象ID
 * @param operate    String    页面加载操作状态(A新增,U更新,V查看,H查看历史)
 * @param params    JSON    对象{'url':访问地址,'gridId':列表对象ID,'pKey':记录主键字段, 'toTabIndex':转向至tabs索引数的页面（从0开始)}
 */
function form_load(formId, operate, params) {
    if (null == formId || null == params) {
        $.messager.alert('提示信息', 'form_load方法参数异常.');
        return;
    }
    var fm = $('#' + formId);
    if ('A' == operate) {
    } else if ('U' == operate || 'V' == operate || 'H' == operate) {
        fm.form('loadRecordFromGrid', params);
        $('#updateShowProperty').show();
        if ('V' == operate || 'H' == operate) {
            // 取消控件检查
            fm.form("disableValidation");
            // 禁用控件
            form_disable(formId, true);
        }
    }
}

function form_forload(formId, operate) {
    if (null == formId) {
        $.messager.alert('提示信息', 'form_load方法参数异常.');
        return;
    }
    var fm = $('#' + formId);
    if ('A' == operate) {
    } else if ('U' == operate || 'V' == operate || 'H' == operate) {
        var row = grid_select_record("dg", parent[0]);
        fm.form('load', row);
        $('#updateShowProperty').show();
        if ('V' == operate || 'H' == operate) {
            // 取消控件检查
            fm.form("disableValidation");
            // 禁用控件
            //form_disable(formId, true);
        }
    }
}

function form_load_impl(formId, url) {
    $('#' + formId).form('load', url);
}

/**
 * 禁用form表单中所有的input[文本框、复选框、单选框],select[下拉选],多行文本框[textarea]
 * @param formId        String    表单ID
 * @param isDisabled    boolean    true禁用,false启用
 */
function form_disable(formId, isDisabled) {
    $("#" + formId + " :input").attr("disabled", isDisabled);
    $("#" + formId + " a[class='easyui-linkbutton l-btn l-btn-plain']").each(function () {
        if (this.id) {
            $("#" + this.id).linkbutton('disable');
            this.disabled = isDisabled;
        }
    });
    $("#" + formId + " .easyui-combobox").combobox({disabled: isDisabled});
    $("#" + formId + " .easyui-datebox").datebox({disabled: isDisabled});
    $("#" + formId + " .easyui-datetimebox").datetimebox({disabled: isDisabled});
    $("#" + formId + " .easyui-timespinner").timespinner({disabled: isDisabled});
}

function form_readonly(formId) {
    $("#" + formId + " :input").attr("readonly", "readonly");
}

/**
 * 生成请求的URL地址
 * @param url            String    地址
 * @param data            JSON    数据
 * @param columns        String    传到ACTION的字段集合(示例"id,name")
 * @returns {String}
 */
function form_build_req_url(url, data, columns) {
    var reqUrl = url;
    if (!RString_contains(url, '?')) {
        reqUrl = url + '?1=1';
    }
    ;
    if (null == data) {
        return reqUrl;
    }
    if (undefined == columns || null == columns) {
        if (data.id == undefined) {
            return reqUrl;
        }
        return reqUrl + "&id=" + data.id;
    }
    var keys = columns.split(',');
    for (var i = 0; i < keys.length; i++) {
        var key = keys[i];
        reqUrl = reqUrl + '&' + key + '=' + data[key];
    }
    return reqUrl;
}

/**
 * 打开iframe窗口
 * @param o            Object    按键对象
 * @param divId        String  窗口对象编号
 * @param iframeId  Stirng  窗口中iframe编号
 * @param url        String    请求地址
 * @param gridId    String    表格编号
 * @param columns    String    传到ACTION的字段集合(示例:"id,name")
 */
function win_ifm_open(o, divId, iframeId, url, gridId, columns) {
    o.click(function () {
        if (this.disabled == true) {
            return;
        }
        var row = grid_select_record(gridId);
        if (undefined == row || null == row) {
            $.messager.alert('提示信息', '请选择需要修改的记录');
            return;
        }
        win_ifm_open_impl(divId, iframeId, url, row, columns);
    });
}

function win_ifm_open_impl(divId, iframeId, url, row, columns) {
    var winObj = $("#" + divId);
    winObj.mask({maskMsg: "加载中....."});
    var reqUrl = form_build_req_url(url, row, columns);
    $("#" + iframeId).attr("src", reqUrl);
    winObj.window('open');
    winObj.mask("hide");
}

/**
 * 打开普通窗口
 * @param o            Object    按键对象
 * @param w            JSON    窗口属性({'winId':'窗口对象', 'url':'请求地址', 'title':'标题', 'width':宽度, 'height':高度})
 * @param toolbar    Object    窗口按键对象
 */
function win_open(o, w, toolbar) {
    o.click(function () {
        if (undefined == w || null == w) {
            $.messager.alert('提示信息', '打开窗口失败');
            return;
        }
        if (undefined == w.winId || undefined == w.url) {
            $.messager.alert('提示信息', '窗口参数异常');
            return;
        }
        win_open_impl(w, toolbar);
    });
}

function win_open_impl(w, toolbar) {
    var winObj = $("#" + w.winId);
    var width = (undefined == w.width || null == w.width) ? 800 : w.width;
    var height = (undefined == w.height || null == w.height) ? 460 : w.height;
    var tb = (undefined == toolbar) ? [] : toolbar;
    var title = this.text;//默认获取按键名称
    if (undefined == title) {//IE8
        title = this.innerText;
    }
    title = (undefined == w.title || null == w.title) ? title : w.title;
    winObj.mask({maskMsg: "加载中....."});
    winObj.dialog({
        closed: false, cache: false, modal: true,
        title: title, width: width, height: height, href: w.url, toolbar: tb
    });
    winObj.mask("hide");
}

/**
 * 打开iframe窗口
 * @param o            Object    按键对象
 * @param w            Object    窗口对象
 */
function win_close(o, w) {
    o.click(function () {
        w.window('close');
    });
}

/**
 * 窗口表单提交
 * @param o                    Object    按键对象
 * @param formId            String    表单对象编号
 * @param url                String    请求路径
 * @param winId                String    窗口编号
 * @param isRefreshParent    String    是否刷新父页面(Y是刷新)
 */
function win_submit(o, formId, url, winId, isRefreshParent) {
    o.click(function () {
        if (true == this.disabled) {
            return;
        }
        //var _val = $('#'+formId).form('getJsonToStr');
        //alert(_val);
        win_submit_impl(formId, url, winId, isRefreshParent);
    });
}

function win_submit_impl(formId, url, winId, isRefreshParent) {
    var t = parent;
    $('#' + formId).form('submit', {
        url: url,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var dt = toJson(data);
            if (true == dt.success) {
                $.messager.alert('提示信息', dt.note);
                //重新加载父页面
                if ('Y' == isRefreshParent) {
                    t.bsMainLoad();
                }
                //关闭窗口
                t.$('#' + winId).window('close');
            } else {
                $.messager.alert('提示信息', dt.note);
            }
        }, error: function (message) {
            $.messager.alert('提示信息', '处理失败');
        }
    });
}

function win_view(o, url, gridId, icon, sheet, columns) {
    o.click(function () {
        if (this.disabled == true) {
            return;
        }
        var row = grid_select_record(gridId, parent);
        if (undefined == row || null == row) {
            $.messager.alert('提示信息', '请选择需要修改的记录');
            return;
        }
        //tab_close_after(sheet);
        var vIcon = 'form-view';
        if (null != icon || "" != icon) {
            vIcon = icon;
        }
        var reqUrl = form_build_req_url(url, row, columns);
        var title = this.text;
        if (undefined == title) {//IE8
            title = this.innerText;
        }
        tab_add_parent(title, vIcon, reqUrl);
    });
}

function ctl_checkbox_value(groupId) {
    var qx = $("input[name='" + groupId + "']:checked").map(function () {
        return $(this).val();
    }).get().join(',');
    return qx;
}

function form_combogrid_list(o, url, idTitle, labelTitle) {
    var fields = {idField: 'id', 'textField': 'label'};
    var columns = [
        {field: 'id', title: idTitle, width: 30},
        {field: 'label', title: labelTitle, width: 80}
    ];
    form_combogrid_list_impl(o, url, fields, columns);
}

function form_combogrid_Iconlist(o) {
    var fields = {idField: 'code', 'textField': 'code'};
    var columns = [
        {field: 'code', title: '图标', width: 30, formatter: getCmIcon},
        {field: 'label', title: '名称', width: 80}
    ];
    form_combogrid_list_impl(o, 'cm/icon/findPageList.do', fields, columns);
}

/**
 * 共通下拉列表
 * @param o            控件对象
 * @param url        请求地址
 * @param fields    字段项
 * @param columns    显示列表项
 * @param mFlag        true多选,false单选
 */
function form_combogrid_list_impl(o, url, fields, columns, mFlag) {
    var _idField = fields['idField'];
    var _textField = fields['textField'];
    var _mf = false;
    if (null != mFlag) {
        _mf = mFlag;
    }
    o.combogrid({
        panelWidth: 500,
        idField: _idField,
        textField: _textField,
        pageSize: 10,
        pagination: true,
        multiple: _mf,
        delay: 1500,
        prompt: '输入关键字支持模糊查询',
        url: url,
        method: 'post',
        columns: [columns],
        keyHandler: {
            up: function () {
            },
            down: function () {
            },
            enter: function () {
            },
            query: function (q) {
                o.combogrid('grid').datagrid('reload', {'osearch.text': q});
                o.combogrid('setValue', q);
            }
        },
        fitColumns: true
    });
}

/**
 * 下拉选项
 * @param o            按键对象
 * @param code        列表名称
 * @param type        类型(all全部)
 */
function form_combobox_list(o, code, type) {
    if (undefined == type || null == type) {
        type = '';
    }
    o.combobox({
        url: 'cm/list/findList.do?type=' + type + '&code=' + code,
        method: 'post',
        valueField: 'value',
        textField: 'label'
    });
}

function form_multiple_combogrid_list(o, url, idTitle, labelTitle) {
    var fields = {idField: 'code', 'textField': 'label'};
    var columns = [
        {field: 'code', checkbox: true},
        {field: 'id', title: idTitle, width: 30},
        {field: 'label', title: labelTitle, width: 80}
    ];
    form_combogrid_list_impl(o, url, fields, columns, true);
}

// 省市
function form_combobox_area(o, gridId) {
    var rowCityCode = '';
    var row = null;
    if (null != gridId) {
        row = parent[0].$('#' + gridId).datagrid('getSelected');
    }
    if (null == row) {
        rowCityCode = '330000';
    } else {
        rowCityCode = row.cityCode;
    }
    o.combobox({
        url: 'cm/area/findAreaListByCombox.do',
        method: 'post',
        valueField: 'code',
        textField: 'label',
        onChange: function (newValue, oldValue) {
            $('#cityCodeCombo').combobox({
                url: 'cm/area/findAreaListByCombox.do?parentCode=' + newValue,
                valueField: 'code',
                textField: 'label'
            });
            var areaObj = $('#areaCodeCombo');
            if (null != areaObj) {
                areaObj.combobox('setValue', '');
                areaObj.combobox('setText', '');
            }
        },
        onLoadSuccess: function () {
            var cityCode = $('#cityCodeCombo').combobox("getValue");
            var provinceCode = rowCityCode.substring(0, 2) + "0000";
            o.combobox('setValue', provinceCode);
            if (null != cityCode) {
                $('#cityCodeCombo').combobox('setValue', cityCode);
            }
        }

    });
}


function form_imgUpload(o, url, successMethod, inputName) {
    if (null == inputName || "" == inputName) {
        inputName = "imageFile";
    }
    var ts = new Date().getTime();

    var dialogHtml = '<div id="imgImport_' + ts + '"  style="width:90%;padding:10px;"  >' +
        '<div style="width:90%;height:90%;float: left;padding-top: 10px;">' +
        '<input id="infile_' + ts + '"  type="text"   style="width:100%"  name="' + inputName + '"  />' +
        '	 <div style="width:90%;float: left;text-align: right;margin-top: 20px;" >' +
        '	<a   id="importSave_' + ts + '"  href="javascript:void(0)"   >保存</a>' +
        '	<a   id="importExit_' + ts + '"  href="javascript:void(0)"    style="margin-right: 10px;" >退出</a>' +
        '	 </div>' +
        '</div>' +
        ' </div>';
    $("body").append(dialogHtml);
    $("#imgImport_" + ts).dialog({
        title: '图片上传',
        width: 300,
        height: 160,
        closed: true,
        cache: false,
        modal: true
    });
    $("#infile_" + ts).filebox({prompt: "选择上传的图片...", buttonText: "选择文件"});
    $("#importSave_" + ts).linkbutton();
    $("#importExit_" + ts).linkbutton();

    //打开对话框事件
    o.bind('click', function () {
        $("#imgImport_" + ts).dialog("open");
    });
    //保存
    $("#importSave_" + ts).bind('click', function () {
        var fileId = $("#imgImport_" + ts).find("input[type='file']").attr("id");
        form_imgUploadMethod(fileId, url, function (data) {
            if (null != data && true == data.success) {
                //上传成功
                successMethod(data);
                //关闭
                $("#imgImport_" + ts).dialog("close");
            } else {
                $.messager.alert('提示信息', "上传失败，" + data.note);
            }
            $("#infile_" + ts).filebox({prompt: "选择上传的图片...", buttonText: "选择文件"});
        });
    });
    //退出
    $("#importExit_" + ts).bind('click', function () {
        $("#imgImport_" + ts).dialog("close");
    });

}


//图片上传
function form_imgUploadMethod(fileId, url, successMethod) {
    //得到上传文件的全路径
    var fileName = $('#' + fileId).val();
    //进行基本校验
    if (fileName == "") {
        $.messager.alert('提示信息', '请选择上传文件！');
        return false;
    }
    //对文件格式进行校验
    var d1 = /\.[^\.]+$/.exec(fileName);
    d1 = (d1 + "").toLowerCase();
    if (d1 != ".jpg" && d1 != ".png" && d1 != ".gif") {
        $.messager.alert('提示信息', "图片格式必须是jpg,png,gif");
        return false;
    }

    $.ajaxFileUpload({
        url: url,  //用于文件上传的服务器端请求地址
        secureuri: false, //是否需要安全协议，一般设置为false
        fileElementId: fileId, //文件上传域的ID
        dataType: 'json', //返回值类型 一般设置为json
        success: successMethod,
        error: function (data, status, e)//服务器响应失败处理函数
        {
            alert('上传失败');
        }
    });
}

/**
 * 文件上传
 * @param o
 * @param url
 * @param successMethod
 * @param inputName
 */
function form_fileUpload(o, url, successMethod, inputName) {
    if (null == inputName || "" == inputName) {
        inputName = "fileName";
    }
    var ts = new Date().getTime();

    var dialogHtml = '<div id="fileImport_' + ts + '"  style="width:90%;padding:10px;"  >' +
        '<div style="width:90%;height:90%;float: left;padding-top: 10px;">' +
        '<input id="infile_' + ts + '"  type="text"   style="width:100%"  name="' + inputName + '"  />' +
        '	 <div style="width:90%;float: left;text-align: right;margin-top: 20px;" >' +
        '	<a   id="importSave_' + ts + '"  href="javascript:void(0)"   >保存</a>' +
        '	<a   id="importExit_' + ts + '"  href="javascript:void(0)"    style="margin-right: 10px;" >退出</a>' +
        '	 </div>' +
        '</div>' +
        ' </div>';
    $("body").append(dialogHtml);
    $("#fileImport_" + ts).dialog({
        title: '文件上传',
        width: 300,
        height: 160,
        closed: true,
        cache: false,
        modal: true
    });
    $("#infile_" + ts).filebox({prompt: "选择上传的文件...", buttonText: "选择文件"});
    $("#importSave_" + ts).linkbutton();
    $("#importExit_" + ts).linkbutton();

    //打开对话框事件
    o.bind('click', function () {
        $("#fileImport_" + ts).dialog("open");
    });
    //保存
    $("#importSave_" + ts).bind('click', function () {
        var fileId = $("#fileImport_" + ts).find("input[type='file']").attr("id");
        form_fileUploadMethod(fileId, url, function (data) {
            if (null != data && true == data.success) {
                //上传成功
                successMethod(data);
                //关闭
                $("#fileImport_" + ts).dialog("close");
            } else {
                $.messager.alert('提示信息', "上传失败，" + data.note);
            }
            $("#infile_" + ts).filebox({prompt: "选择上传的文件...", buttonText: "选择文件"});
        });
    });
    //退出
    $("#importExit_" + ts).bind('click', function () {
        $("#fileImport_" + ts).dialog("close");
    });
}

//文件上传
function form_fileUploadMethod(fileId, url, successMethod) {
    //得到上传文件的全路径
    var fileName = $('#' + fileId).val();
    //进行基本校验
    if (fileName == "") {
        $.messager.alert('提示信息', '请选择上传文件！');
        return false;
    }

    $.ajaxFileUpload(
        {
            url: url,  //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: fileId, //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            success: successMethod,
            error: function (data, status, e)//服务器响应失败处理函数
            {
                alert('上传失败');
            }
        });
}

function isZm(str) {
    var reg = /^(?![^a-zA-Z]+$)(?!\D+$)(?![!@#$%&*^]$).{6,}$/;
    if (reg.test(str)) {
        return true;
    } else {
        alert("密码必须长度大于等于为6位，且必须包含字母数字和特殊字符!@#$%&*^");
        return false;
    }
    ;
}


var serializeObject = function (obj) {
    if (typeof obj === 'string') return obj;
    var resultArray = [];
    var separator = '&';
    for (var prop in obj) {
        if (obj.hasOwnProperty(prop)) {
            if ($.isArray(obj[prop])) {
                var toPush = [];
                for (var i = 0; i < obj[prop].length; i++) {
                    toPush.push(encodeURIComponent(prop) + '=' + encodeURIComponent(obj[prop][i]));
                }
                if (toPush.length > 0) resultArray.push(toPush.join(separator));
            } else {
                // Should be string
                resultArray.push(encodeURIComponent(prop) + '=' + encodeURIComponent(obj[prop]));
            }
        }

    }

    return resultArray.join(separator);
};
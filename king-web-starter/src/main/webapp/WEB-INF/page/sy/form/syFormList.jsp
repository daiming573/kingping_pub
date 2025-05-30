<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>表单权限列表</title>
    <%@include file="/head.jsp" %>
</head>
<body class="easyui-layout">
<div class="easyui-panel" data-options="region:'north'">
    <form id="searchForm" method="post">
        <table class="form-search">
            <tr>
                <td style="width:10%">中文名称：</td>
                <td style="width:23%"><input class="easyui-validatebox" type="text" name="label"
                                             style="width:90%"></input></td>
                <td style="width:10%">英文名称：</td>
                <td style="width:23%"><input class="easyui-validatebox" type="text" name="name"
                                             style="width:90%"></input></td>
                <td style="width:33%;" class="s_button_left">
                    <a href="javascript:void(0);" class="easyui-linkbutton c9" id="searchBtn">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton c5" id="clearBtn">清除</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- multiple: true, 多选-->
<div data-options="region:'center'" style="border:0px;">
    <table id="dg" class="easyui-datagrid"
           data-options="rownumbers:true,
			fit:true,
			border:true,
			pagination:true,
			toolbar:toolbar,
			fitColumns: true,
			pageSize: 20,
			iconCls:'form-list',
			onClickRow: onClickRow,
			singleSelect:true,
			url:'sy/form/findPageList.do',
			queryParams:{catalogNodeId:'${catalogNodeId}'}">
        <thead>
        <tr>
            <th data-options="field:'id',checkbox:true"></th>
            <th data-options="field:'label',width:80">中文名称</th>
            <th data-options="field:'name',width:80">英文名称</th>
            <th data-options="field:'code', width:80">代码</th>
            <th data-options="field:'typeName',width:80,align:'right'">类型</th>
            <th data-options="field:'createDate',width:80">创建时间</th>
        </tr>
        </thead>
    </table>
</div>
<script type="text/javascript">
    var formObjBtn = {text: '表单对象', iconCls: 'form-list', id: 'formObjBtn'};
    var formRoleBtn = {text: '表单角色', iconCls: 'form-list', id: 'formRoleBtn'};
    var buttons = [formObjBtn, formRoleBtn];
    //构造工具栏
    var toolbar = form_toolbar(buttons);
    $(function () {
        var _syFormTabs = parent.$('#syFormTabs');
        _syFormTabs.mask("hide");
        var catalogNodeId = '${catalogNodeId}';
        $("#addBtn").click(function () {
            closeTabs();
            var _syForm = {
                "iconCls": 'icon-add',
                "url": 'sy/form/goAdd.do?catalogNodeId=' + catalogNodeId,
                "text": '新建'
            };
            _syFormTabs.tabs('addTab', _syForm);
        });
        $("#updateBtn").click(function () {
            closeTabs();
            var row = grid_select_record('dg');
            if (undefined == row || null == row) {
                $.messager.alert('提示信息', '请选择记录');
                return;
            }
            var _syForm = {"iconCls": 'form-edit', "url": 'sy/form/goEdit.do', "text": '修改'};
            _syFormTabs.tabs('addTab', _syForm);
        });
        form_delete($("#deleteBtn"), 'sy/form/doDelete.do', 'dg');
        $("#viewBtn").click(function () {
            closeTabs();
            var row = grid_select_record('dg');
            if (undefined == row || null == row) {
                $.messager.alert('提示信息', '请选择记录');
                return;
            }
            var _syForm = {"iconCls": 'form-view', "url": 'sy/form/goView.do', "text": '查看'};
            _syFormTabs.tabs('addTab', _syForm);
        });
        form_search($('#searchBtn'), 'searchForm', 'dg');
        form_clear($('#clearBtn'), 'searchForm');
        $("#formObjBtn").click(function () {
            closeTabs();
            var record = $('#dg').datagrid('getSelected');
            if (undefined == record || null == record) {
                $.messager.alert('提示信息', '请选择记录');
                return;
            }
            var formId = record.id;
            var _syForm = {"iconCls": 'form-list', "url": 'sy/FormObj/goMain.do?formId=' + formId, "text": '表单对象'};
            _syFormTabs.tabs('addTab', _syForm);
        });
        $("#formRoleBtn").click(function () {
            closeTabs();
            var record = $('#dg').datagrid('getSelected');
            if (undefined == record || null == record) {
                $.messager.alert('提示信息', '请选择记录');
                return;
            }
            var formId = record.id;
            var _syForm = {"iconCls": 'form-list', "url": 'sy/FormRole/goMain.do?formId=' + formId, "text": '表单角色'};
            _syFormTabs.tabs('addTab', _syForm);
        });
    });

    function bsMainLoad() {
        $("#dg").datagrid('reload');
        tab_mask_hide();
    }

    function closeTabs() {
        var _syFormTabs = parent.$('#syFormTabs');
        var tabs = _syFormTabs.tabs('tabs');
        for (var i = tabs.length - 1; i > 0; i--) {
            _syFormTabs.tabs('close', i);
        }
    }
</script>
</body>
</html>
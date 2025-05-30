<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>表单角色列表</title>
    <%@include file="/head.jsp" %>
</head>
<body class="easyui-layout">
<div class="easyui-panel" data-options="region:'north'">
    <form id="searchForm" method="post">
        <table class="form-search">
            <tr>
                <td style="width:10%">角色名称：</td>
                <td style="width:40%"><select id="roleComboGrid" name="roleId" style="width:90%;"></select></td>
                <td style="width:50%;" class="s_button_left">
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
			url:'sy/FormRole/findPageList.do',
			queryParams:{formId:'${formId}'}">
        <thead>
        <tr>
            <th data-options="field:'id',checkbox:true"></th>
            <th data-options="field:'formLabel',width:80">表单名称</th>
            <th data-options="field:'roleLabel',width:80">角色名称</th>
            <th data-options="field:'createDate',width:80">创建时间</th>
        </tr>
        </thead>
    </table>
</div>
<script type="text/javascript">
    var buttons = [];
    //构造工具栏
    var toolbar = form_toolbar(buttons);
    $(function () {
        form_combogrid_list($('#roleComboGrid'), 'pm/role/findPageList.do', '角色编号', '角色名称');
        var _syFormTabs = parent.$('#syFormTabs');
        _syFormTabs.mask("hide");
        var formId = '${formId}';
        $("#addBtn").click(function () {
            _syFormTabs.tabs('close', 2);
            var _syForm = {"iconCls": 'icon-add', "url": 'sy/FormRole/goAdd.do?formId=' + formId, "text": '新建'};
            _syFormTabs.tabs('addTab', _syForm);
        });
        $("#updateBtn").click(function () {
            _syFormTabs.tabs('close', 2);
            var row = grid_select_record('dg');
            if (undefined == row || null == row) {
                $.messager.alert('提示信息', '请选择记录');
                return;
            }
            var _syForm = {"iconCls": 'form-edit', "url": 'sy/FormRole/goEdit.do?formId=' + formId, "text": '修改'};
            _syFormTabs.tabs('addTab', _syForm);
        });
        form_delete($("#deleteBtn"), 'sy/FormRole/doDelete.do', 'dg');
        $("#viewBtn").click(function () {
            _syFormTabs.tabs('close', 2);
            var row = grid_select_record('dg');
            if (undefined == row || null == row) {
                $.messager.alert('提示信息', '请选择记录');
                return;
            }
            var _syForm = {"iconCls": 'form-view', "url": 'sy/FormRole/goView.do?formId=' + formId, "text": '查看'};
            _syFormTabs.tabs('addTab', _syForm);
        });
        form_search($('#searchBtn'), 'searchForm', 'dg');
        form_clear($('#clearBtn'), 'searchForm');

    });

    function bsMainLoad() {
        $("#dg").datagrid('reload');
        tab_mask_hide();
    }
</script>
</body>
</html>
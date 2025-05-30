<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>角色列表</title>
    <%@include file="/head.jsp" %>
    <script type="text/javascript" src="js/pages/formatter.js"></script>
</head>
<body class="easyui-layout">
<div class="easyui-panel" data-options="region:'north'">
    <form id="searchForm" method="post">
        <table class="form-search">
            <tr>
                <td>角色代码：</td>
                <td><input class="easyui-validatebox" type="text" name="code"></input></td>
                <td>角色名称：</td>
                <td><input class="easyui-validatebox" type="text" name="label"></input></td>
                <td class="s_button_left">
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'icon-search',toggle:true" id="searchBtn">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'icon-cancel',toggle:true" id="clearBtn">清除</a>
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
			url:'sy/role/findPageList.do'">
        <thead>
        <tr>
            <th data-options="field:'id',checkbox:true"></th>
            <!-- <th data-options="field:'typeCd',width:60,formatter:getRoleType">角色类型</th> -->
            <th data-options="field:'label',width:120">角色名称</th>
            <th data-options="field:'code',width:120">角色代码</th>
            <th data-options="field:'name',width:120">英文名称</th>
            <th data-options="field:'createTime',width:110">创建时间</th>
        </tr>
        </thead>
    </table>
</div>
<script type="text/javascript">
    var cfgRoleUserBtn = {text: '角色人员', iconCls: 'form-config', id: 'cfgRoleUserBtn'};
    var buttons = [cfgRoleUserBtn];
    var toolbar = form_toolbar(buttons);
    $(function () {
        tab_mask_hide();
        form_search($('#searchBtn'), 'searchForm', 'dg');
        form_clear($('#clearBtn'), 'searchForm');
        form_add($("#addBtn"), 'sy/role/goAdd.do');
        form_update($("#updateBtn"), 'sy/role/goEdit.do', 'dg');
        form_delete($("#deleteBtn"), 'sy/role/doDelete.do', 'dg');
        form_view($("#viewBtn"), 'sy/role/goView.do', 'dg');
        form_view($("#cfgRoleUserBtn"), 'sy/RoleUser/goMain.do', 'dg', 'form-config', null, "id,code,label");
    });

    function bsMainLoad() {
        $("#dg").datagrid('reload');
        tab_mask_hide();
    }
</script>
</body>
</html>
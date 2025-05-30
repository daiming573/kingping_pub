<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>金猪用户列表</title>
    <%@include file="/head.jsp" %>
    <script type="text/javascript" src="js/pages/formatter.js"></script>
    <script type="text/javascript" src="js/pages/bs_formatter.js"></script>
</head>
<body class="easyui-layout">
<div class="easyui-panel" data-options="region:'north'">
    <form id="searchForm" method="post">
        <table class="form-search">
            <tr>
                <td>unionId：</td>
                <td><input class="easyui-validatebox" type="text" name="unionId"></input></td>
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
			url:'king/user/findPageList.do'">
        <thead>
        <tr>
            <th data-options="field:'id',checkbox:true"></th>
            <th data-options="field:'userId',width:120">userId</th>
            <th data-options="field:'unionId',width:120">unionId</th>
            <th data-options="field:'openId',width:110">openId</th>
            <th data-options="field:'userType',width:110,formatter:formatUserType">用户类型</th>
            <th data-options="field:'nickName',width:110">昵称</th>
            <th data-options="field:'familyId',width:110">familyId</th>
            <th data-options="field:'mobile',width:110">手机号</th>
            <th data-options="field:'inviteCode',width:110">邀请码</th>
            <th data-options="field:'createTime',width:150">创建时间</th>
            <th data-options="field:'updateTime',width:150">更新时间</th>

        </tr>
        </thead>
    </table>
</div>
<script type="text/javascript">
    // var addBtn = {text: '新增', iconCls: 'icon-add', id: 'addBtn'};
    var updateBtn = {text: '修改', iconCls: 'form-edit', id: 'updateBtn'};
    var deleteBtn = {text: '删除', iconCls: 'form-delete', id: 'deleteBtn'};
    var viewBtn = {text: '查看', iconCls: 'form-view', id: 'viewBtn'};
    var childUserBtn = {text: '孩子列表', iconCls: 'form-config', id: 'childUserBtn'};
    var toolbar = [updateBtn, viewBtn];

    $(function () {
        tab_mask_hide();
        form_search($('#searchBtn'), 'searchForm', 'dg');
        form_clear($('#clearBtn'), 'searchForm');
        //form_add($("#addBtn"), 'king/user/goAdd.do');
        form_update($("#updateBtn"), 'king/user/goEdit.do', 'dg');
        // form_delete($("#deleteBtn"), 'king/user/doDelete.do', 'dg');
        form_view($("#viewBtn"), 'king/user/goView.do', 'dg');
        // form_view($("#childUserBtn"), 'king/child/goMain.do', 'dg', 'form-config', 1, "familyId");
    });

    function bsMainLoad() {
        $("#dg").datagrid('reload');
        tab_mask_hide();
    }

    function formatUserType(value, row, index) {
        if ("F" == value) {
            return '<font color="green">爸爸</font>';
        } else if ("M" == value) {
            return '<font color="red">妈妈</font>';
        } else {
            return '未知';
        }
    }

</script>
</body>
</html>
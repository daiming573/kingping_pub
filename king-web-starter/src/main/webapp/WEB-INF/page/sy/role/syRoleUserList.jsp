<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>角色用户列表</title>
    <%@include file="/head.jsp" %>
    <script type="text/javascript" src="js/pages/formatter.js"></script>
    <script type="text/javascript" src="js/pages/openSelectWin.js"></script>
</head>
<body class="easyui-layout">
<div class="easyui-panel" data-options="region:'north'">
    <form id="searchForm" method="post">
        <table class="form-search">
            <tr>
                <td>用户名称：</td>
                <td><select id="userComboGrid" name="userId" style="width: 225px;"></select></td>
                <td class="s_button_left">
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'icon-search',toggle:true" id="searchBtn">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'icon-cancel',toggle:true" id="clearBtn">清除</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
                       id="closeButton">关闭</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- multiple: true, 多选-->
<div data-options="region:'center'" style="border:0px;">
    <table id="roleUserDg" class="easyui-datagrid"
           data-options="rownumbers:true,
			fit:true,
			border:true,
			pagination:true,
			toolbar:toolbar,
			fitColumns: true,
			pageSize: 20,
			iconCls:'form-list',
			onClickRow: onClickRow,
			singleSelect:false,
			url:'sy/RoleUser/findPageList.do?roleId=${id}'">
        <thead>
        <tr>
            <th data-options="field:'id',checkbox:true"></th>
            <th data-options="field:'userName',width:110">用户账号</th>
            <th data-options="field:'name',width:110">用户姓名</th>
            <th data-options="field:'roleLabel',width:160">角色名称</th>
            <th data-options="field:'createTime',width:110">创建时间</th>
        </tr>
        </thead>
    </table>
</div>
<div id="roleWin"></div>
<script type="text/javascript">
    var addUserBtn = {text: '绑定用户', iconCls: 'icon-add', id: 'addUserBtn'};
    var delUserBtn = {text: '解除用户', iconCls: 'form-delete', id: 'delUserBtn'};
    var toolbar = [addUserBtn, '-', delUserBtn];
    var roleId = "${id}";
    $(function () {
        tab_mask_hide();
        form_search($('#searchBtn'), 'searchForm', 'roleUserDg');
        form_clear($('#clearBtn'), 'searchForm');
        form_combogridUser_list($('#userComboGrid'), 'sy/user/findPageList.do', '用户编号', '用户名称');
        $("#addUserBtn").click(function () {
            if (this.disabled == true) {
                return;
            }
            var title = this.text;
            if (undefined == title) {//IE8
                title = this.innerText;
            }
            openSelectWin();
        });

        form_delete($("#delUserBtn"), 'sy/RoleUser/doDelete.do', 'roleUserDg');
        form_close_tab($('#closeButton'));

    });

    //打开选择窗口
    function openSelectWin() {
        var w = $('#roleWin');
        if (w[0].childNodes.length > 0) {
            return w.window('open');
        }
        w.window({
            title: '角色选择',
            modal: true,
            minimizable: false,
            cache: false,
            width: 600,
            height: 400
        });
        // 创建datagrid
        var grid = $("<table/>");
        // 创建toolbar
        var tb = $("<div/>");
        grid.appendTo(w);
        tb.appendTo(w);
        // 生成toolbar
        bulidToolBar(tb, w, grid, '用户选择', addRoleUser);
        var thFields = [[
            {field: 'id', title: '编号', width: 60},
            {field: 'userName', title: '用户名称', width: 400},
        ]];
        // 生成datagrid
        buildDatagrid(grid, tb, 'sy/user/findPageList.do', thFields);
        $.parser.parse(w);
    }

    function bsMainLoad() {
        $("#roleUserDg").datagrid('reload');
        tab_mask_hide();
    }

    function addRoleUser(w, row) {
        if (undefined == row || null == row) {
            $.messager.alert('提示信息', '请选择记录');
            return;
        }
        // lovType分为O组织，R角色，V查看,D驾校
        var userId = row.id;
        $.ajax({
            type: "post",
            url: "sy/RoleUser/doSave.do",
            data: {userId: userId, roleId: roleId},
            success: function (data) {
                var dt = toJson(data);
                if (true == dt.success) {
                    w.window('close');
                    bsMainLoad();
                } else {
                    $.messager.alert('提示信息', dt.note);
                }
            }
        });

    }

    function form_combogridUser_list(o, url, idTitle, labelTitle) {
        var fields = {idField: 'id', textField: 'userName'};
        var columns = [
            {field: 'id', title: idTitle, width: 30},
            {field: 'userName', title: labelTitle, width: 80}
        ];
        form_combogrid_list_impl(o, url, fields, columns);
    }
</script>
</body>
</html>
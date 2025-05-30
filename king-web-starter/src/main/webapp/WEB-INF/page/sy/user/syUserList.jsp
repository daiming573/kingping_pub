<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>用户列表</title>
    <%@include file="/head.jsp" %>
    <script type="text/javascript" src="js/pages/formatter.js"></script>
    <script type="text/javascript" src="js/pages/bs_formatter.js"></script>
</head>
<body class="easyui-layout">
<div class="easyui-panel" data-options="region:'north'">
    <form id="searchForm" method="post">
        <table class="form-search">
            <tr>
                <td>用户名：</td>
                <td><input class="easyui-validatebox" type="text" name="userName"></input></td>
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
			url:'sy/user/findPageList.do'">
        <thead>
        <tr>
            <th data-options="field:'id',checkbox:true"></th>
            <th data-options="field:'userName',width:120">用户名</th>
            <th data-options="field:'name',width:120">真实姓名</th>
            <th data-options="field:'status',width:100,formatter:formatStatus">状态</th>
            <th data-options="field:'mobile',width:120">手机号码</th>
            <th data-options="field:'sexCd',width:110,formatter:formatSex">性别</th>
            <th data-options="field:'idNumber',width:110">证件号码</th>
            <th data-options="field:'createTime',width:110">创建时间</th>

        </tr>
        </thead>
    </table>
</div>
<script type="text/javascript">
    var resetBtn = {text: '重置密码', iconCls: 'icon-edit', id: 'resetBtn'};
    var btn = new Array();
    btn.push(resetBtn);
    var toolbar = form_toolbar(btn);

    $(function () {
        tab_mask_hide();
        form_search($('#searchBtn'), 'searchForm', 'dg');
        form_clear($('#clearBtn'), 'searchForm');
        form_add($("#addBtn"), 'sy/user/goAdd.do');
        form_update($("#updateBtn"), 'sy/user/goEdit.do', 'dg');
        form_delete($("#deleteBtn"), 'sy/user/doDelete.do', 'dg');
        form_view($("#viewBtn"), 'sy/user/goView.do', 'dg');
        form_resetPassword($("#resetBtn"), "sy/user/resetPassword.do");
        //form_view($("#cfgRoleUserBtn"), 'sy/RoleUser/goLov.do', 'dg', 'form-config', null, "id,code,label");
    });

    function bsMainLoad() {
        $("#dg").datagrid('reload');
        tab_mask_hide();
    }

    function form_resetPassword(o, url) {
        o.click(function () {
            if (this.disabled == true) {
                return;
            }
            var row = grid_select_record("dg");
            if (row == undefined) {
                $.messager.alert('提示信息', '请选择需要重置密码的记录');
                return false;
            }
            var id = row.id;
            if (window.confirm('确定重置密码？')) {
                $.ajax({
                    type: "post",
                    url: url,
                    dataType: "json",
                    data: {"id": id},
                    success: function (data) {
                        $.messager.alert('提示信息', data.note);
                    }, error: function (message) {
                        $.messager.alert('提示信息', '处理失败');
                    }
                });
            }
        });
    }
</script>
</body>
</html>
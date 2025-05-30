<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>微信字典配置列表</title>
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
			url:'wx/env/findPageList.do'">
        <thead>
        <tr>
            <th data-options="field:'id',checkbox:true"></th>
            <th data-options="field:'envKey',width:120">变量key</th>
            <th data-options="field:'envValue',width:120">变量值</th>
            <th data-options="field:'invalidTime',width:110">过期时间</th>
            <th data-options="field:'createTime',width:110">创建时间</th>
            <th data-options="field:'updateTime',width:110">更新时间</th>

        </tr>
        </thead>
    </table>
</div>
<script type="text/javascript">
    var toolbar = form_toolbar();

    $(function () {
        tab_mask_hide();
        form_search($('#searchBtn'), 'searchForm', 'dg');
        form_clear($('#clearBtn'), 'searchForm');
        form_add($("#addBtn"), 'wx/env/goAdd.do');
        form_update($("#updateBtn"), 'wx/env/goEdit.do', 'dg');
        form_delete($("#deleteBtn"), 'wx/env/doDelete.do', 'dg');
        form_view($("#viewBtn"), 'wx/env/goView.do', 'dg');
    });

    function bsMainLoad() {
        $("#dg").datagrid('reload');
        tab_mask_hide();
    }

</script>
</body>
</html>
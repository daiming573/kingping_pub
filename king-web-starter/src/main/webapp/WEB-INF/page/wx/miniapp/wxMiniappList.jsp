<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>微信小程序列表</title>
    <%@include file="/head.jsp" %>
    <script type="text/javascript" src="js/pages/formatter.js"></script>
    <script type="text/javascript" src="js/pages/bs_formatter.js"></script>
</head>
<body class="easyui-layout">
<div class="easyui-panel" data-options="region:'north'">
    <form id="searchForm" method="post">
        <table class="form-search">
            <tr>
                <td>小程序名称：</td>
                <td><input class="easyui-validatebox" type="text" name="miniappName"/></td>
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
			url:'wx/miniapp/findPageList.do'">
        <thead>
        <tr>
            <th data-options="field:'id',checkbox:true"></th>
            <th data-options="field:'miniappCode',width:120">小程序编号</th>
            <th data-options="field:'miniappName',width:120">小程序名称</th>
            <th data-options="field:'appId',width:110">appId</th>
            <th data-options="field:'appSecret',width:110">小程序密钥</th>
            <th data-options="field:'status',width:110,formatter:formatOnOrOff">状态</th>
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
        form_add($("#addBtn"), 'wx/miniapp/goAdd.do');
        form_update($("#updateBtn"), 'wx/miniapp/goEdit.do', 'dg');
        form_delete($("#deleteBtn"), 'wx/miniapp/doDelete.do', 'dg');
        form_view($("#viewBtn"), 'wx/miniapp/goView.do', 'dg');
    });

    function bsMainLoad() {
        $("#dg").datagrid('reload');
        tab_mask_hide();
    }

</script>
</body>
</html>
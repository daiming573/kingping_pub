<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>配置分组定义</title>
    <%@include file="/head.jsp" %>
</head>
<body class="easyui-layout">
<div class="easyui-panel" data-options="region:'north'">
    <form id="searchForm" method="post">
        <table class="form-search">
            <tr>
                <td style="width:10%">组英文名：</td>
                <td style="width:23%"><input class="easyui-validatebox" type="text" name="name"
                                             style="width:90%"></input></td>
                <td style="width:10%">组文名称：</td>
                <td style="width:23%"><input class="easyui-validatebox" type="text" name="label"
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
    <table id="syConfigGroupDg" class="easyui-datagrid"
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
			url:'sy/ConfigGroup/findPageList.do'">
        <thead>
        <tr>
            <th data-options="field:'id',checkbox:true"></th>
            <th data-options="field:'name',width:140">组英文名</th>
            <th data-options="field:'label',width:140">组文名称</th>
            <!-- <th data-options="field:'createDate',width:110">创建时间</th> -->
            <th data-options="field:'createUserLabel',width:60">创建者</th>
            <th data-options="field:'updateDate',width:110">更新时间</th>
            <th data-options="field:'updateUserLabel',width:60">更新者</th>
        </tr>
        </thead>
    </table>
</div>
<script type="text/javascript">
    var toolbar = form_toolbar(null);
    $(function () {
        tab_mask_hide();
        form_search($('#searchBtn'), 'searchForm', 'syConfigGroupDg');
        form_clear($('#clearBtn'), 'searchForm');
        form_add($("#addBtn"), 'sy/ConfigGroup/goAdd.do');
        form_update($("#updateBtn"), 'sy/ConfigGroup/goEdit.do', 'syConfigGroupDg');
        form_delete($("#deleteBtn"), 'sy/ConfigGroup/doDelete.do', 'syConfigGroupDg');
        form_view($("#viewBtn"), 'sy/ConfigGroup/goView.do', 'syConfigGroupDg');
    });

    function bsMainLoad() {
        $("#syConfigGroupDg").datagrid('reload');
        tab_mask_hide();
    }
</script>
</body>
</html>
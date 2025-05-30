<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>配置数据</title>
    <%@include file="/head.jsp" %>
    <script type="text/javascript" src="js/pages/formatter.js"></script>
</head>
<body class="easyui-layout">
<div class="easyui-panel" data-options="region:'north'">
    <input id="configId" type="hidden" value="${id}"/>
    <input id="configName" type="hidden" value="${name}"/>
    <form id="searchForm" method="post">
        <table class="form-search">
            <tr>
                <td style="width:10%;">配置名称：</td>
                <td style="width:23%;"><input class="easyui-validatebox" type="text" id="configLabel" style="width:90%;"
                                              disabled></input></td>
                <td style="width:10%;">中文名称：</td>
                <td style="width:23%;"><input class="easyui-validatebox" type="text" name="label"
                                              style="width:90%;"></input></td>
                <td style="width:33%;" class="s_button_left">
                    <a href="javascript:void(0);" class="easyui-linkbutton c9" id="searchBtn">查询</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- multiple: true, 多选-->
<div data-options="region:'center'">
    <table id="syConfigDataDg" class="easyui-datagrid"
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
			url:'sy/ConfigData/findPageList.do?configId=${id}'">
        <thead>
        <tr>
            <th data-options="field:'id',checkbox:true"></th>
            <th data-options="field:'name',width:80">英文名称</th>
            <th data-options="field:'label',width:80">中文名称</th>
            <th data-options="field:'dataTypeCd',width:40,formatter:getListCodeDataType">数据类型</th>
            <th data-options="field:'dataValue',width:200">数据内容</th>
            <th data-options="field:'dispOrder',width:40">显示顺序</th>
            <th data-options="field:'updateDate',width:100">更新时间</th>
            <th data-options="field:'updateUserLabel',width:100">更新者</th>
        </tr>
        </thead>
    </table>
</div>
<script type="text/javascript">
    var delCacheBtn = {text: '删除缓存', iconCls: 'form-delete', id: 'delCacheBtn'};
    var buttons = [delCacheBtn];
    var toolbar = form_toolbar(buttons);
    $(function () {
        tab_mask_hide();
        var row = grid_select_record('syConfigDg', parent[0]);
        $('#configLabel').val(row.label);
        form_search($('#searchBtn'), 'searchForm', 'syConfigDataDg');
        form_add($("#addBtn"), 'sy/ConfigData/goAdd.do', 2);
        form_update($("#updateBtn"), 'sy/ConfigData/goEdit.do', 'syConfigDataDg', 2);
        form_delete($("#deleteBtn"), 'sy/ConfigData/doDelete.do', 'syConfigDataDg');
        form_view($("#viewBtn"), 'sy/ConfigData/goView.do', 'syConfigDataDg', null, 2);
        $('#delCacheBtn').click(function () {
            if (this.disabled == true) {
                return;
            }
            var row = grid_select_record('syConfigDataDg');
            if (undefined == row || null == row) {
                $.messager.alert('提示信息', '请选择记录');
                return;
            }
            var data = {'code': row.name};
            form_ajax('sy/ConfigData/doDeleteCache.do', data, null, -1);
        });
    });

    function bsMainLoad() {
        $("#syConfigDataDg").datagrid('reload');
        tab_mask_hide();
    }
</script>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>配置列表</title>
    <%@include file="/head.jsp" %>
</head>
<body class="easyui-layout">
<div class="easyui-panel" data-options="region:'north'">
    <form id="searchForm" method="post">
        <table class="form-search">
            <tr>
                <td style="width:10%;">英文名称：</td>
                <td style="width:23%;"><input class="easyui-validatebox" type="text" name="name"
                                              style="width:90%;"></input></td>
                <td style="width:10%;">中文名称：</td>
                <td style="width:23%;"><input class="easyui-validatebox" type="text" name="label"
                                              style="width:90%;"></input></td>
                <td style="width:33%;" class="s_button_left">
                    <a href="javascript:void(0);" class="easyui-linkbutton c9" id="searchBtn">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton c5" id="clearBtn">清除</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- multiple: true, 多选-->
<div data-options="region:'center'">
    <table id="syConfigDg" class="easyui-datagrid"
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
			url:'sy/config/findPageList.do'">
        <thead>
        <tr>
            <th data-options="field:'id',checkbox:true"></th>
            <th data-options="field:'groupLabel',width:80">组名称</th>
            <th data-options="field:'name',width:120">英文名称</th>
            <th data-options="field:'label',width:140">中文名称</th>
            <!-- <th data-options="field:'createDate',width:110">创建时间</th> -->
            <th data-options="field:'createUserLabel',width:60">创建者</th>
            <th data-options="field:'updateDate',width:110">更新时间</th>
            <th data-options="field:'updateUserLabel',width:60">更新者</th>
        </tr>
        </thead>
    </table>
</div>
<script type="text/javascript">
    var cfgDataBtn = {text: '配置数据', iconCls: 'form-config', id: 'cfgDataBtn'};
    var updateCacheBtn = {text: '更新缓存', iconCls: 'form-exe', id: 'updateCacheBtn'};
    var buttons = [cfgDataBtn, updateCacheBtn];
    var toolbar = form_toolbar(buttons);
    $(function () {
        tab_mask_hide();
        form_search($('#searchBtn'), 'searchForm', 'syConfigDg');
        form_clear($('#clearBtn'), 'searchForm');
        form_add($("#addBtn"), 'sy/config/goAdd.do');
        form_update($("#updateBtn"), 'sy/config/goEdit.do', 'syConfigDg');
        form_delete($("#deleteBtn"), 'sy/config/doDelete.do', 'syConfigDg');
        form_view($("#viewBtn"), 'sy/config/goView.do', 'syConfigDg');
        form_view($("#cfgDataBtn"), 'sy/ConfigData/goMain.do', 'syConfigDg', 'form-config', null, "id,name");
        $('#updateCacheBtn').click(function () {
            if (this.disabled == true) {
                return;
            }
            var row = grid_select_record('syConfigDg');
            if (undefined == row || null == row) {
                $.messager.alert('提示信息', '请选择记录');
                return;
            }
            var data = {'code': row.name};
            form_ajax('sy/config/updateConfigCache.do', data, null, -1);
        });

    });

    function bsMainLoad() {
        $("#syConfigDg").datagrid('reload');
        tab_mask_hide();
    }
</script>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>金猪用户孩子列表</title>
    <%@include file="/head.jsp" %>
    <script type="text/javascript" src="js/pages/formatter.js"></script>
    <script type="text/javascript" src="js/pages/bs_formatter.js"></script>
</head>
<body class="easyui-layout">
<div class="easyui-panel" data-options="region:'north'">
    <form id="searchForm" method="post">
        <table class="form-search">
            <tr>
                <td>昵称：</td>
                <td><input class="easyui-validatebox" type="text" name="nickName"></input></td>
                <td class="s_button_left">
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'icon-search',toggle:true" id="searchBtn">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'icon-cancel',toggle:true" id="clearBtn">清除</a>
<%--                    <a id="closeBtn" href="javascript:void(0)" class="easyui-linkbutton"--%>
<%--                       data-options="toggle:true,iconCls:'icon-cancel'" >关闭</a>--%>
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
			url:'king/child/findPageList.do?familyId=${familyId}'">
        <thead>
        <tr>
            <th data-options="field:'id',checkbox:true"></th>
            <th data-options="field:'nickName',width:120">昵称</th>
            <th data-options="field:'sex',width:110,formatter:formatSex">性别</th>
            <th data-options="field:'birthday',width:110">生日</th>
            <th data-options="field:'accountTotal',width:110">账户</th>
            <th data-options="field:'createTime',width:110">创建时间</th>
            <th data-options="field:'updateTime',width:110">更新时间</th>

        </tr>
        </thead>
    </table>
</div>
<script type="text/javascript">
    // var addBtn = {text: '新增', iconCls: 'icon-add', id: 'addBtn'};
    var updateBtn = {text: '修改', iconCls: 'form-edit', id: 'updateBtn'};
    var deleteBtn = {text: '删除', iconCls: 'form-delete', id: 'deleteBtn'};
    var viewBtn = {text: '查看', iconCls: 'form-view', id: 'viewBtn'};
    var accountRecordBtn = {text: '账户记录', iconCls: 'form-config', id: 'accountRecordBtn'};
    var toolbar = [updateBtn, viewBtn];

    $(function () {
        tab_mask_hide();
        <%--form_search_param($('#searchBtn'), 'searchForm', 'dg', '${familyId}');--%>
        form_clear($('#clearBtn'), 'searchForm');
        // form_close_tab($('#closeBtn'));
        form_search($('#searchBtn'), 'searchForm', 'dg');

        //form_add($("#addBtn"), 'king/child/goAdd.do');
        form_update($("#updateBtn"), 'king/child/goEdit.do', 'dg');
        // form_delete($("#deleteBtn"), 'king/child/doDelete.do', 'dg');
        form_view($("#viewBtn"), 'king/child/goView.do', 'dg');
        // form_view($("#accountRecordBtn"), 'king/record/goMain.do', 'dg', 'form-config', null, "id");
    });

    //搜索条件查询
    // function form_search_param(o, formId, gridId, param) {
    //     o.click(function(){
    //         var queryParams = $('#'+formId).form('getJsonPack');
    //         queryParams.familyId = param;
    //         var g = $('#'+gridId);
    //         g.datagrid('options').queryParams = queryParams;
    //         grid_reload(gridId);
    //     });
    // }

    function bsMainLoad() {
        $("#dg").datagrid('reload');
        tab_mask_hide();
    }

</script>
</body>
</html>
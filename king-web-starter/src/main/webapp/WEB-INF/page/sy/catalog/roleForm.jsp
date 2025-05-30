<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>角色目录</title>
    <%@include file="/head.jsp" %>
    <script type="text/javascript" src="js/pages/formatter.js"></script>
</head>
<body class="easyui-layout">
<!-- multiple: true, 多选-->
<div data-options="region:'west',split:true" style="width:400px;">
    <table id="roledg" class="easyui-datagrid"
           data-options="
			fit:true,
			border:false,
			pagination:true,
			fitColumns: true,
			pageSize: 20,
			iconCls:'form-list',
			onClickRow: onClickRole,
			singleSelect:true,
			url:'sy/role/findPageList.do'">
        <thead>
        <tr>
            <th data-options="field:'id'">角色编号</th>
            <th data-options="field:'code',width:80">角色代码</th>
            <th data-options="field:'label',width:80">中文名字</th>
        </tr>
        </thead>
    </table>
</div>
<div data-options="region:'center'">
    <div id="syCatalogRoleTabs" class="easyui-tabs" data-options="fit:true,border:false"></div>
</div>
<script type="text/javascript">

    $(function () {
        tab_mask_hide();
    });

    function onClickRole(rowIndex, rowData) {
        var url = 'sy/CatalogRole/goRoleCatalog.do?operate=U&roleId=' + rowData.id;
        var node = {
            "iconCls": 'form-list',
            "url": url,
            "text": '角色目录',
            "id": rowData.id
        };
        node.width = 150;
        $('#syCatalogRoleTabs').tabs('closeTabs');
        $('#syCatalogRoleTabs').tabs('addTab', node);
    }
</script>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>表单权限</title>
    <%@include file="/head.jsp" %>
</head>
<body class="easyui-layout">
<!-- multiple: true, 多选-->
<div data-options="region:'west',split:true" title="" style="width:200px;">
    <!-- <div style="margin:2px 2px 2px 2px;">
        <input class="easyui-textbox" id="searchText" data-options="prompt:'Search...',icons:[{iconCls:'icon-search',handler: onsearch}]" style="width:190px;">
    </div> -->
    <div class="easyui-accordion" data-options="fit:true,border:false">
        <ul id="syCatalogTree" data-options="animate:true,url:'sy/catalog/findCatalogTree.do',method:'get'">
        </ul>
    </div>
</div>
<div data-options="region:'center'">
    <div id="syFormTabs" class="easyui-tabs" data-options="fit:true,border:false"></div>
</div>
<script type="text/javascript">
    $(function () {
        tab_mask_hide();
        //处理树点击添加tab事件
        var syCatalogList = {
            "iconCls": 'form-form',
            "url": 'sy/catalog/goAdd.do',
            "text": '表单列表',
            'id': 'syCatalog',
            'width': 150
        };
        $('#syCatalogTabs').tabs('addTab', syCatalogList);
        $('#syCatalogTree').tree({
            onClick: function (node) {
                //if('N' == node.hasChild){
                node.url = 'sy/form/goPageList.do?catalogNodeId=' + node.id;
                //}else{
                //return;
                //}
                node.width = 150;
                $('#syFormTabs').tabs('closeTabs');
                $('#syFormTabs').tabs('addTab', node);
            },
            onBeforeExpand: function (node) {
                $(this).tree('options').url = 'sy/catalog/findCatalogTreeNode.do?linkCatalog=' + node.code;
            }
        });
        /**
         $('#searchButton').click(function(){
		var txt = $('#searchText').val();
		$('#syCatalogTree').tree('reload',{label: txt}); 
	});**/
    });

    function onsearch() {

    }
</script>
</body>
</html>
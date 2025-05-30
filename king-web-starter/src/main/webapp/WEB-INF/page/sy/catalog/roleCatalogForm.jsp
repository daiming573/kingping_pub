<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>角色目录</title>
    <%@include file="/head.jsp" %>
    <script type="text/javascript" src="js/pages/formatter.js"></script>
    <style type="text/css">
        .accordion {
            background-color: #ffffff;
            overflow: hidden;
        }

        .panel-header {
            background-color: #ffffff;
            color: #000000;
        }

        .panel-title {
            font-size: 12px;
            font-weight: bold;
            color: #000000;
            height: 20px;
            line-height: 20px;
        }

        .tree-title {
            color: #000000;
        }

        .tree-node-hover {
            background: #e2e2e2;
            color: #000000;
        }
    </style>
</head>
<body class="easyui-layout">
<!-- multiple: true, 多选-->
<div data-options="region:'center',split:false">
    <div class="easyui-panel form-tool" data-options="border:false">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'"
           id="submitButton">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'"
           id="expandAllButton">展开</a>
        <form id="syCatalogRoleForm" method="post">
            <input name="id" type="hidden" value="${id}"/>
            <input name="isValid" type="hidden" value="Y"/>
            <input name="roleId" type="hidden" value="${roleId}"/>
            <input id="nodeIds" name="nodeIds" type="hidden"/>
            <input id="powerNodeIds" name="powerNodeIds" type="hidden"/>
        </form>
    </div>
    <div class="easyui-accordion" data-options="border:false">
        <ul id="syCatalogTree" class="easyui-tree"></ul>
    </div>
</div>
<script type="text/javascript">

    $(function () {
        var roleId = '${roleId}';
        $('#syCatalogTree').tree({
            url: 'sy/catalogNode/findAllTree.do?rootId=0&roleId=' + roleId,
            method: 'get',
            cascadeCheck: true,
            lines: true,
            animate: false,
            checkbox: true
        });

        var _syCatalogTabs = parent.$('#syCatalogRoleTabs');
        _syCatalogTabs.mask("hide");

        //处理树点击添加tab事件
        $('#expandAllButton').click(function () {
            var node = $('#syCatalogTree').tree('getSelected');
            if (node) {
                $('#syCatalogTree').tree('expandAll', node.target);
            } else {
                $('#syCatalogTree').tree('expandAll');
            }
        });

        $('#submitButton').click(function () {
            if (this.disabled == true) {
                return;
            }
            var nodes = $('#syCatalogTree').tree('getChecked', 'indeterminate');
            var s = '';
            var p = '';
            for (var i = 0; i < nodes.length; i++) {
                if (p != '') p += ',';
                p += nodes[i].id;
            }
            nodes = $('#syCatalogTree').tree('getChecked');
            for (var i = 0; i < nodes.length; i++) {
                if (s != '') s += ',';
                s += nodes[i].id;
            }
            $('#nodeIds').val(s);
            $('#powerNodeIds').val(p + ',' + s);
            form_submit_impl('syCatalogRoleForm', 'sy/CatalogRole/doSave.do', null, 'M');
        });
    });
</script>
</body>
</html>
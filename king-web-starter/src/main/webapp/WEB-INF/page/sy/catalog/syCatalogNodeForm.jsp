<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>系统目录表单</title>
    <%@include file="/head.jsp" %>
    <script type="text/javascript" src="js/pages/formatter.js"></script>
</head>
<body class="easyui-layout">
<div class="easyui-panel form-tool" data-options="region:'north'">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'"
       id="submitButton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'"
       id="addNodeButton">新建子菜单</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'form-delete'"
       id="deleteNodeButton">删除</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'"
       id="closeButton">关闭</a>
</div>

<div class="easyui-panel" data-options="region:'center',border:true,fit:false">
    <form id="syCatalogNodeForm" method="post">
        <input type="hidden" id="id" name="id"/>
        <input type="hidden" name="operationType" value="NODE"/>
        <table class="form-table">
            <tr>
                <td>
                    <div>父编号：</div>
                    <input class="easyui-validatebox" type="text" name="parentId" value="${parentId}"
                           data-options="required:true"/></td>
                <td>
                    <div>是否有子：</div>
                    <select class="easyui-combobox" id="hasChild" name="hasChild" style="width:51%;"
                            data-options="editable:false">
                        <option value="Y">有</option>
                        <option value="N">无</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <div>目录类型：</div>
                    <select class="easyui-combobox" name="typeName" style="width:51%;" data-options="editable:false">
                        <option value="MENU">菜单</option>
                        <option value="SUBMENU">子菜单</option>
                    </select>
                </td>
                <td>
                    <div>有效性：</div>
                    <select class="easyui-combobox" name="isValid" style="width:51%;" data-options="editable:false">
                        <option value="Y">有效</option>
                        <option value="N">无效</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <div>目录代码：</div>
                    <input class="easyui-validatebox" type="text" id="code" name="code" data-options="required:true"/>
                </td>
                <td>
                    <div>显示图标：</div>
                    <input class="easyui-combotree" name="iconName"
                           data-options="url:'sy/catalogNode/doGerIconJson.do',method:'get'" style="width:200px;">
            </tr>
            <tr>
                <td>
                    <div>中文名称：</div>
                    <input class="easyui-validatebox" type="text" id="label" name="label"
                           data-options="required:true"></input></td>
                <td>
                    <div>英文名称：</div>
                    <input class="easyui-validatebox" type="text" name="name" data-options="required:true"/></td>
            </tr>
            <tr>
                <td>
                    <div>关联目录：</div>
                    <input type="text" name="linkCatalog"></input></td>
                <td>
                    <div>排序：</div>
                    <input class="easyui-validatebox" type="text" name="dispOrder" data-options="required:true"/></td>
            </tr>
            <tr>
                <td>
                    <div>转向动作：</div>
                    <input type="text" name="doRedirect"></input></td>
                <td>
                    <div>调度动作：</div>
                    <input type="text" name="doInvoke"/></td>
            </tr>
            <tr>
                <td>
                    <div>外部目录：</div>
                    <input type="text" name="outerCatalog"></input></td>
                <td>
                    <div>介绍动作：</div>
                    <input type="text" name="doIntroduce"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <div>备注：</div>
                    <textarea name="note" style="width:76%;"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    $(function () {
        // 父页面TABS对象
        var _syCatalogTabs = parent.$('#syCatalogTabs');
        _syCatalogTabs.mask("hide");
        // tab中按键控制
        var index = tab_get_index_impl(_syCatalogTabs);
        if (index == 0) {
            $("#closeButton").hide();
        }
        if (index >= 1) {
            $("#addNodeButton").hide();
            $("#deleteNodeButton").hide();
        }
        // 操作
        var operate = '${operate}';
        if ('U' == operate || 'V' == operate || 'H' == operate) {
            $('#hideCreateLine').show();
            $('#hideUpdateLine').show();
            $('#syCatalogNodeForm').form('load', "sy/catalogNode/getCatalogTreeNode.do?id=${id}");
        }
        if ('V' == operate || 'H' == operate) {
            var permissionObj = ['deleteNodeButton', 'submitButton', 'addNodeButton'];
            form_toolbar_control(permissionObj, 'disable');
        }
        //form_combogrid_Iconlist($('#iconComboGrid'));
        $("#submitButton").click(function () {
            $('#syCatalogNodeForm').form('submit', {
                url: 'sy/catalogNode/doSaveCatalogNode.do',
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (data) {
                    var dt = toJson(data);
                    $.messager.alert('提示信息', dt.note);
                    if (true == dt.success || "true" == dt.success) {
                        var _tree = parent.$('#syCatalogTree');
                        var _node = _tree.tree('getSelected');
                        if ('A' == operate) {
                            _tree.tree('append', {
                                parent: (_node ? _node.target : null),
                                data: [{
                                    id: dt.id,
                                    text: $('#label').val(),
                                    code: $('#code').val(),
                                    iconCls: $('#iconName').val(),
                                    state: ''
                                }]
                            });
                        } else if ('U' == operate || null != _node) {
                            _tree.tree('update', {target: _node.target, text: $('#label').val()});
                            //var _tab = _syCatalogTabs.tabs('getTab', index);
                            //_syCatalogTabs.tabs('update', {tab: _tab, options: { title: $('#label').val()} });
                        }
                    }
                }, error: function (message) {
                    $.messager.alert('提示信息', '处理失败');
                }
            });
        });
        $("#addNodeButton").click(function () {
            var p = "?operate=A&parentId=" + $('#id').val() + "&catalogId=" + $('#catalogId').val();
            var syCatalogNode = {
                "iconCls": 'form-form',
                "url": 'sy/catalogNode/goSyCatalogNodeForm.do' + p,
                "text": '新建子节点',
                'id': 'syCatalogNewNode'
            };
            _syCatalogTabs.tabs('addTab', syCatalogNode);
        });
        $("#closeButton").click(function () {
            var _idx = tab_get_index_impl(_syCatalogTabs);
            var t = {"start": 1, "index": _idx};
            _syCatalogTabs.tabs('closeTabByAfter', t);
        });
        $("#deleteNodeButton").click(function () {
            if ('Y' == $('#hasChild').combobox('getValue')) {
                $.messager.alert('提示信息', '存在子节点，不能进行删除!');
                return;
            }
            $.messager.confirm('提示信息', '你确定删除?', function (isTrue) {
                if (isTrue) {
                    var id = $('#id').val();
                    $.ajax({
                        type: "post", url: "sy/catalogNode/doDeleteCatalogNode.do", data: "ids=" + id,
                        success: function (data) {
                            var dt = toJson(data);
                            $.messager.alert('提示信息', dt.note);
                            if (true == dt.success) {
                                var _tree = parent.$('#syCatalogTree');
                                var _node = _tree.tree('getSelected');
                                // 移除目录树节点
                                _tree.tree('remove', _node.target);
                                // 禁止操作控件
                                form_toolbar_control(['deleteNodeButton', 'submitButton', 'addNodeButton'], 'disable');
                                // 禁止页面控制
                                form_disable('syCatalogNodeForm', true);
                                // 关闭后面的tab
                                var t = {"start": 1, "index": 1};
                                _syCatalogTabs.tabs('closeTabByAfter', t);
                            }
                        }, error: function (message) {
                            $.messager.alert('提示信息', '处理失败');
                        }
                    });
                }
            });
        });
    });
</script>
</body>
</html>
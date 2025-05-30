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
</div>

<div class="easyui-panel" data-options="region:'center',border:true,fit:false">
    <form id="syCatalogForm" method="post">
        <input type="hidden" id="id" name="id"/>
        <table class="form-table">
            <tr>
                <td>
                    <div>父编号：</div>
                    <input class="easyui-validatebox" type="text" name="parentId" value="0"
                           data-options="required:true"/></td>
                <td>
                    <div>是否有子：</div>
                    <select class="easyui-combobox" name="hasChild" style="width:51%" data-options="editable:false">
                        <option value="Y">有</option>
                        <option value="N">无</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <div>目录类型：</div>
                    <select class="easyui-combobox" name="typeName" style="width:51%" data-options="editable:false">
                        <option value="MENU">菜单</option>
                        <option value="SUBMENU">子菜单</option>
                    </select>
                </td>
                <td>
                    <div>有效性：</div>
                    <select class="easyui-combobox" name="isValid" style="width:51%" data-options="editable:false">
                        <option value="Y">有效</option>
                        <option value="N">无效</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <div>目录代码：</div>
                    <input class="easyui-validatebox" type="text" name="code" data-options="required:true"/></td>
                <td>
                    <div>显示图标：</div>
                    <input class="easyui-combotree" name="iconName"
                           data-options="url:'sy/catalogNode/doGerIconJson.do',method:'get'" style="width:200px;">
                </td>
            </tr>
            <tr>
                <td>
                    <div>中文名称：</div>
                    <input class="easyui-validatebox" type="text" name="label" data-options="required:true"></input>
                </td>
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
                    <textarea name="note" style="width:76%"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    $(function () {
        var syCatalogTabs = parent.$('#syCatalogTabs');
        syCatalogTabs.mask("hide");
        var operate = '${operate}';
        if ('U' == operate || 'V' == operate || 'H' == operate) {
            $('#hideCreateLine').show();
            $('#hideUpdateLine').show();
        }
        if ('V' == operate || 'H' == operate) {
            var permissionObj = ['submitButton', 'clearButton'];
            form_toolbar_control(permissionObj, 'disable');
        }
        // 图标选择
        //form_combogrid_Iconlist($('#iconComboGrid'));
        $("#submitButton").click(function () {
            $('#syCatalogForm').form('submit', {
                url: 'sy/catalogNode/doSaveCatalogNode.do',
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (data) {
                    var dt = toJson(data);
                    $.messager.alert('提示信息', dt.note);

                }, error: function (message) {
                    $.messager.alert('提示信息', '处理失败');
                }
            });
        });

    });

    function ss(node) {

    }
</script>
</body>
</html>
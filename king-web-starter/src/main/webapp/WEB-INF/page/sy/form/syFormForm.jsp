<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>表单权限</title>
    <%@include file="/head.jsp" %>
</head>
<body class="easyui-layout">
<div class="easyui-panel form-tool" data-options="region:'north'">
    <a href="javascript:void(0)" class="easyui-linkbutton c9" id="submitButton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton c5" id="closeButton">关闭</a>
</div>
<div class="easyui-panel" data-options="region:'center',border:true,fit:false">
    <form id="syForm" method="post">
        <input type="hidden" id="catalogNodeId" name="catalogNodeId" value="${catalogNodeId}"/>
        <input name="id" type="hidden"/>
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
                    <div>节点类型：</div>
                    <select class="easyui-combobox" name="typeName" style="width:51%;" data-options="editable:false">
                        <option value="L">列表</option>
                        <option value="F">菜单</option>
                    </select>
                </td>
                <td>
                    <div>排序：</div>
                    <input class="easyui-validatebox" type="text" name="dispOrder" data-options="required:true"/></td>
            </tr>
            <tr>
                <td>
                    <div>表单编号：</div>
                    <input class="easyui-validatebox" type="text" name="code" data-options="required:true"/></td>
                <td>
                    <div>显示图标：</div>
                    <input class="easyui-validatebox" type="text" name="iconName" value="catalog-form"
                           data-options="required:true"/></td>
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
                    <div>转向动作：</div>
                    <input type="text" name="doRedirect"></input></td>
                <td>
                    <div>调度动作：</div>
                    <input type="text" name="doInvoke"/></td>
            </tr>
            <tr style="display: none;" id="hideCreateLine">
                <td>
                    <div>创建者：</div>
                    <input type="text" name="createUserLabel" disabled></input></td>
                <td>
                    <div>创建时间：</div>
                    <input class="easyui-validatebox" type="text" name="createDate" disabled/></td>
            </tr>
            <tr style="display: none;" id="hideUpdateLine">
                <td>
                    <div>更新者：</div>
                    <input type="text" name="updateUserLabel" disabled></input></td>
                <td>
                    <div>更新时间：</div>
                    <input class="easyui-validatebox" type="text" name="updateDate" disabled/></td>
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
        var _syFormTabs = parent.$('#syFormTabs');
        _syFormTabs.mask("hide");
        var operate = '${operate}';
        if ('U' == operate || 'V' == operate || 'H' == operate) {
            $('#hideCreateLine').show();
            $('#hideUpdateLine').show();
            var params = {'url': "sy/form/load.do", 'gridId': 'dg'};
            form_load('syForm', operate, params);
        }
        if ('V' == operate || 'H' == operate) {
            //clearButton
            var permissionObj = ['submitButton'];
            form_toolbar_control(permissionObj, 'hide');
        }
        $("#submitButton").click(function () {
            $('#syForm').form('submit', {
                url: 'sy/form/doSave.do',
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (data) {
                    var dt = toJson(data);
                    $.messager.alert('提示信息', dt.note, 'info', function () {
                        parent[0].bsMainLoad();
                        var index = tab_get_index_impl(_syFormTabs);
                        _syFormTabs.tabs('close', index);
                    });
                }, error: function (message) {
                    $.messager.alert('提示信息', '处理失败');
                }
            });
        });
        $("#closeButton").click(function () {
            var index = tab_get_index_impl(_syFormTabs);
            _syFormTabs.tabs('close', index);
        });

    });
</script>
</body>
</html>
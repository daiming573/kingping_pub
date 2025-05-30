<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>权限对象</title>
    <%@include file="/head.jsp" %>
</head>
<body class="easyui-layout">
<div class="easyui-panel form-tool" data-options="region:'north'">
    <a href="javascript:void(0)" class="easyui-linkbutton c9" id="submitButton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton c5" id="closeButton">关闭</a>
</div>
<div class="easyui-panel" data-options="region:'center',border:true,fit:false">
    <form id="syObjForm" method="post">
        <input type="hidden" id="formId" name="formId" value="${formId}"/>
        <input name="id" type="hidden"/>
        <table class="form-table">
            <tr>
                <td>
                    <div>类型：</div>
                    <select class="easyui-combobox" name="typeCd" style="width:51%;" data-options="editable:false">
                        <option value="B">按键</option>
                        <option value="L">列表</option>
                        <option value="T">文本</option>
                        <option value="N">数字</option>
                        <option value="A">区域</option>
                        <option value="D">日期</option>
                    </select>
                </td>
                <td>
                    <div>排序：</div>
                    <input class="easyui-validatebox" type="text" name="dispOrder" data-options="required:true"/></td>
            </tr>
            <tr>
                <td>
                    <div>代码：</div>
                    <input class="easyui-validatebox" type="text" name="code" data-options="required:true"/></td>
                <td>
                    <div>图标名称：</div>
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
                    <textarea name="note" style="width:76%"></textarea></td>
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
            var params = {'url': "sy/FormObj/load.do", 'gridId': 'dg', 'pKey': 'id', 'toTabIndex': '1'};
            form_load('syObjForm', operate, params);
        }
        if ('V' == operate || 'H' == operate) {
            //clearButton
            var permissionObj = ['submitButton'];
            form_toolbar_control(permissionObj, 'hide');
        }
        $("#submitButton").click(function () {
            $('#syObjForm').form('submit', {
                url: 'sy/FormObj/doSave.do',
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (data) {
                    var dt = toJson(data);
                    $.messager.alert('提示信息', dt.note, 'info', function () {
                        parent[1].bsMainLoad();
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
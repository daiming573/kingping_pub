<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>表单角色</title>
    <%@include file="/head.jsp" %>
</head>
<body class="easyui-layout">
<div class="easyui-panel form-tool" data-options="region:'north'">
    <a href="javascript:void(0)" class="easyui-linkbutton c9" id="submitButton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton c5" id="closeButton">关闭</a>
</div>
<div class="easyui-panel" data-options="region:'center',border:true,fit:false">
    <form id="syCatalogRoleForm" method="post">
        <input type="hidden" id="formId" name="formId" value="${formId}"/>
        <input name="id" type="hidden"/>
        <table class="form-table">
            <tr>
                <td>
                    <div>表单对象：</div>
                    <select id="objComboGrid" style="width:51%;"></select></td>
                <td>
                    <div>角色名称：</div>
                    <select id="roleComboGrid" name="roleId" style="width:51%;"></select></td>
            </tr>
            <tr>
                <td>
                    <div>有效性：</div>
                    <select class="easyui-combobox" name="isValid" style="width:51%;" data-options="editable:false">
                        <option value="Y">有效</option>
                        <option value="N">无效</option>
                    </select>
                </td>
                <td><input type="hidden" id="grantData" name="grantData"></input></td>
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
        form_multiple_combogrid_list($('#objComboGrid'), 'sy/FormObj/findPageList.do?formId=${formId}', '表单对象编号', '表单对象名称');
        form_combogrid_list($('#roleComboGrid'), 'pm/role/findPageList.do', '角色编号', '角色名称');
        var _syFormTabs = parent.$('#syFormTabs');
        _syFormTabs.mask("hide");
        var operate = '${operate}';
        if ('U' == operate || 'V' == operate || 'H' == operate) {
            $('#hideCreateLine').show();
            $('#hideUpdateLine').show();
            var params = {'url': "sy/FormRole/load.do", 'gridId': 'dg', 'pKey': 'id', 'toTabIndex': '1'};
            form_load('syCatalogRoleForm', operate, params);
            var g = $('#objComboGrid').combogrid('grid');
            $('#syCatalogRoleForm').form({
                onLoadSuccess: function (data) {
                    var val = data.grantData;
                    g.datagrid({
                        onLoadSuccess: function (data) {
                            var r = g.datagrid('getRows');
                            var v = [];
                            for (var i = 0; i < r.length; i++) {
                                if (val.indexOf(r[i].code) == -1) {
                                    v.push(r[i].code);
                                }
                            }
                            $('#objComboGrid').combogrid('setValues', v);
                        }
                    });
                }
            });

        }
        if ('V' == operate || 'H' == operate) {
            //clearButton
            var permissionObj = ['submitButton'];
            form_toolbar_control(permissionObj, 'hide');
        }
        $("#submitButton").click(function () {
            var g = $('#objComboGrid').combogrid('grid');
            var r = g.datagrid('getRows');
            var v = [];
            var val = $('#objComboGrid').combogrid('getValues');
            for (var i = 0; i < r.length; i++) {
                if (val.indexOf(r[i].code) == -1) {
                    v.push(r[i].code);
                }
            }
            $("#grantData").val(v);
            $('#syCatalogRoleForm').form('submit', {
                url: 'sy/FormRole/doSave.do',
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
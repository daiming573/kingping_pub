<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>角色表单</title>
    <%@include file="/head.jsp" %>
</head>
<body class="easyui-layout">
<div class="easyui-panel form-tool" data-options="region:'north'">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'"
       id="submitButton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'"
       id="closeButton">关闭</a>
</div>

<div class="easyui-panel" data-options="region:'center',border:true,fit:false">
    <form id="syRoleForm" method="post">
        <input name="id" type="hidden"/>
        <input name="uuid" type="hidden"/>
        <input name="isValid" type="hidden" value="Y"/>
        <table class="form-table">
            <tr>
                <td>
                    <div>代码：</div>
                    <input class="easyui-validatebox" type="text" name="code" data-options="required:true"/></td>
                <td>
                    <div>中文名字：</div>
                    <input class="easyui-validatebox" type="text" name="label" data-options="required:true"/></td>
            </tr>
            <tr>
                <td>
                    <div>英文名称：</div>
                    <input class="easyui-validatebox" type="text" name="name"/></td>
                <td>
                    <div>图标名称：</div>
                    <input class="easyui-validatebox" name="iconName" type="text"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <div>备注：</div>
                    <textarea name="note"></textarea></td>
            </tr>
            <tr style="display: none;" id="hideCreateLine">
                <td>
                    <div>创建时间：</div>
                    <input class="easyui-validatebox" type="text" name="createTime" disabled/></td>
            </tr>

        </table>
    </form>
</div>
<script type="text/javascript">
    $(function () {
        var operate = '${operate}';
        if ('U' == operate || 'V' == operate || 'H' == operate) {
            $('#hideCreateLine').show();
            $('#hideUpdateLine').show();
        }
        if ('V' == operate || 'H' == operate) {
            var permissionObj = ['submitButton'];
            form_toolbar_control(permissionObj, 'hide');
        }
        //对象{'url':访问地址,'gridId':列表对象ID,'pKey':记录主键字段, 'toTabIndex':转向至tabs索引数的页面（从0开始)}
        var params = {'url': "sy/role/load.do", 'gridId': 'dg'};
        form_load('syRoleForm', operate, params);
        tab_mask_hide();
        form_submit($("#submitButton"), 'syRoleForm', 'sy/role/doSave.do');
        form_close_tab($('#closeButton'));

    });
</script>
</body>
</html>
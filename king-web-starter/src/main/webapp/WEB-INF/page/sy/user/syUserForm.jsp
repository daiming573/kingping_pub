<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户表单</title>
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
    <form id="syUserForm" method="post">
        <input name="id" type="hidden"/>
        <table class="form-table">
            <tr>
                <td>
                    <div>用户名：</div>
                    <input class="easyui-validatebox" type="text" name="userName" data-options="required:true"/></td>
                <td>
                    <div>真实姓名：</div>
                    <input class="easyui-validatebox" type="text" name="name" data-options="required:true"/></td>
            </tr>
            <tr>
                <td>
                    <div>密码：</div>
                    <input id="password" class="easyui-validatebox" type="password" name="password"/></td>
                <td>
                    <div>状态：</div>
                    <select class="easyui-combobox" name="status" style="width:225px;">
                        <option value="1">启用</option>
                        <option value="2">禁用</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <div>手机号：</div>
                    <input class="easyui-validatebox" type="text" name="mobile"/></td>
                <td>
                    <div>身份证：</div>
                    <input class="easyui-validatebox" type="text" name="idNumber"/></td>
            </tr>
            <tr>
                <td>
                    <div>出生日期：</div>
                    <input class="easyui-datebox" type="text" name="birthday"/></td>
                <td>
                    <div>性别：</div>
                    <select class="easyui-combobox" name="sexCd" style="width:225px;">
                        <option value="M">男</option>
                        <option value="F">女</option>
                    </select>
                </td>
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
            $('#password').attr("disabled", "disabled");
        }
        if ('V' == operate || 'H' == operate) {
            $('#password').attr("disabled", "disabled");
            var permissionObj = ['submitButton'];
            form_toolbar_control(permissionObj, 'hide');
        }
        //对象{'url':访问地址,'gridId':列表对象ID,'pKey':记录主键字段, 'toTabIndex':转向至tabs索引数的页面（从0开始)}
        var params = {'url': "sy/user/load.do", 'gridId': 'dg'};
        form_load('syUserForm', operate, params);
        tab_mask_hide();
        form_submit($("#submitButton"), 'syUserForm', 'sy/user/doSave.do');
        form_close_tab($('#closeButton'));

    });
</script>
</body>
</html>
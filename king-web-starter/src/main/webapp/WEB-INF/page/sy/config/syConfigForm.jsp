<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>配置表单</title>
    <%@include file="/head.jsp" %>
</head>
<body class="easyui-layout">
<div class="easyui-panel form-tool" data-options="region:'north'">
    <a href="javascript:void(0)" class="easyui-linkbutton c9" id="submitButton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton c5" id="closeButton">关闭</a>
</div>

<div class="easyui-panel" data-options="region:'center',border:true,fit:false">
    <form id="syConfigForm" method="post">
        <input name="id" type="hidden"/>
        <input name="over" type="hidden"/>
        <table class="form-table">
            <tr>
                <td>
                    <div>组名称：</div>
                    <select id="syConfigComboGrid" class="easyui-validatebox" name="groupId" style="width:50%"
                            data-options="required:true"></select>
                </td>
            </tr>
            <tr>
                <td>
                    <div>英文名称：</div>
                    <input class="easyui-validatebox" type="text" style="width:50%" name="name"
                           data-options="required:true"/></td>
            </tr>
            <tr>
                <td>
                    <div>中文名称：</div>
                    <input class="easyui-validatebox" type="text" style="width:50%" name="label"
                           data-options="required:true"/></td>
            </tr>
            <tr>
                <td>
                    <div>备注：</div>
                    <textarea name="note" style="width:50%"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    $(function () {
        var operate = '${operate}';

        if ('V' == operate || 'H' == operate) {
            var permissionObj = ['submitButton'];
            form_toolbar_control(permissionObj, 'hide');
        }
        form_combogrid_list($('#syConfigComboGrid'), 'sy/ConfigGroup/findPageList.do', '组编号', '组中文名');
        //对象{'url':访问地址,'gridId':列表对象ID,'pKey':记录主键字段, 'toTabIndex':转向至tabs索引数的页面（从0开始)}
        var params = {'url': "sy/config/load.do", 'gridId': 'syConfigDg'};
        form_load('syConfigForm', operate, params);
        tab_mask_hide();
        form_submit($("#submitButton"), 'syConfigForm', 'sy/config/doSave.do');
        form_close_tab($('#closeButton'));
    });
</script>
</body>
</html>
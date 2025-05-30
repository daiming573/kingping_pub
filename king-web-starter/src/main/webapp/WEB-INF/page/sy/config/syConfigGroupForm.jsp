<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>配置分组表单</title>
    <%@include file="/head.jsp" %>
</head>
<body class="easyui-layout">
<div class="easyui-panel form-tool" data-options="region:'north'">
    <a href="javascript:void(0)" class="easyui-linkbutton c9" id="submitButton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton c5" id="closeButton">关闭</a>
</div>

<div class="easyui-panel" data-options="region:'center',border:true,fit:false">
    <form id="syConfigGroupForm" method="post">
        <input name="id" type="hidden"/>
        <input name="over" type="hidden"/>
        <table class="form-table">
            <tr>
                <td>
                    <div>组英文名：</div>
                    <input class="easyui-validatebox" type="text" name="name" data-options="required:true"/></td>
            </tr>
            <tr>
                <td>
                    <div>组中文名：</div>
                    <input class="easyui-validatebox" type="text" name="label" data-options="required:true"/></td>
            </tr>
            <tr style="display: none;" id="hideCreateLine1">
                <td>
                    <div>创建者：</div>
                    <input type="text" name="createUserLabel" disabled></input></td>
            </tr>
            <tr style="display: none;" id="hideCreateLine2">
                <td>
                    <div>创建时间：</div>
                    <input class="easyui-validatebox" type="text" name="createDate" disabled/></td>
            </tr>
            <tr style="display: none;" id="hideUpdateLine1">
                <td>
                    <div>更新者：</div>
                    <input type="text" name="updateUserLabel" disabled></input></td>
            </tr>
            <tr style="display: none;" id="hideUpdateLine2">
                <td>
                    <div>更新时间：</div>
                    <input class="easyui-validatebox" type="text" name="updateDate" disabled/></td>
            </tr>
            <tr>
                <td>
                    <div>备注：</div>
                    <textarea name="note" style="width:51%"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    $(function () {
        var operate = '${operate}';
        if ('U' == operate || 'V' == operate || 'H' == operate) {
            $('#hideCreateLine1').show();
            $('#hideCreateLine2').show();
            $('#hideUpdateLine1').show();
            $('#hideUpdateLine2').show();
        }
        if ('V' == operate || 'H' == operate) {
            var permissionObj = ['submitButton'];
            form_toolbar_control(permissionObj, 'hide');
        }
        //对象{'url':访问地址,'gridId':列表对象ID,'pKey':记录主键字段, 'toTabIndex':转向至tabs索引数的页面（从0开始)}
        var params = {'url': "sy/ConfigGroup/load.do", 'gridId': 'syConfigGroupDg'};
        form_load('syConfigGroupForm', operate, params);
        tab_mask_hide();
        form_submit($("#submitButton"), 'syConfigGroupForm', 'sy/ConfigGroup/doSave.do');
        form_close_tab($('#closeButton'));

    });
</script>
</body>
</html>
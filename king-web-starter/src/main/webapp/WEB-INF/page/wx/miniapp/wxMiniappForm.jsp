<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>微信小程序表单</title>
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
    <form id="wxMiniappForm" method="post">
        <input name="id" type="hidden"/>
        <table class="form-table">
            <tr>
                <td>
                    <div>小程序编号：</div>
                    <input class="easyui-validatebox" type="text" name="miniappCode" data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div>小程序名称：</div>
                    <input class="easyui-textbox" type="text" name="miniappName" data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div>appId：</div>
                    <input class="easyui-textbox" type="text" name="appId" data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div>appSecret：</div>
                    <input class="easyui-textbox" type="text" name="appSecret" data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div>状态：</div>
                    <select  class="easyui-combobox" type="text" name="status" data-options="required:true" style="width:226px;" >
                        <option value="0">启用</option>
                        <option value="1">停用</option>
                    </select>
                </td>
            </tr>
            <tr style="display: none;" id="hideCreateLine">
                <td>
                    <div>创建时间：</div>
                    <input class="easyui-datetimebox" type="text" name="createTime" disabled/></td>
            </tr>
            <tr style="display: none;" id="hideUpdateLine">
                <td>
                    <div>更新时间：</div>
                    <input class="easyui-datetimebox" type="text" name="updateTime" disabled/></td>
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
        var params = {'url': "wx/miniapp/load.do", 'gridId': 'dg'};
        form_load('wxMiniappForm', operate, params);
        tab_mask_hide();
        form_submit($("#submitButton"), 'wxMiniappForm', 'wx/miniapp/doSave.do');
        form_close_tab($('#closeButton'));

    });
</script>
</body>
</html>
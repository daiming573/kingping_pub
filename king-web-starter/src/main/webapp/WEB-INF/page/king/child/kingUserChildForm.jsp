<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>金猪用户孩子表单</title>
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
    <form id="kingUserChildForm" method="post">
        <input name="id" type="hidden"/>
        <table class="form-table">
            <tr>
                <td>
                    <div>昵称：</div>
                    <input class="easyui-textbox" type="text" name="nickName" data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div>性别：</div>
                    <select  class="easyui-combobox" type="text" name="sex" data-options="required:true" style="width:226px;" >
                        <option value="F">女</option>
                        <option value="M">男</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <div>生日：</div>
                    <input class="easyui-datebox" type="text" name="birthday" data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div>账户：</div>
                    <input class="easyui-datebox" type="text" name="accountTotal" data-options="required:false,readonly:true"/>
                </td>
            </tr>
            <tr style="display: none;" id="hideCreateLine">
                <td>
                    <div>创建时间：</div>
                    <input class="easyui-datetimebox" type="text" name="createTime" disabled/>
                </td>
            </tr>
            <tr style="display: none;" id="hideUpdateLine">
                <td>
                    <div>更新时间：</div>
                    <input class="easyui-datetimebox" type="text" name="updateTime" disabled/>
                </td>
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
        var params = {'url': "king/child/load.do", 'gridId': 'dg'};
        form_load('kingUserChildForm', operate, params);
        tab_mask_hide();
        form_submit($("#submitButton"), 'kingUserChildForm', 'king/child/doSave.do');
        form_close_tab($('#closeButton'));

    });
</script>
</body>
</html>
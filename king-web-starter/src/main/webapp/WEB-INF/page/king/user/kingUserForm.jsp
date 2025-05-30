<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>金猪用户表单</title>
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
    <form id="kingUserForm" method="post">
        <input name="parentId" type="hidden"/>
        <table class="form-table">
            <tr>
                <td>
                    <div>userId：</div>
                    <input class="easyui-textbox" type="text" name="userId" data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div>unionId：</div>
                    <input class="easyui-validatebox" type="text" name="unionId" data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div>openId：</div>
                    <input class="easyui-textbox" type="text" name="openId" data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div>用户类型：</div>
                    <select  class="easyui-combobox" type="text" name="userType" data-options="required:true" style="width:226px;" >
                        <option value="F">父亲</option>
                        <option value="M">母亲</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <div>昵称：</div>
                    <input class="easyui-textbox" type="text" name="nickName" data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div>familyId：</div>
                    <input class="easyui-textbox" type="text" name="familyId" data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div>mobile：</div>
                    <input class="easyui-textbox" type="text" name="mobile" data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div>邀请码：</div>
                    <input class="easyui-textbox" type="text" name="inviteCode" data-options="required:false"/>
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
        var params = {'url': "king/user/load.do", 'gridId': 'dg'};
        form_load('kingUserForm', operate, params);
        tab_mask_hide();
        form_submit($("#submitButton"), 'kingUserForm', 'king/user/doSave.do');
        form_close_tab($('#closeButton'));

    });
</script>
</body>
</html>
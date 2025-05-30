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
    <form id="accountRecordForm" method="post">
        <input name="parentId" type="hidden"/>
        <table class="form-table">
            <tr>
                <td>
                    <div>childId：</div>
                    <input class="easyui-textbox" type="text" name="childId" data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div>类型：</div>
                    <select  class="easyui-combobox" type="text" name="type" data-options="required:true" style="width:226px;" >
                        <option value="income">收入</option>
                        <option value="pay">支出</option>
                        <option value="task">任务</option>
                        <option value="reward">奖励</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <div>收入：</div>
                    <input class="easyui-textbox" type="text" name="accountIn" data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div>支出：</div>
                    <input class="easyui-textbox" type="text" name="accountOut" data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div>状态：</div>
                    <select  class="easyui-combobox" type="text" name="status" data-options="required:true" style="width:226px;" >
                        <option value="undo">未完成</option>
                        <option value="finish">已完成</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <div>备注：</div>
                    <input class="easyui-textbox" type="text" name="notes" data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div>日期：</div>
                    <input class="easyui-datebox" type="text" name="currentDate" data-options="required:false"/>
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
        var params = {'url': "king/record/load.do", 'gridId': 'dg'};
        form_load('accountRecordForm', operate, params);
        tab_mask_hide();
        form_submit($("#submitButton"), 'accountRecordForm', 'king/record/doSave.do');
        form_close_tab($('#closeButton'));

    });
</script>
</body>
</html>
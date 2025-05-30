<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>配置数据表单</title>
    <%@include file="/head.jsp" %>
</head>
<body class="easyui-layout">
<div class="easyui-panel form-tool" data-options="region:'north'">
    <a href="javascript:void(0)" class="easyui-linkbutton c9" id="submitButton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton c5" id="closeButton">关闭</a>
</div>

<div class="easyui-panel" data-options="region:'center',border:true,fit:false">
    <form id="syConfigDataForm" method="post">
        <input name="id" type="hidden"/>
        <input name="over" type="hidden"/>
        <input id="configId" name="configId" type="hidden"/>
        <table class="form-table">
            <tr>
                <td>
                    <div>配置名称：</div>
                    <input type="text" id="configLabel" type="text" disabled/>
                </td>
                <td>
                    <div>有效性：</div>
                    <select class="easyui-combobox" name="isValid" data-options="editable:false" style="width: 51%;">
                        <option value="Y">有效</option>
                        <option value="N">无效</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <div>英文名称：</div>
                    <input class="easyui-validatebox" type="text" id="name" name="name" data-options="required:true"/>
                </td>
                <td>
                    <div>中文名称：</div>
                    <input class="easyui-validatebox" type="text" name="label" data-options="required:true"/></td>
            </tr>
            <tr>
                <td>
                    <div>数据类型：</div>
                    <input class="easyui-combobox" id="dataTypeCd" data-options="editable:false" name="dataTypeCd"
                           style="width: 51%;">
                </td>
                <td>
                    <div>显示顺序：</div>
                    <input class="easyui-validatebox" type="text" name="dispOrder" data-options="required:true">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <div>数据内容：</div>
                    <input class="easyui-textbox" name="dataValue" data-options="multiline:true,required:true"
                           style="height:60px;width: 76%;">
                </td>
            </tr>

            <tr style="display: none;" id="hideCreateLine">
                <td>
                    <div>创建者：</div>
                    <input type="text" name="createUserLabel" disabled></input></td>
                <td>
                    <div>创建时间：</div>
                    <input type="text" name="createDate" disabled/></td>
            </tr>
            <tr style="display: none;" id="hideUpdateLine">
                <td>
                    <div>更新者：</div>
                    <input type="text" name="updateUserLabel" disabled></input></td>
                <td>
                    <div>更新时间：</div>
                    <input type="text" name="updateDate" disabled/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <div>备注：</div>
                    <textarea name="note" style="width: 76%;"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    $(function () {
        var operate = '${operate}';
        form_combobox_list($('#dataTypeCd'), 'sy.config.data.dataTypeCd');
        if ('A' == operate) {
            $('#dataTypeCd').combobox('setValue', 'S');
            var p1 = parent[1];
            $('#name').val(p1.$('#configName').val() + "_");
            $('#configId').val(p1.$('#configId').val());
        }
        $('#configLabel').val(parent[1].$('#configLabel').val());
        if ('U' == operate || 'V' == operate || 'H' == operate) {
            $('#hideCreateLine').show();
            $('#hideUpdateLine').show();
            //对象{'url':访问地址,'gridId':列表对象ID,'pKey':记录主键字段, 'toTabIndex':转向至tabs索引数的页面（从0开始)}
            var params = {'url': "sy/ConfigData/load.do", 'gridId': 'syConfigDataDg', 'pKey': null, 'toTabIndex': 1};
            form_load('syConfigDataForm', operate, params);
        }
        if ('V' == operate || 'H' == operate) {
            var permissionObj = ['submitButton'];
            form_toolbar_control(permissionObj, 'hide');
        }
        tab_mask_hide();
        form_submit($("#submitButton"), 'syConfigDataForm', 'sy/ConfigData/doSave.do', 1);
        form_close_tab($('#closeButton'));

    });
</script>
</body>
</html>
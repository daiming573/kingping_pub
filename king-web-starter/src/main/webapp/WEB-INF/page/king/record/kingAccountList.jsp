<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>金猪用户列表</title>
    <%@include file="/head.jsp" %>
    <script type="text/javascript" src="js/pages/formatter.js"></script>
    <script type="text/javascript" src="js/pages/bs_formatter.js"></script>
</head>
<body class="easyui-layout">
<div class="easyui-panel" data-options="region:'north'">
    <form id="searchForm" method="post">
        <table class="form-search">
            <tr>
                <td>childId：</td>
                <td><input class="easyui-validatebox" type="text" name="childId"/>
                </td>
                <td>类型：</td>
                <td>
                    <select  class="easyui-combobox" type="text" name="type" style="width: 180px;">
                        <option value="income">收入</option>
                        <option value="pay">支出</option>
                        <option value="task">任务</option>
                        <option value="reward">奖励</option>
                    </select>
                </td>
                <td class="s_button_left">
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'icon-search',toggle:true" id="searchBtn">查询</a>
                </td>
            </tr>
            <tr>
                <td>状态：</td>
                <td>
                    <select  class="easyui-combobox" type="text" name="status" style="width:180px;"  >
                        <option value="undo">未完成</option>
                        <option value="finish">已完成</option>
                    </select>
                </td>
                <td></td>
                <td></td>
                <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'icon-cancel',toggle:true" id="clearBtn">清除</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- multiple: true, 多选-->
<div data-options="region:'center'" style="border:0px;">
    <table id="dg" class="easyui-datagrid"
           data-options="rownumbers:true,
			fit:true,
			border:true,
			pagination:true,
			toolbar:toolbar,
			fitColumns: true,
			pageSize: 20,
			iconCls:'form-list',
			onClickRow: onClickRow,
			singleSelect:true,
			url:'king/record/findPageList.do'">
        <thead>
        <tr>
            <th data-options="field:'id',checkbox:true"></th>
            <th data-options="field:'childId',width:120">childId</th>
            <th data-options="field:'type',width:110,formatter:formatType">用户类型</th>
            <th data-options="field:'accountIn',width:110">收入</th>
            <th data-options="field:'accountOut',width:110">支出</th>
            <th data-options="field:'status',width:110,formatter:formatStatus">状态</th>
            <th data-options="field:'notes',width:110">备注</th>
            <th data-options="field:'currentDate',width:110">日期</th>
            <th data-options="field:'createTime',width:110">创建时间</th>
            <th data-options="field:'updateTime',width:110">更新时间</th>

        </tr>
        </thead>
    </table>
</div>
<script type="text/javascript">
    // var addBtn = {text: '新增', iconCls: 'icon-add', id: 'addBtn'};
    var updateBtn = {text: '修改', iconCls: 'form-edit', id: 'updateBtn'};
    var deleteBtn = {text: '删除', iconCls: 'form-delete', id: 'deleteBtn'};
    var viewBtn = {text: '查看', iconCls: 'form-view', id: 'viewBtn'};
    var toolbar = [updateBtn, viewBtn];

    $(function () {
        tab_mask_hide();
        form_search($('#searchBtn'), 'searchForm', 'dg');
        form_clear($('#clearBtn'), 'searchForm');
        //form_add($("#addBtn"), 'king/record/goAdd.do');
        form_update($("#updateBtn"), 'king/record/goEdit.do', 'dg');
        // form_delete($("#deleteBtn"), 'king/record/doDelete.do', 'dg');
        form_view($("#viewBtn"), 'king/record/goView.do', 'dg');
    });

    function bsMainLoad() {
        $("#dg").datagrid('reload');
        tab_mask_hide();
    }

    //类型，income-收入，pay-支出，task-任务，reward-奖励
    function formatType(value, row, index) {
        if ("income" == value) {
            return '收入';
        } else if ("pay" == value) {
            return '支出';
        } else if ("task" == value) {
            return '任务';
        } else if ("reward" == value) {
            return '奖励';
        } else {
            return '未知';
        }
    }

    //任务状态，undo-未完成，finish-已完成
    function formatStatus(value, row, index) {
        if ("undo" == value) {
            return '未完成';
        } else if ("finish" == value) {
            return '已完成';
        } else {
            return '未知';
        }
    }

</script>
</body>
</html>
// 生成工具栏
function bulidToolBar(o, w, grid, title, dofunction) {
    $("<span>" + title + "：</span>").appendTo(o);
    var text = $("<input type=\"text\" style=\"width:200px;\"></input>");
    text.appendTo(o);
    var searchBtn = $("<a href=\"javascript:void(0);\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'icon-search'\" >查询</a>").appendTo(o);
    var selectBtn = $("<a href=\"javascript:void(0);\" class=\"easyui-linkbutton\"  data-options=\"plain:true,iconCls:'icon-save'\" >绑定</a>").appendTo(o);
    searchBtn.click(function () {
        grid.datagrid('reload', {'osearch.text': text.val()});
    });
    selectBtn.click(function () {
        var row = grid.datagrid('getSelected');
        if (undefined == row || null == row) {
            $.messager.alert('提示信息', '请选择记录');
            return;
        }
        dofunction(w, row);
    });
}

function buildDatagrid(grid, toolbar, action, thFields) {
    grid.datagrid({
        singleSelect: true,
        pagination: true,
        pageSize: 10,
        height: 360,
        url: action,
        toolbar: toolbar,
        columns: thFields
    });
}

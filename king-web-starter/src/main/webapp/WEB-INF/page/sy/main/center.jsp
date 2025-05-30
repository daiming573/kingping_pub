<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="/head.jsp" %>
    <script type="text/javascript">
        $(function () {
            // 模块
            var id = '${id}';
            var introduceUrl = '${introduceUrl}';
            $.post("sy/main/loadMenu.do", {id: id}, function (result) {
                if (null != result && "" != result) {
                    result = eval("(" + result + ")");
                    menu_load($('#menu'), 'sy/main/loadSubMenu.do', result);
                    //menu_load($('#menu'), '',result);
                    //处理树点击添加tab事件
                    var navigate = {"iconCls": 'com-help', "url": introduceUrl, "text": '功能介绍'};
                    tab_add(navigate);
                    //parent.$('#topTabs').mask("hide");
                }
                parent.$('#topTabs').mask("hide");
            });
        });
    </script>
</head>
<body class="easyui-layout" style="margin-top: 2px;">
<div data-options="region:'west',split:true,iconCls:'catalog-module'" title="功能菜单" style="width:200px;">
    <div class="easyui-accordion" id="menu" data-options="fit:true,border:false">
    </div>
</div>
<div data-options="region:'center'">
    <div id="mainTabPanel" class="easyui-tabs" data-options="fit:true,border:false"></div>
</div>
</body>
</html>
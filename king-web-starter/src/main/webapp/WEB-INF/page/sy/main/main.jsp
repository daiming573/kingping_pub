<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8"/>
    <%@include file="/head.jsp" %>
    <script type='text/javascript' src='js/particleground/jquery.particleground.js'></script>
    <style type="text/css">
        .mainLoginDiv {
            float: right;
            height: 60px;
            line-height: 60px;
            color: #fff;
            text-indent: 30px;
            font-size: 16px;
            font-color: #ffffff;
            margin-right: 50px;
        }
    </style>
</head>
<script type="text/javascript">
    var result;
    $(document).ready(function () {
        $('#particles').particleground({
            dotColor: '#2C95A4',
            lineColor: '#2C95A4'
        });
        $('.intro').css({
            'margin-top': -($('.intro').height() / 2)
        });
    });

    $(function () {
        $.post("sy/main/loadTabs.do", function (result) {
            if (null != result && "" != result) {
                result = eval("(" + result + ")");
                tab_load($('#topTabs'), result);
                tab_select($('#topTabs'), result);
            }
        });
    });

    function logout() {
        var basePath = '<%=path%>';
        location.replace(basePath + 'sy/user/logout.do');
        //event.returnValue=false;

    }

    function openPwdDg() {
        $('#pwdDgForm').form('clear');
        $('#pwdDg').dialog('open');
    }

    function isRightPassword(str) {
        var reg = /^(?![^a-z]+$)(?![^A-Z]+$)(?![^0-9]+$).{8,16}$/;
        if (reg.test(str)) {
            return true;
        } else {
            return false;
        }
        ;
    }

    function updatePasssword() {
        var p1 = $('#newPassword').val();
        var p2 = $('#new2Password').val();
        if (!isRightPassword(p2)) {
            $.messager.alert('提示信息', '新密码必须长度为8至16位，且必须包含大写字母，小写字母和数字');
            return false;
        }
        if (p1 != p2) {
            $.messager.alert('提示信息', '输入的新密码与确认密码不一致，请重新填写');
            return;
        }
        win_submit_impl('pwdDgForm', 'sy/user/updatePassword.do', 'pwdDg', 'N');
    }

</script>
<body class="easyui-layout">
<div data-options="region:'north',border:false" class="top_bg" style="height:60px;">
    <div id="particles" style='position: absolute;width: 100%;height: 100%;    z-index: 0;'>
        <div id="intro">
        </div>
    </div>
    <div class="top_font" style="width:310px;height:60px; float:left; ">
        <img alt="" src="images/login/logo_1.png" width="170px" height="29px;"
             style="padding-top: 15px;margin-left: 20px;">
    </div>
    <div title="退出" class="exit"><img onclick="logout();" alt="退出" src="images/main/exit.png"
                                      style="cursor: pointer;position: absolute;top: 22px;"></div>
    <div title="修改密码" class="updatepassword"><img onclick="openPwdDg();" alt="修改密码" src="images/main/mima.png"
                                                  width="15px" height="17px;"
                                                  style="cursor: pointer;position: absolute;top: 22px;"></div>
    <div class="mainLoginDiv" style=" z-index: 99999999999;">欢迎您，<%=userName %>
    </div>
</div>
<div data-options="region:'center'">
    <div id="topTabs" class="easyui-tabs" data-options="fit:true,border:false"></div>
</div>
<div data-options="region:'south',split:true,border:false" class="toolbar" style="height:30px;text-align:center;">
    <div style="padding-top:2px;"> &copy;金猪kingpig</div>
</div>

<div id="pwdDg" class="easyui-dialog" title="修改密码"
     data-options="modal:true,minimizable:false,closed:true,iconCls:'icon-edit'"
     style="width:420px;height:180px;padding:10px;">
    <form id="pwdDgForm" method="post">
        <table class="form-table">
            <tr>
                <td>
                    <div>原始密码：</div>
                    <input class="easyui-validatebox" type="password" id="password" name="password" maxlength="20"
                           data-options="required:true"/></td>
            </tr>
            <tr>
                <td>
                    <div>新密码：</div>
                    <input class="easyui-validatebox" type="password" id="newPassword" name="newPassword" maxlength="20"
                           data-options="required:true"/></td>
            </tr>
            <tr>
                <td>
                    <div>确认密码：</div>
                    <input class="easyui-validatebox" type="password" id="new2Password" name="new2Password"
                           maxlength="20" data-options="required:true"/></td>
            </tr>
        </table>
    </form>
    <div style="text-align:center;padding:5px">
        <a href="javascript:void(0)" class="easyui-linkbutton c9" id="submitEditPwdButton" onclick="updatePasssword();">提交</a>
    </div>
</div>
</body>
</html>

/* 
 * 遮罩Mask
 */
(function ($) {
    function init(target, options) {
        var wrap = $(target);
        if ($("div.mask", wrap).length) wrap.mask("hide");
        wrap.attr("position", wrap.css("position"));
        wrap.attr("overflow", wrap.css("overflow"));
        wrap.css("position", "relative");
        wrap.css("overflow", "hidden");

        var maskCss = {
            position: "absolute",
            left: 0,
            top: 0,
            cursor: "wait",
            background: "#ccc",
            opacity: options.opacity,
            filter: "alpha(opacity=" + options.opacity * 100 + ")",
            display: "none"
        };

        var maskMsgCss = {
            position: "absolute",
            width: "auto",
            padding: "10px 20px",
            border: "2px solid #ccc",
            color: "white",
            cursor: "wait",
            display: "none",
            borderRadius: 5,
            background: "black",
            opacity: 0.6,
            filter: "alpha(opacity=60)"
        };
        var width, height, left, top;
        if (target == 'body') {
            width = Math.max(document.documentElement.clientWidth, document.body.clientWidth);
            height = Math.max(document.documentElement.clientHeight, document.body.clientHeight);
        } else {
            width = wrap.outerWidth() || "100%";
            height = wrap.outerHeight() || "100%";
        }
        $('<div class="mask"></div>').css($.extend({}, maskCss, {
            display: 'block',
            width: width,
            height: height,
            zIndex: options.zIndex
        })).appendTo(wrap);

        var maskm = $('<div class="mask-msg"></div>').html(options.maskMsg).appendTo(wrap).css(maskMsgCss);

        if (target == 'body') {
            left = (Math.max(document.documentElement.clientWidth, document.body.clientWidth) - $('div.mask-msg', wrap).outerWidth()) / 2;
            if (document.documentElement.clientHeight > document.body.clientHeight) {
                top = (Math.max(document.documentElement.clientHeight, document.body.clientHeight) - $('div.mask-msg', wrap).outerHeight()) / 2;
            } else {
                top = (Math.min(document.documentElement.clientHeight, document.body.clientHeight) - $('div.mask-msg', wrap).outerHeight()) / 2;
            }

        } else {
            left = (wrap.width() - $('div.mask-msg', wrap).outerWidth()) / 2;
            top = (wrap.height() - $('div.mask-msg', wrap).outerHeight()) / 2;
        }

        maskm.css({
            display: 'block',
            zIndex: options.zIndex + 1,
            left: left,
            top: top
        });

        setTimeout(function () {
            wrap.mask("hide");
        }, options.timeout);

        return wrap;
    }

    $.fn.mask = function (options) {
        if (typeof options == 'string') {
            return $.fn.mask.methods[options](this);
        }
        options = $.extend({}, $.fn.mask.defaults, options);
        return init(this, options);
    };
    $.mask = function (options) {
        if (typeof options == 'string') {
            return $.fn.mask.methods[options]("body");
        }
        options = $.extend({}, $.fn.mask.defaults, options);
        return init("body", options);
    };

    $.mask.hide = function () {
        $("body").mask("hide");
    };

    $.fn.mask.methods = {
        hide: function (jq) {
            return jq.each(function () {
                var wrap = $(this);
                $("div.mask", wrap).fadeOut(function () {
                    $(this).remove();
                });
                $("div.mask-msg", wrap).fadeOut(function () {
                    $(this).remove();
                    wrap.css("position", wrap.attr("position"));
                    wrap.css("overflow", wrap.attr("overflow"));
                });
            });
        }
    };
    maskMsg:'\u52a0\u8f7d……',
        $.fn.mask.defaults = {
            maskMsg: 'loading...',
            zIndex: 100000,
            timeout: 30000,
            opacity: 0.6
        };
})(jQuery);
/* 
 * tabs方法扩展
 */
(function () {
    $.extend($.fn.tabs.methods, {
        // 清除所有tab
        closeTabs: function (jq) {
            return jq.each(function () {
                var panel = $(this).tabs('tabs');
                for (var i = 0, len = panel.length; i < len; i++) {
                    $(this).tabs('close', 0);
                }
            });
        },
        /**
         * 固定前几项不关闭，之后根据索引值进行关闭
         * @param t 对象参数{"start":起始位数,"index":关闭的Tab索引值}
         */
        closeTabByAfter: function (jq, t) {
            if (t.index == undefined) {
                alert('closeTabByAfter方法参数异常');
                return;
            }
            var panel = $(jq[0]).tabs('tabs');
            for (var i = t.start, len = panel.length; i < len; i++) {
                $(jq[0]).tabs('close', t.index);
            }
        },
        /**
         * 根据索引值进行关闭
         * @param index 关闭的Tab索引值
         */
        closeTabByIndex: function (jq, index) {
            return jq.each(function () {
                //var panel = $(this).tabs('tabs');
                $(this).tabs('close', index);
            });
        },
        openTab: function (jq) {
            var tab = $(jq[0]).tabs('getSelected');
            var iframe = $("<iframe style='width:100%;height:100%;' scrolling='no' frameborder='0'></iframe>");
            iframe.appendTo(tab);
            var tbId = tab.attr("id");
            //获取tab的iframe对象  
            var _url = eval("({" + tab.attr('data-options') + "})").url;
            var tbIframe = $("#" + tbId + " iframe:first-child");
            tbIframe.attr("src", _url);
        },
        addTab: function (jq, t) {
            var tabWidth = '';
            tabWidth = t.width == undefined ? 110 : t.width;
            var panel = $(jq[0]);
            var id = 'mainFrame';
            var turl = t.url;
            id = t.code == undefined ? id : t.code;
            panel.tabs('add', {
                title: t.text,
                selected: true,
                tabWidth: tabWidth,
                closable: false,
                iconCls: t.iconCls,
                cache: false,
                content: '<iframe id="' + id + '" scrolling="no" frameborder="0"  src="' + turl + '" style="width:100%;height:100%;"></iframe>'
            });
            if (turl != null) {
                turl = turl.replace(/^\s+|\s+$/gm, '');
                if (turl.substr(0, 7) != "http://" && turl.substr(0, 8) != "https://") {
                    panel.mask();
                }
            }
        },
        show: function (jq) {
            var panel = $(jq[0]);
            panel.mask();
        }
    });
})(jQuery);

/* 
 * form方法扩展
 */
(function () {
    $.extend($.fn.form.methods, {
        // 返回FORM的JSON对象
        getJsonPack: function (jq) {
            var _value = $(jq[0]).serialize();
            return formatStringToJson(_value);
        },
        // 返回FORM的JSON对象转String
        getJsonToStr: function (jq) {
            var _value = $(jq[0]).serialize();
            return JSON.stringify(formatStringToJson(_value));
        },
        /**
         * 获取列表选中数据的ID,调用服务接口加载数据至form
         * @param t 对象参数{'url':访问地址,'gridId':列表对象ID,'pKey':记录主键字段, 'toTabIndex':转向至tabs索引数的页面（从0开始)}
         */
        loadRecordFromGrid: function (jq, t) {
            var o = (t.toTabIndex == undefined || null == t.toTabIndex) ? parent[0] : parent[t.toTabIndex];
            var timestamp = Date.parse(new Date());
            if (null != t.url && t.url.indexOf('?') != -1) {
                $(jq[0]).form('load', t.url + '&t=' + timestamp);
                return;
            }
            var record = o.$('#' + t.gridId).datagrid('getSelected');
            if (record != null) {
                if (undefined == t.url || null == t.url) {
                    // url为空取静态加载form数据
                    $(jq[0]).form('load', record);
                } else {
                    var pk = "id";
                    if (undefined != t.pKey && null != t.pKey) {
                        pk = t.pKey;
                    }
                    var pkValue = record[pk];
                    if (undefined == pkValue || null == pkValue) {
                        $.messager.alert('error', '主键不存在！');
                    }
                    $(jq[0]).form('load', t.url + '?' + pk + '=' + pkValue + '&t=' + timestamp);
                }
            } else {
                $.messager.alert('error', '加载数据失败！');
            }
        },
        /**
         * 新增保存，关闭当前sheet，刷新列表
         * @param t 对象参数{'url':访问地址,'toTabIndex':转向至tabs索引数的页面（从0开始)}
         */
        submitForm: function (jq, t) {
            var o = (t.toTabIndex == undefined || null == t.toTabIndex) ? parent[0] : parent[t.toTabIndex];
            $(jq[0]).form('submit', {
                url: t.url,
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (data) {
                    //重新加载父页面
                    o.bsMainLoad();
                    //关闭当前tab
                    var index = o.tab_get_selected_index();
                    o.tab_close_after(index);
                }
            });
        }
    });
})(jQuery);
(function () {
    $.extend($.fn.datagrid.methods, {
        editCell: function (jq, param) {
            return jq.each(function () {
                //var opts = $(this).datagrid('options');
                var fields = $(this).datagrid('getColumnFields', true).concat($(this).datagrid('getColumnFields'));
                for (var i = 0; i < fields.length; i++) {
                    var col = $(this).datagrid('getColumnOption', fields[i]);
                    col.editor1 = col.editor;
                    if (fields[i] != param.field) {
                        col.editor = null;
                    }
                }
                $(this).datagrid('beginEdit', param.index);
                for (var i = 0; i < fields.length; i++) {
                    var col = $(this).datagrid('getColumnOption', fields[i]);
                    col.editor = col.editor1;
                }
            });
        }
    });
    $.extend($.fn.validatebox.defaults.rules, {
        idcard: {// 验证身份证  
            validator: function (value) {
                return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
            },
            message: '身份证号码格式不正确'
        },
        phone: {// 验证电话号码  
            validator: function (value) {
                return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
            },
            message: '格式不正确,请使用下面格式:020-88888888'
        },
        mobile: {// 验证手机号码  
            validator: function (value) {
                return /^(13|15|18)\d{9}$/i.test(value);
            },
            message: '手机号码格式不正确'
        },
        intRange: {
            validator: function (value, param) {
                var flg = /^\d+?$/i.test(value);
                if (!flg) {
                    return flg;
                }
                var len = $.trim(value).length;
                if (len >= 2) {
                    var v = value.substr(0, 1);
                    if (v == '0') return false;
                }
                var vleInt = parseInt(value);
                return vleInt >= param[0] && vleInt <= param[1];
            },
            message: "请输入数字必须介于{0}和{1}之间."
        },
        intOrFloat: {// 验证整数或小数
            validator: function (value) {
                return /^\d+(\.\d+)?$/i.test(value);
            },
            message: '请输入数字，并确保格式正确'
        },
        lengthRang: {//验证字符串长度
            validator: function (value, param) {
                var strLen = $.trim(value).length;
                return strLen <= param[0];
            },
            message: '最多可输入{0}个字符'
        },
    });
})(jQuery);

/**
 * 字符串序列化打JSON对象
 * 格式：id=1&name=zeng
 * @param str 字符串
 */
function formatStringToJson(str) {
    //serialize 会把空格转换为+号，所以先替换，再转码
    str = str.replace(/\+/g, " ");
    str = decodeURIComponent(str, true);
    str = str.replace(/&/g, "','");
    str = str.replace(/=/g, "':'");
    str = "({'" + str + "'})";
    obj = eval(str);
    return obj;
}

function toJson(str) {
    str = "(" + str + ")";
    obj = eval(str);
    return obj;
}

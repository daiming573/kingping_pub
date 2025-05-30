function getRootPath() {
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp  
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp  
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083  
    var localhostPaht = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：/uimcardprj  
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return (localhostPaht + projectName);
}

var sysUrl = getRootPath();
var _mainTabPanel = "mainTabPanel";
var _parentTabObj = parent.$('#' + _mainTabPanel);

/**
 * 清除所有tabs
 */
function tab_close_all() {
    $("#" + _mainTabPanel).tabs('closeTabs');
}

/**
 * 根据索引清除对应的Tab
 *
 * @param index
 *            tab的索引编号
 */
function tab_close_after(index) {
    var indexVal;
    if (index < 0) {
        return;
    } else if (undefined == index || null == index) {
        indexVal = 1;
    } else {
        indexVal = index;
    }
    var t = {
        "start": 1,
        "index": indexVal
    };
    _parentTabObj.tabs('closeTabByAfter', t);
}

/**
 * 根据对象easyui-tabs获取目前选择的tab
 *
 * @param obj
 * @returns
 */
function tab_get_selected() {
    var pn = _parentTabObj.tabs('getSelected');
    var tab = pn.panel('options').tab;
    return tab;
}

/**
 * 根据对象easyui-tabs获取目前选择的tab
 *
 * @param obj
 * @returns
 */
function tab_get_selected_index() {
    return tab_get_index_impl(_parentTabObj);
}

function tab_get_index_impl(tabObject) {
    var tab = tabObject.tabs('getSelected');
    var index = tabObject.tabs('getTabIndex', tab);
    return index;
}

/**
 * 左边目录点击添加Tab
 *
 * @param t
 *            对象参数{iconCls="icon-role", url="bs_main.html", text="标题",
 *            width='100'}
 */
function tab_add(t, panelId) {
    var tp;
    if (undefined != panelId || null != panelId) {
        tp = $('#' + panelId);
    } else {
        tp = $('#' + _mainTabPanel);
    }
    tp.tabs('addTab', t);
}

/**
 * 加载选项卡
 *
 * @param obj
 *            easyui-tabs对象
 * @param data
 *            tabJSON数据
 */
function tab_load(obj, data) {
    $.each(data, function (i, n) {
        var url = n.url;
        if (undefined != url && "" != url) {
            url = url + "?id=" + n.id + '&introduceUrl=' + n.introduceUrl;
        }
        var navigate = {
            "iconCls": n.iconCls,
            "url": "",
            "text": n.text,
            "id": n.id
        };
        if (i == 0 && n.url != "") {
            navigate = {
                "iconCls": n.iconCls,
                "url": url,
                "text": n.text,
                "id": n.id
            };
        }
        obj.tabs("addTab", navigate);
        obj.tabs("select", 0);
    });
}

/**
 * 选择选项卡
 *
 * @param obj
 *            easyui-tabs对象
 * @param data
 *            tabJSON数据
 */
function tab_select(obj, data) {
    obj.tabs({
        onSelect: function (title, index) {
            var tab = obj.tabs('getSelected');
            var o = "";
            $.each(data, function (i, n) {
                o = data[index];
            });
            var url = o.url;
            if (undefined == url || "" == url) {
                url = "";
            } else {
                url = url + "?id=" + o.id + '&introduceUrl=' + o.introduceUrl;
            }
            obj.tabs('update', {
                tab: tab,
                options: {
                    content: '<iframe id="' + o.id
                        + '" scrolling="no" frameborder="0"  src="' + url
                        + '" style="width:100%;height:100%;"></iframe>'
                }
            });
        }
    });
}

/**
 * Tab遮罩隐藏
 */
function tab_mask_hide() {
    _parentTabObj.mask("hide");
    // _parentTabObj.tabs('hide');
}

/**
 * Tab遮罩显示
 *
 * @param msg
 *            显示信息
 */
// function tabMaskShow(msg){
function tab_mask_show(msg) {
    _parentTabObj.tabs('show', msg);
}

/**
 * 子页面添加父页面的tabs
 *
 * @param title
 *            tab标题
 * @param icon
 *            tab图标
 * @param url
 *            tab地址
 * @param width
 *            tab宽度
 */
function tab_add_parent(title, icon, url, width) {
    var isExistTab = _parentTabObj.tabs('getTab', title);
    if (isExistTab) {
        _parentTabObj.tabs('select', title);
        // refreshTab({tabTitle:title,url:url});
        tab_refresh({
            tabTitle: title,
            url: url
        });
    } else {
        var t = {
            "iconCls": icon,
            "url": url,
            "text": title,
            "width": width,
            "closeable": true,
            closeable: true
        };
        _parentTabObj.tabs('addTab', t);
    }
}

/**
 * 子页面关闭时，刷新父tab
 * <p>
 * cfg示例:{tabTitle:'tabTitle',url:'refreshUrl'}
 * </p>
 * <p>
 * 如果tabTitle为空，则默认刷新当前选中的tab
 * </p>
 * <p>
 * 如果url为空，则默认以原来的url进行reload
 * </p>
 *
 * @param cfg
 *            配置
 */
function tab_refresh(cfg) {
    var _refreshTab = cfg.tabTitle ? _parentTabObj.tabs('getTab', cfg.tabTitle)
        : _parentTabObj.tabs('getSelected');
    if (_refreshTab && refresh_tab.find('iframe').length > 0) {
        var _refreshIfram = _refreshTab.find('iframe')[0];
        var _refreshUrl = cfg.url ? cfg.url : _refreshIfram.src;
        // _refresh_ifram.src = refresh_url;
        _refreshIfram.contentWindow.location.href = _refreshUrl;
        tab_mask_show();
    }
}

/**
 * 加载菜单
 *
 * @param obj
 *            easyui-accordion对象
 * @param url
 *            请求地址
 * @param data
 *            菜单JSON数据
 */
function menu_load(obj, url, data) {
    menu_load_module(obj, data);
    menu_load_tree(obj, url, data);
}

/**
 * 菜单加载模块
 *
 * @param obj
 *            easyui-accordion对象
 * @param data
 *            菜单JSON数据
 */
function menu_load_module(obj, data) {
    $.each(data, function (i, n) {
        obj.accordion('add', {
            title: n.text,
            id: n.id,
            iconCls: n.iconCls,
            content: '<div style="padding:10px"><ul name="' + n.text + '"></ul></div>'
        });
    });
}

/**
 * 加载子菜单
 *
 * @param obj
 *            easyui-accordion对象
 * @param url
 *            子菜单节点ACTION地址
 *//*
function menu_load_tree(obj, url, data) {
	obj.accordion({
		onSelect : function(title, i) {
			var exeUrl = "";
			var outerCatalog = data[i].outerCatalog;
			if(undefined != outerCatalog && null != outerCatalog && "" != outerCatalog) {
				exeUrl = outerCatalog;
			} else {
				var selectObj = obj.accordion('getSelected');
				exeUrl = url + '?parentId=' + selectObj[0].id;
			}
			$("ul[name='" + title + "']").tree({
				url : exeUrl,
				method : 'get',
				onClick : function(node) {
					var url = node.url;
					var redirectUrl = node.redirectUrl;
					if(undefined != redirectUrl && '' != redirectUrl) {
						window.open(redirectUrl);
					}
					tab_close_all();
					if (undefined != url && '' != url) {
						tab_add(node);
					}
				}
			});
		}
	});
}
*/
/**
 * 加载子菜单
 *
 * @param obj
 *            easyui-accordion对象
 * @param url
 *            子菜单节点ACTION地址
 */
function menu_load_tree(obj, url, data) {
    obj.accordion({
        onSelect: function (title, i) {
            var exeUrl = "";
            var outerCatalog = data[i].outerCatalog;
            if (undefined != outerCatalog && null != outerCatalog && "" != outerCatalog) {
                exeUrl = outerCatalog;
            } else {
                var selectObj = obj.accordion('getSelected');
                exeUrl = url + '?parentId=' + selectObj[0].id;
            }
            $("ul[name='" + title + "']").tree({
                url: exeUrl,
                method: 'get',
                onClick: function (node) {
                    var url = node.url;
                    var redirectUrl = node.redirectUrl;
                    if (undefined != redirectUrl && '' != redirectUrl) {
                        window.open(redirectUrl);
                    }
                    tab_close_all();
                    if (undefined != url && '' != url) {
                        tab_add(node);
                    }
                }
            });
        }
    });
}

function RString_contains(v, s) {
    return (null != v && null != s) ? (v.indexOf(s) != -1) : false;
}

function RString_isEmpty(v) {
    return (null == v) ? true : !(v.length);
}

/**
 *    操作代码及中文
 */
function getHistorylabel(value, row, index) {
    if ('A' == value) {
        return '新建';
    } else if ('U' == value) {
        return '更新';
    } else if ('D' == value) {
        return '删除';
    }
}

/**
 * 表类型代码及中文
 */
function getTableTypelabel(value, row, index) {
    if ('W' == value) {
        return '工作表';
    } else if ('N' == value) {
        return '虚拟表';
    } else if ('S' == value) {
        return '单体表';
    } else if ('T' == value) {
        return '模板';
    } else if ('M' == value) {
        return '分表';
    }
}

/**
 * 字段类型
 */
function getColumnTypelabel(value, row, index) {
    var v = '';
    if ('' == value || 'R' == value) {
        v = '<a href="javascript:void(0);" class="ds-field" title="实字段">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>';
    } else if ('V' == value) {
        v = '<a href="javascript:void(0);" class="ds-fld" title="虚字段">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>';
    }
    return v;
}

/**
 * 主键格式化
 */
function getColumnPkImage(value, row, index) {
    if ('Y' == value) {
        return '<a href="javascript:void(0);" class="db-key" title="主键">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>';
    }
    return value;
}

/**
 * 角色用户状态
 */
function getRoleUserStatus(value, row, index) {
    if ('B' == value) {
        return "绑定";
    } else if ('R' == value) {
        return "解除";
    }
    return value;
}

/**
 * 角色用户状态
 */
function formatStatus(value, row, index) {
    if ('1' == value) {
        return '<font color="green">启用</font>';
    } else if ('2' == value) {
        return '<font color="red">禁用</font>';
    } else {
        return "未知";
    }
}


/**
 * 性别
 */
function formatSex(value, row, index) {
    if ('F' == value) {
        return "女";
    } else if ('M' == value) {
        return "男";
    } else {
        return "未知";
    }
}

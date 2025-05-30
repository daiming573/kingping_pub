function formatOnOrOff(value, row, index) {
    if ('0' == value) {
        return '<font color="green">启用</font>';
    } else if ('1' == value) {
        return '<font color="red">停用</font>';
    } else {
        return "未知";
    }
}

function formatMsg(value, row, index) {
    if (null == value || "" == value) {
        return "";
    }
    if (value.length > 10) {
        return value.substring(0, 10) + "...";
    } else {
        return value;
    }

}

//长度超长，省略号。鼠标指向显示title
function formatShowTitle(value, row, index) {
    if (null != value && '' != value) {
        value = '<span  title=' + value + '>' + value + '</span>';
    } else {
        value = '';
    }
    return value;
}

function formatTime(value, row, index) {
    if (value != null && value.length >= 14) {
        return value.strToTime();
    }
}

String.prototype.strToTime = function () {
    return this.slice(0, 4) + '-' + this.slice(4, 6) + '-' + this.slice(6, 8) + ' ' + this.slice(8, 10) + ':' + this.slice(10, 12) + ':' + this.slice(12, 14);
}
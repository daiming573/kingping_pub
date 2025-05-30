package com.cat.bean.tree.easyUI;

import java.io.Serializable;

public class IconTree implements Serializable {

    private static final long serialVersionUID = -2956404622610948385L;

    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String text;


    /**
     * 状态 即打开或关闭
     */
    private String state;

    /**
     * 图标
     */
    private String iconCls;

    /**
     * 节点编号
     */
    private String code;


    private Object children;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }


    public String getState() {
        return state;
    }


    public void setState(String state) {
        this.state = state;
    }


    public String getIconCls() {
        return iconCls;
    }


    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public Object getChildren() {
        return children;
    }


    public void setChildren(Object children) {
        this.children = children;
    }


}
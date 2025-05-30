package com.cat.bean.tree.easyUI;

import java.io.Serializable;

public class Tree implements Serializable {

    private static final long serialVersionUID = -2956404622610948385L;

    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String text;

    /**
     * 链接地址 DO
     */
    private String url;

    /**
     * 转向地址
     */
    private String redirectUrl;

    /**
     * 加载外部目录地址
     */
    private String outerCatalog;

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

    /**
     * 关联节点
     */
    private String linkCatalog;

    /**
     * 类型
     */
    private String typeName;

    /**
     * 介绍调度地址
     */
    private String introduceUrl;

    private Object children;
    private boolean checked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLinkCatalog() {
        return linkCatalog;
    }

    public void setLinkCatalog(String linkCatalog) {
        this.linkCatalog = linkCatalog;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getOuterCatalog() {
        return outerCatalog;
    }

    public void setOuterCatalog(String outerCatalog) {
        this.outerCatalog = outerCatalog;
    }

    public String getIntroduceUrl() {
        return introduceUrl;
    }

    public void setIntroduceUrl(String introduceUrl) {
        this.introduceUrl = introduceUrl;
    }

    public Object getChildren() {
        return children;
    }

    public void setChildren(Object children) {
        this.children = children;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}

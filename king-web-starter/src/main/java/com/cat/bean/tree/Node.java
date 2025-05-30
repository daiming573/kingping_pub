package com.cat.bean.tree;

import java.io.Serializable;

public class Node implements Serializable {

    private static final long serialVersionUID = 5727725401495082381L;
    /**
     * 编号
     */
    private Integer id;

    /**
     * 目录编号
     */
    private Integer catalogId;

    /**
     * 父节点
     */
    private Integer parentId;

    /**
     * 有效性(N:无效，Y:有效)
     */
    private String isValid;

    /**
     * 代码
     */
    private String code;

    /**
     * 英文名称
     */
    private String name;

    /**
     * 中文名称
     */
    private String label;

    /**
     * 含有子节点(Y是，N否)
     */
    private String hasChild;

    /**
     * 类型[菜单:MENU；子菜单:SUBMENU] 子菜单[文件夹:FOLDER；表单:FORM]
     */
    private String typeName;

    /**
     * 图标名称
     */
    private String iconName;

    /**
     * 显示顺序
     */
    private Integer dispOrder;

    /**
     * 关联目录:左边加载的树目录code
     */
    private String linkCatalog;

    /**
     * 挂载外部树
     */
    private String outerCatalog;
    /**
     * 转向地址
     */
    private String doRedirect;
    /**
     * 调度地址
     */
    private String doInvoke;
    /**
     * 介绍调度地址
     */
    private String doIntroduce;

    private boolean checked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHasChild() {
        return hasChild;
    }

    public void setHasChild(String hasChild) {
        this.hasChild = hasChild;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getLinkCatalog() {
        return linkCatalog;
    }

    public void setLinkCatalog(String linkCatalog) {
        this.linkCatalog = linkCatalog;
    }

    public String getDoRedirect() {
        return doRedirect;
    }

    public void setDoRedirect(String doRedirect) {
        this.doRedirect = doRedirect;
    }

    public String getDoInvoke() {
        return doInvoke;
    }

    public void setDoInvoke(String doInvoke) {
        this.doInvoke = doInvoke;
    }

    public String getOuterCatalog() {
        return outerCatalog;
    }

    public void setOuterCatalog(String outerCatalog) {
        this.outerCatalog = outerCatalog;
    }

    public String getDoIntroduce() {
        return doIntroduce;
    }

    public void setDoIntroduce(String doIntroduce) {
        this.doIntroduce = doIntroduce;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}

package com.cat.sy.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author daiming
 * @since 2019-11-20
 */
@TableName("sy_catalog_node")
public class SyCatalogNode extends Model<SyCatalogNode> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("uuid")
    private String uuid;
    /**
     * 菜单ID
     */
    @TableField("catalog_id")
    private Integer catalogId;
    /**
     * 父级菜单ID
     */
    @TableField("parent_id")
    private Integer parentId;
    /**
     * 是否有效，Y-合法，N-非法
     */
    @TableField("is_valid")
    private String isValid;
    /**
     * 菜单英文编号
     */
    @TableField("code")
    private String code;
    /**
     * 菜单编号
     */
    @TableField("name")
    private String name;
    /**
     * 菜单中文名称
     */
    @TableField("label")
    private String label;
    /**
     * 是否有子节点，Y-有子节点，N-无子节点
     */
    @TableField("has_child")
    private String hasChild;
    /**
     * 类型，MENU-菜单
     */
    @TableField("type_name")
    private String typeName;
    /**
     * icon名称
     */
    @TableField("icon_name")
    private String iconName;
    /**
     * 排序
     */
    @TableField("disp_order")
    private Integer dispOrder;
    @TableField("link_catalog")
    private String linkCatalog;
    @TableField("outer_catalog")
    private String outerCatalog;
    @TableField("do_redirect")
    private String doRedirect;
    /**
     * 跳转链接
     */
    @TableField("do_invoke")
    private String doInvoke;
    /**
     * 介绍动作
     */
    @TableField("do_introduce")
    private String doIntroduce;
    @TableField("create_user_id")
    private Integer createUserId;
    @TableField("note")
    private String note;
    @TableField("create_time")
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getOuterCatalog() {
        return outerCatalog;
    }

    public void setOuterCatalog(String outerCatalog) {
        this.outerCatalog = outerCatalog;
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

    public String getDoIntroduce() {
        return doIntroduce;
    }

    public void setDoIntroduce(String doIntroduce) {
        this.doIntroduce = doIntroduce;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SyCatalogNode{" + "id=" + id + ", uuid=" + uuid + ", catalogId=" + catalogId + ", parentId=" + parentId + ", isValid=" + isValid + ", code=" + code + ", name=" + name + ", label=" + label + ", hasChild=" + hasChild + ", typeName=" + typeName + ", iconName=" + iconName + ", dispOrder=" + dispOrder + ", linkCatalog=" + linkCatalog + ", outerCatalog=" + outerCatalog + ", doRedirect=" + doRedirect + ", doInvoke=" + doInvoke + ", doIntroduce=" + doIntroduce + ", createUserId=" + createUserId + ", note=" + note + ", createTime=" + createTime + "}";
    }
}

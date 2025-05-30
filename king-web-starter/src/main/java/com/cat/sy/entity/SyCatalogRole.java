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
@TableName("sy_catalog_role")
public class SyCatalogRole extends Model<SyCatalogRole> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 是否有效，Y-合法，N-非法
     */
    @TableField("is_valid")
    private String isValid;
    /**
     * 菜单节点ID
     */
    @TableField("node_ids")
    private String nodeIds;
    /**
     * 有权限的菜单节点ID
     */
    @TableField("power_node_ids")
    private String powerNodeIds;
    /**
     * 角色ID
     */
    @TableField("role_id")
    private Integer roleId;
    @TableField("create_user_id")
    private Integer createUserId;
    @TableField("create_time")
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(String nodeIds) {
        this.nodeIds = nodeIds;
    }

    public String getPowerNodeIds() {
        return powerNodeIds;
    }

    public void setPowerNodeIds(String powerNodeIds) {
        this.powerNodeIds = powerNodeIds;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
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
        return "SyCatalogRole{" + "id=" + id + ", isValid=" + isValid + ", nodeIds=" + nodeIds + ", powerNodeIds=" + powerNodeIds + ", roleId=" + roleId + ", createUserId=" + createUserId + ", createTime=" + createTime + "}";
    }
}

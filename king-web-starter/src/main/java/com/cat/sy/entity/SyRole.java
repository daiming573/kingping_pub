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
@TableName("sy_role")
public class SyRole extends Model<SyRole> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("uuid")
    private String uuid;
    /**
     * 是否有效，Y-合法，N-非法
     */
    @TableField("is_valid")
    private String isValid;
    /**
     * 角色编号
     */
    @TableField("code")
    private String code;
    @TableField("type")
    private String type;
    /**
     * 角色英文名称
     */
    @TableField("name")
    private String name;
    /**
     * 角色中文名称
     */
    @TableField("label")
    private String label;
    @TableField("icon_name")
    private String iconName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
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
        return "SyRole{" + "id=" + id + ", uuid=" + uuid + ", isValid=" + isValid + ", code=" + code + ", type=" + type + ", name=" + name + ", label=" + label + ", iconName=" + iconName + ", note=" + note + ", createTime=" + createTime + "}";
    }
}

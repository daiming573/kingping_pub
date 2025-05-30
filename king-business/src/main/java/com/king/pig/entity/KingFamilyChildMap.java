package com.king.pig.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author daiming
 * @since 2019-11-30
 */
@TableName("t_king_family_child_map")
public class KingFamilyChildMap extends Model<KingFamilyChildMap> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("family_id")
    private Integer familyId;
    @TableField("child_id")
    private Integer childId;
    @TableField("is_deleted")
    private Integer isDeleted;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public Integer getIsDeleted() { return isDeleted; }

    public void setIsDeleted(Integer isDeleted) { this.isDeleted = isDeleted; }

    public static final String ID = "id";

    public static final String FAMILY_ID = "family_id";

    public static final String CHILD_ID = "child_id";

    public static final String IS_DELETED = "is_deleted";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "KingFamilyChildMap{" +
        "id=" + id +
        ", familyId=" + familyId +
        ", childId=" + childId +
        ", isDeleted=" + isDeleted +
        "}";
    }
}

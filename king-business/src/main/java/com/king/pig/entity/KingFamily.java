package com.king.pig.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.time.LocalDateTime;
import java.util.Date;
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
@TableName("t_king_family")
public class KingFamily extends Model<KingFamily> {

    private static final long serialVersionUID = 1L;

    /**
     * 家庭ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 家庭名称
     */
    @TableField("family_name")
    private String familyName;
    /**
     * 家庭账户
     */
    @TableField("family_account")
    private Integer familyAccount;
    /**
     * 家庭状态0-正常，1-停用
     */
    private Integer status;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Integer getFamilyAccount() {
        return familyAccount;
    }

    public void setFamilyAccount(Integer familyAccount) {
        this.familyAccount = familyAccount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static final String ID = "id";

    public static final String FAMILY_NAME = "family_name";

    public static final String FAMILY_ACCOUNT = "family_account";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "KingFamily{" +
        "id=" + id +
        ", familyName=" + familyName +
        ", familyAccount=" + familyAccount +
        ", status=" + status +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}

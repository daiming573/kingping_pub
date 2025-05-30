package com.king.pig.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author daiming
 * @since 2019-11-22
 */
@TableName("t_king_child_account_record")
public class KingChildAccountRecord extends Model<KingChildAccountRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("child_id")
    private Integer childId;
    /**
     * 类型，income-收入，pay-支出，task-任务，reward-奖励
     */
    private String type;
    /**
     * 账户收入，单位分
     */
    @TableField("account_in")
    private Integer accountIn;
    /**
     * 账户支出，单位分
     */
    @TableField("account_out")
    private Integer accountOut;
    /**
     * 任务状态，undo-未完成，finish-已完成
     */
    private String status;
    /**
     * 说明
     */
    private String notes;
    /**
     * 当前日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("current_date")
    private Date currentDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("update_time")
    private Date updateTime;
    @TableField("create_user_id")
    private String createUserId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAccountIn() {
        return accountIn;
    }

    public void setAccountIn(Integer accountIn) {
        this.accountIn = accountIn;
    }

    public Integer getAccountOut() {
        return accountOut;
    }

    public void setAccountOut(Integer accountOut) {
        this.accountOut = accountOut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
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

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public static final String ID = "id";

    public static final String CHILD_ID = "child_id";

    public static final String TYPE = "type";

    public static final String ACCOUNT_IN = "account_in";

    public static final String ACCOUNT_OUT = "account_out";

    public static final String STATUS = "status";

    public static final String NOTES = "notes";

    public static final String CURRENT_DATE = "current_date";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_USER_ID = "create_user_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "KingChildAccountRecord{"
                + "id=" + id + ", childId="
                + childId + ", type=" + type
                + ", accountIn=" + accountIn
                + ", accountOut=" + accountOut
                + ", status=" + status
                + ", notes=" + notes
                + ", currentDate=" + currentDate
                + ", createTime=" + createTime
                + ", updateTime=" + updateTime
                + ", createUserId=" + createUserId
                + "}";
    }
}

package com.king.pig.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author daiming
 * @since 2019-11-22
 */
@TableName("t_king_family_child")
public class KingFamilyChild extends Model<KingFamilyChild> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     *  昵称
     */
    @TableField("nick_name")
    private String nickName;
    /**
     * 性别，F-女性，M-男性
     */
    private String sex;
    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;
    /**
     * 账户总金额，单位分
     */
    @TableField("account_total")
    private Integer accountTotal;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("update_time")
    private Date updateTime;
    @TableField(exist = false)
    private Integer familyId;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAccountTotal() {
        return accountTotal;
    }

    public void setAccountTotal(Integer accountTotal) {
        this.accountTotal = accountTotal;
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

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    public static final String ID = "id";

    public static final String NICK_NAME = "nick_name";

    public static final String SEX = "sex";

    public static final String BIRTHDAY = "birthday";

    public static final String ACCOUNT_TOTAL = "account_total";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "KingFamilyChild{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", accountTotal=" + accountTotal +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", familyId=" + familyId +
                '}';
    }
}

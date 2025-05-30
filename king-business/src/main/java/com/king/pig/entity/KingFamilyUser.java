package com.king.pig.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author daiming
 * @since 2019-11-22
 */
@TableName("t_king_family_user")
public class KingFamilyUser extends Model<KingFamilyUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户UID
     */
    @TableField("user_id")
    private String userId;
    @TableField("union_id")
    private String unionId;
    @TableField("open_id")
    private String openId;
    /**
     * 身份，F-父亲，M-母亲
     */
    @TableField("user_type")
    private String userType;
    @TableField("nick_name")
    private String nickName;
    @TableField("family_id")
    private Integer familyId;
    @TableField("mobile")
    private String mobile;
    @TableField("invite_code")
    private String inviteCode;
    /**
     * 性别，F-女性，M-男性
     */
    private String sex;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("update_time")
    private Date updateTime;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserType() { return userType; }

    public void setUserType(String userType) { this.userType = userType; }

    public String getNickName() { return nickName; }

    public void setNickName(String nickName) { this.nickName = nickName; }

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

    public Integer getFamilyId() { return familyId; }

    public void setFamilyId(Integer familyId) { this.familyId = familyId; }

    public String getMobile() { return mobile; }

    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getInviteCode() { return inviteCode; }

    public void setInviteCode(String inviteCode) { this.inviteCode = inviteCode; }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String UNION_ID = "union_id";

    public static final String OPEN_ID = "open_id";

    public static final String USER_TYPE = "user_type";

    public static final String FAMILY_ID = "family_id";

    public static final String MOBILE = "mobile";

    public static final String INVITE_CODE = "invite_code";

    public static final String NICK_NAME = "nick_name";

    public static final String SEX = "sex";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "KingUser{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", unionId='" + unionId + '\'' +
                ", openId='" + openId + '\'' +
                ", userType='" + userType + '\'' +
                ", nickName='" + nickName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", familyId=" + familyId +
                ", mobile='" + mobile + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}

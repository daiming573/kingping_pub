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
@TableName("sy_user")
public class SyUser extends Model<SyUser> {

    private static final long serialVersionUID = 1L;

    public static final String status_yes = "1";

    public static final String status_no = "2";

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 登录帐号
     */
    @TableField("user_name")
    private String userName;
    /**
     * 登录密码
     */
    @TableField("password")
    private String password;
    /**
     * 中文名字
     */
    @TableField("name")
    private String name;
    /**
     * 手机号码
     */
    @TableField("mobile")
    private String mobile;
    /**
     * 1 启用 2 不启用
     */
    @TableField("status")
    private String status;
    /**
     * 证件
     */
    @TableField("id_number")
    private String idNumber;
    /**
     * 男Male: M;女Female:F
     */
    @TableField("sex_cd")
    private String sexCd;
    /**
     * 生日
     */
    @TableField("birthday")
    private Date birthday;
    @TableField("note")
    private String note;
    /**
     * 短信发送状态
     */
    @TableField("msg_status")
    private String msgStatus;
    @TableField("user_id")
    private String userId;
    @TableField("token")
    private String token;
    @TableField("token_time")
    private Date tokenTime;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSexCd() {
        return sexCd;
    }

    public void setSexCd(String sexCd) {
        this.sexCd = sexCd;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenTime() {
        return tokenTime;
    }

    public void setTokenTime(Date tokenTime) {
        this.tokenTime = tokenTime;
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
        return "SyUser{" + "id=" + id + ", userName=" + userName + ", password=" + password + ", name=" + name + ", mobile=" + mobile + ", status=" + status + ", idNumber=" + idNumber + ", sexCd=" + sexCd + ", birthday=" + birthday + ", note=" + note + ", msgStatus=" + msgStatus + ", userId=" + userId + ", token=" + token + ", tokenTime=" + tokenTime + ", createUserId=" + createUserId + ", createTime=" + createTime + "}";
    }
}

package com.king.wechat.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author daiming
 * @since 2019-11-24
 */
@TableName("t_wx_miniapp_list")
public class WxMiniappList extends Model<WxMiniappList> {

    //启用
    public static final Integer STATUS_ON = 0;
    //停用
    public static final Integer STATUS_OFF = 1;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 小程序编号
     */
    @TableField("miniapp_code")
    private String miniappCode;
    /**
     * 小程序名称
     */
    @TableField("miniapp_name")
    private String miniappName;
    /**
     * 小程序APP_ID
     */
    @TableField("app_id")
    private String appId;
    /**
     * 小程序密钥
     */
    @TableField("app_secret")
    private String appSecret;
    /**
     * 状态，0-启用，1-停用
     */
    private Integer status;
    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("update_time")
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMiniappCode() {
        return miniappCode;
    }

    public void setMiniappCode(String miniappCode) {
        this.miniappCode = miniappCode;
    }

    public String getMiniappName() {
        return miniappName;
    }

    public void setMiniappName(String miniappName) {
        this.miniappName = miniappName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
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

    public static final String MINIAPP_CODE = "miniapp_code";

    public static final String MINIAPP_NAME = "miniapp_name";

    public static final String APP_ID = "app_id";

    public static final String APP_SECRET = "app_secret";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WxMiniappList{" +
                "id=" + id +
                ", miniappCode=" + miniappCode +
                ", miniappName=" + miniappName +
                ", appId=" + appId +
                ", appSecret=" + appSecret +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}

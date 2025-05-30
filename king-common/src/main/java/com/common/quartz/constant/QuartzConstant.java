package com.common.quartz.constant;

/**
 * @Program: king
 * @Description: quatz常量
 * @Author: daiming5
 * @Date: 2021-03-06 11:00
 * @Version 1.0
 **/
public class QuartzConstant {

    /**
     * 定时任务前缀
     */
    public static final String JOB_SUFFIX = "Job";

    /**
     * quzrt分组后缀
     */
    public static final String GROUP_SUFFIX = "Group";

    /**
     * 触发器后缀
     */
    public static final String TRIGGER_SUFFIX = "Trigger";

    /** 定时2小时刷新小程序token cron表达式*/
    public static final String ACCESS_TOKEN_REFRESH_CRON = "0 0 0/2 * * ?";
}

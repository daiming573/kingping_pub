package com.config;

import com.common.quartz.constant.QuartzConstant;
import com.common.quartz.service.IQuartzService;
import com.king.wechat.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author daiming5
 */
@Component
public class QuartzInit implements CommandLineRunner {

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private IQuartzService quartzService;

    /**
     * 刷新token 定时任务执行方法
     */
    public static final String METHOD_NAME = "refreshMiniAppAccessToken";
    /**
     * 定时任务执行类名
     */
    public static final String JOB_CLASS_NAME = "accessTokenServiceImpl";

    @Override
    public void run(String... args) throws Exception {

        //初始化token
        accessTokenService.refreshMiniAppAccessToken();

        String jobName = METHOD_NAME + QuartzConstant.JOB_SUFFIX;
        String jobGroupName = jobName + QuartzConstant.GROUP_SUFFIX;
        String triggerName = METHOD_NAME + QuartzConstant.TRIGGER_SUFFIX;
        String triggerGroupName = triggerName + QuartzConstant.GROUP_SUFFIX;

        quartzService.removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
        //添加定时任务 刷新小程序accessToken
        quartzService.addJob(jobName, jobGroupName, JOB_CLASS_NAME, METHOD_NAME, triggerName, triggerGroupName,
                QuartzConstant.ACCESS_TOKEN_REFRESH_CRON, null);

    }
}

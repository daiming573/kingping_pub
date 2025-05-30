//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.common.quartz.service.impl;

import com.common.quartz.InvokingJobDetailFactoryParam;
import com.common.quartz.QuartzUtil;
import com.common.quartz.service.IQuartzService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

@Component("quartzService")
public class QuartzServiceImpl implements IQuartzService, ApplicationContextAware {
    private Logger log = LoggerFactory.getLogger(QuartzServiceImpl.class);

    @Autowired
    private Scheduler scheduler;
    private static ApplicationContext applicationContext;

    public QuartzServiceImpl() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        QuartzServiceImpl.applicationContext = applicationContext;
    }

    @Override
    public void triggerJob(String jobName) {
        this.triggerJob(jobName, "");
    }

    @Override
    public void triggerJob(String jobName, String jobGroupName) {
        try {
            if (StringUtils.isEmpty(jobGroupName)) {
                jobGroupName = "DEFAULT";
            }

            this.scheduler.triggerJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (Exception var4) {
            this.log.error("trigger job error：", var4);
        }

    }

    @Override
    public void addJob(String jobName, String jobClassName, String method, String cron) {
        this.addJob(jobName, "", jobClassName, method, cron, (Boolean) true, (Boolean) false, (Object) null);
    }

    @Override
    public void addStatefulJob(String jobName, String triggerName, String jobClassName, String method, String cron, Boolean startUpFlag) {
        try {
            JobDetailFactoryBean jobDetailFactoryBean = QuartzUtil.createStatafulJobDetail(jobName, jobClassName, method);
            jobDetailFactoryBean.afterPropertiesSet();
            JobDetail jobDetail = jobDetailFactoryBean.getObject();
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(triggerName, (String) null);
            triggerBuilder.startNow();
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            this.scheduler.scheduleJob(jobDetail, trigger);
            if (startUpFlag) {
                JobKey jobkey = new JobKey(jobName);
                this.scheduler.triggerJob(jobkey);
            }
        } catch (Exception var12) {
            this.log.error("add job error:", var12);
        }

    }

    @Override
    public void addJob(String jobName, String triggerName, String jobClassName, String method, String cron, Boolean startUpFlag, Boolean concurrent, Object param) {
        if (StringUtils.isEmpty(jobName)) {
            jobName = jobClassName;
        }

        if (StringUtils.isEmpty(triggerName)) {
            triggerName = jobName + "_trigger";
        }

        this.log.info("addJob jobName {}, jobGroupName {}, jobClassName {}, method {}, triggerName {}, triggerGroupName {}, cron {}", jobName, null, jobClassName, method, triggerName, null, cron);

        try {
            JobDetailFactoryBean factoryBean = null;
            if (concurrent != null && concurrent) {
                factoryBean = QuartzUtil.createJobDetail(InvokingJobDetailFactoryParam.class, jobName, jobClassName, method);
            } else {
                factoryBean = QuartzUtil.createStatafulJobDetail(jobName, jobClassName, method);
            }

            factoryBean.afterPropertiesSet();
            JobDetail jobDetail = factoryBean.getObject();
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(triggerName, (String) null);
            triggerBuilder.startNow();
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            if (param != null) {
                jobDetail.getJobDataMap().put("JOB_PARAM_KEY", param);
            }

            this.scheduler.scheduleJob(jobDetail, trigger);
            if (startUpFlag) {
                JobKey jobkey = new JobKey(jobName);
                this.scheduler.triggerJob(jobkey);
            }
        } catch (Exception var14) {
            this.log.error("add job error:", var14);
        }

    }

    @Override
    public void addJob(String jobName, String jobClassName, String method, String triggerName, String cron) {
        this.addJob(jobName, (String) null, jobClassName, method, triggerName, (String) null, (String) cron, (String) null);
    }

    @Override
    public void addJob(String jobName, String jobClassName, String method, String triggerName, String cron, String param) {
        this.addJob(jobName, (String) null, jobClassName, method, triggerName, (String) null, (String) cron, (String) param);
    }

    @Override
    public void addtriggerWithExistsJob(String jobName, String jobClassName, String method, String triggerName, String cron) {
        this.addtriggerWithExistsJob(jobName, (String) null, jobClassName, method, triggerName, (String) null, cron);
    }

    @Override
    public void addJob(Class<? extends Job> jobClass, String jobName, String jobGroupName, Map<String, Object> jobDataMap, String triggerName, String triggerGroupName, Date startTime, Long intervalMillisecond, Integer repeatCount) {
        this.log.info("addJob jobClass {}, jobName {}, jobGroupName {}, jobDataMap {}, triggerName {}, triggerGroupName {}, startTime {}, intervalMillisecond {}, repeatCount{}", jobClass, jobName, jobGroupName, jobDataMap, triggerName, triggerGroupName, startTime, intervalMillisecond, repeatCount);

        try {
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
            if (jobName != null && jobGroupName != null) {
                jobBuilder.withIdentity(jobName, jobGroupName);
            }

            if (jobDataMap != null && jobDataMap.size() > 0) {
                jobBuilder.setJobData(new JobDataMap(jobDataMap));
            }

            JobDetail jobDetail = jobBuilder.build();
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            if (startTime != null) {
                triggerBuilder.startAt(startTime);
            } else {
                triggerBuilder.startNow();
            }

            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
            if (intervalMillisecond != null && intervalMillisecond >= 0L) {
                simpleScheduleBuilder.withIntervalInMilliseconds(intervalMillisecond);
            }

            if (repeatCount == null) {
                simpleScheduleBuilder = simpleScheduleBuilder.repeatForever();
            } else if (repeatCount >= 0) {
                simpleScheduleBuilder = simpleScheduleBuilder.withRepeatCount(repeatCount);
            }

            triggerBuilder.withSchedule(simpleScheduleBuilder);
            SimpleTrigger trigger = (SimpleTrigger) triggerBuilder.build();
            this.scheduler.scheduleJob(jobDetail, trigger);
            if (!this.scheduler.isStarted()) {
                this.scheduler.start();
            }
        } catch (Exception var15) {
            this.log.error("add job error:", var15);
        }

    }

    @Override
    public void addtriggerWithExistsJob(String jobName, String jobGroupName, String jobClassName, String method, String triggerName, String triggerGroupName, Long intervalMillisecond, Integer repeatCount) {
        this.log.info("addtriggerWithExistsJob jobName {}, jobGroupName {}, jobClassName {}, method {}, triggerName {}, triggerGroupName {}, cron {}", jobName, jobGroupName, jobClassName, method, triggerName, triggerGroupName, intervalMillisecond);

        try {
            JobDetail job = this.scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
            if (intervalMillisecond != null && intervalMillisecond >= 0L) {
                simpleScheduleBuilder.withIntervalInMilliseconds(intervalMillisecond);
            }

            if (repeatCount == null) {
                simpleScheduleBuilder = simpleScheduleBuilder.repeatForever();
            } else if (repeatCount >= 0) {
                simpleScheduleBuilder = simpleScheduleBuilder.withRepeatCount(repeatCount);
            }

            triggerBuilder.withSchedule(simpleScheduleBuilder);
            triggerBuilder.forJob(job);
            Trigger trigger = triggerBuilder.build();
            this.scheduler.scheduleJob(trigger);
        } catch (Exception var13) {
            this.log.error("addtriggerWithExistsJob error:", var13);
        }

    }

    public void addtriggerWithExistsJob(String jobName, String jobGroupName, String jobClassName, String method, String triggerName, String triggerGroupName, String cron) {
        this.log.info("addtriggerWithExistsJob jobName {}, jobGroupName {}, jobClassName {}, method {}, triggerName {}, triggerGroupName {}, cron {}", jobName, jobGroupName, jobClassName, method, triggerName, triggerGroupName, cron);

        try {
            JobDetail job = this.scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            triggerBuilder.forJob(job);
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            this.scheduler.scheduleJob(trigger);
        } catch (Exception var11) {
            this.log.error("addtriggerWithExistsJob error:", var11);
        }

    }

    @Override
    public void addJob(String jobName, String jobGroupName, String jobClassName, String method, String triggerName, String triggerGroupName, String cron, String param) {
        this.log.info("addJob jobName {}, jobGroupName {}, jobClassName {}, method {}, triggerName {}, triggerGroupName {}, cron {}", jobName, jobGroupName, jobClassName, method, triggerName, triggerGroupName, cron);

        try {
            JobDetailFactoryBean jobDetailFactoryBean = QuartzUtil.createJobDetail(jobName, jobClassName, method);
            jobDetailFactoryBean.afterPropertiesSet();
            JobDetail jobDetail = jobDetailFactoryBean.getObject();
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            if (param != null) {
                jobDetail.getJobDataMap().put("JOB_PARAM_KEY", param);
            }

            this.scheduler.scheduleJob(jobDetail, trigger);
            if (!this.scheduler.isStarted()) {
                this.scheduler.start();
            }
        } catch (Exception var13) {
            this.log.error("add job error:", var13);
        }

    }

    @Override
    public void addJobBySimpleTriggle(String jobName, String jobGroupName, String jobClassName, String method, String triggerName, String triggerGroupName, int minutes, String param) {
        this.log.info("addJobBySimpleTriggle jobName {}, jobGroupName {}, jobClassName {}, method {}, triggerName {}, triggerGroupName {}, minutes {}, param {}", jobName, jobGroupName, jobClassName, method, triggerName, triggerGroupName, minutes, param);

        try {
            JobDetailFactoryBean jobDetailFactoryBean = QuartzUtil.createJobDetail(jobName, jobClassName, method);
            jobDetailFactoryBean.afterPropertiesSet();
            JobDetail jobDetail = jobDetailFactoryBean.getObject();
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            triggerBuilder.withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(minutes));
            SimpleTrigger trigger = (SimpleTrigger) triggerBuilder.build();
            if (param != null) {
                jobDetail.getJobDataMap().put("JOB_PARAM_KEY", param);
            }

            this.scheduler.scheduleJob(jobDetail, trigger);
            if (!this.scheduler.isStarted()) {
                this.scheduler.start();
            }
        } catch (Exception var13) {
            this.log.error("addJobBySimpleTriggle error:", var13);
        }

    }

    @Override
    public void addJobBySimpleTriggleSeconds(String jobName, String jobClassName, String method, Integer seconds, Integer repeatCount, Object param) {
        String triggerName = jobName + "_trigger";
        this.addJobBySimpleTriggleSeconds(InvokingJobDetailFactoryParam.class, jobName, (String) null, jobClassName, method, triggerName, (String) null, seconds, repeatCount, param);
    }

    @Override
    public void addJobBySimpleTriggleSeconds(Class<? extends Job> jobClass, String jobName, String jobGroupName, String jobClassName, String method, String triggerName, String triggerGroupName, Integer seconds, Integer repeatCount, Object param) {
        this.log.info("addJobBySimpleTriggle jobName {}, jobGroupName {}, jobClassName {}, method {}, triggerName {}, triggerGroupName {}, seconds {}, repeatCount {}, param {}", jobName, jobGroupName, jobClassName, method, triggerName, triggerGroupName, seconds, repeatCount, param);

        try {
            JobDetailFactoryBean jobDetailFactoryBean = QuartzUtil.createJobDetail(jobClass, jobName, jobClassName, method);
            jobDetailFactoryBean.afterPropertiesSet();
            JobDetail jobDetail = jobDetailFactoryBean.getObject();
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
            if (seconds != null && seconds >= 0) {
                simpleScheduleBuilder.withIntervalInSeconds(seconds);
            }

            if (repeatCount == null) {
                simpleScheduleBuilder = simpleScheduleBuilder.repeatForever();
            } else if (repeatCount >= 0) {
                simpleScheduleBuilder = simpleScheduleBuilder.withRepeatCount(repeatCount);
            }

            triggerBuilder.withSchedule(simpleScheduleBuilder);
            SimpleTrigger trigger = (SimpleTrigger) triggerBuilder.build();
            if (param != null) {
                jobDetail.getJobDataMap().put("JOB_PARAM_KEY", param);
            }

            this.scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception var16) {
            this.log.error("addJobBySimpleTriggleSeconds error:", var16);
        }

    }

    @Override
    public void modifyJobTime(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String cron) {
        this.log.info("modifyJobTime jobName {}, jobGroupName {}, triggerName {}, triggerGroupName {}, cron {}", jobName, jobGroupName, triggerName, triggerGroupName, cron);

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) this.scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }

            String oldTime = trigger.getCronExpression();
            if (!cron.equalsIgnoreCase(oldTime)) {
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                trigger = (CronTrigger) triggerBuilder.build();
                this.scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (Exception var10) {
            this.log.error("modifyJobTime error:", var10);
        }

    }

    @Override
    public boolean checkJobExist(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        boolean result = false;
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

        try {
            if (this.scheduler.checkExists(jobKey) && this.scheduler.checkExists(triggerKey)) {
                result = true;
            }
        } catch (SchedulerException var9) {
            this.log.error("check job exist error:", var9);
        }

        return result;
    }

    @Override
    public void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            this.scheduler.pauseTrigger(triggerKey);
            this.scheduler.unscheduleJob(triggerKey);
            this.scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (Exception var6) {
            this.log.error("remove job error：", var6);
        }

    }

    @Override
    public void startJobs() {
        try {
            this.scheduler.start();
        } catch (Exception var2) {
            this.log.error("start job error：", var2);
        }

    }

    @Override
    public void shutdownJobs() {
        try {
            if (!this.scheduler.isShutdown()) {
                this.scheduler.shutdown();
            }
        } catch (Exception var2) {
            this.log.error("shutdown job error：", var2);
        }

    }
}

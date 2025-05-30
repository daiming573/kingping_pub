//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.common.quartz.cron;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

import java.io.Serializable;
import java.util.Map;

public abstract class BaseCronTrigger extends CronTriggerFactoryBean implements Serializable {
    public BaseCronTrigger() {
    }

    public void init() {
        JobBuilder jobBuilder = JobBuilder.newJob(this.getTargetObject().getClass());
        if (this.getJobName() != null && this.getJobGroupName() != null) {
            jobBuilder.withIdentity(this.getJobName(), this.getJobGroupName());
        }

        if (this.getDataMap() != null && this.getDataMap().size() > 0) {
            jobBuilder.setJobData(new JobDataMap(this.getDataMap()));
        }

        jobBuilder.storeDurably();
        JobDetail jobdetail = jobBuilder.build();
        this.setJobDetail(jobdetail);
        this.setName(this.getTriggerName());
        this.setGroup(this.getTriggerGroup());
        this.setCronExpression(this.getCronExpression());
    }

    public String getJobGroupName() {
        return "default";
    }

    public String getTriggerGroup() {
        return "default";
    }

    public String getTriggerName() {
        return this.getClass().getSimpleName();
    }

    public abstract String getJobName();

    public abstract String getCronExpression();

    public abstract Map<String, Object> getDataMap();

    public abstract Job getTargetObject();
}

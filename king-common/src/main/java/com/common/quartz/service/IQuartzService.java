//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.common.quartz.service;

import org.quartz.Job;

import java.util.Date;
import java.util.Map;

public interface IQuartzService {
    void triggerJob(String jobName);

    void triggerJob(String jobName, String jobGroupName);

    void addJob(String jobName, String jobClassName, String method, String cron);

    void addJob(String jobName, String triggerName, String jobClassName, String method, String cron, Boolean startUpFlag, Boolean concurrent, Object param);

    void addStatefulJob(String jobName, String triggerName, String jobClassName, String method, String cron, Boolean startUpFlag);

    void addJob(Class<? extends Job> jobClass, String jobName, String jobGroupName, Map<String, Object> jobDataMap, String triggerName, String triggerGroupName, Date startTime, Long intervalMillisecond, Integer repeatCount);

    void addJob(String jobName, String jobClassName, String method, String triggerName, String cron);

    void addJob(String jobName, String jobClassName, String method, String triggerName, String cron, String param);

    void addtriggerWithExistsJob(String jobName, String jobGroupName, String jobClassName, String method, String triggerName, String triggerGroupName, Long intervalMillisecond, Integer repeatCount);

    void addtriggerWithExistsJob(String jobName, String jobClassName, String method, String triggerName, String cron);

    void addJob(String jobName, String jobGroupName, String jobClassName, String method, String triggerName, String triggerGroupName, String cron, String param);

    void addJobBySimpleTriggle(String jobName, String jobGroupName, String jobClassName, String method, String triggerName, String triggerGroupName, int minutes, String param);

    void addJobBySimpleTriggleSeconds(String jobName, String jobClassName, String method, Integer seconds, Integer repeatCount, Object param);

    void addJobBySimpleTriggleSeconds(Class<? extends Job> jobClass, String jobName, String jobGroupName, String jobClassName, String method, String triggerName, String triggerGroupName, Integer seconds, Integer repeatCount, Object param);

    void modifyJobTime(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String cron);

    boolean checkJobExist(String jobName, String jobGroupName, String triggerName, String triggerGroupName);

    void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName);

    void startJobs();

    void shutdownJobs();
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.common.quartz;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class QuartzUtil {
    public QuartzUtil() {
    }

    public static JobDetailFactoryBean createJobDetail(String targetObject, String targetMethod) {
        return createJobDetail(InvokingJobDetailFactory.class, (String) null, targetObject, targetMethod);
    }

    public static JobDetailFactoryBean createJobDetail(String jobName, String targetObject, String targetMethod) {
        return createJobDetail(InvokingJobDetailFactory.class, jobName, targetObject, targetMethod);
    }

    public static JobDetailFactoryBean createStatafulJobDetail(String jobName, String targetObject, String targetMethod) {
        return createJobDetail(StatefulInvokingJobDetailFactoryParam.class, jobName, targetObject, targetMethod);
    }

    public static MethodInvokingJobDetailFactoryBean createJobDetail(String jobName, Object targetObject, String targetMethod) {
        MethodInvokingJobDetailFactoryBean factoryBean = new MethodInvokingJobDetailFactoryBean();
        if (StringUtils.hasLength(jobName)) {
            factoryBean.setName(jobName);
        }

        factoryBean.setTargetObject(targetObject);
        factoryBean.setTargetMethod(targetMethod);
        factoryBean.setTargetBeanName(jobName);
        return factoryBean;
    }

    public static JobDetailFactoryBean createJobDetail(Class<? extends Job> jobClass, String jobName, String targetObject, String targetMethod) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        if (StringUtils.hasLength(jobName)) {
            factoryBean.setName(jobName);
        }

        Map<String, String> map = new HashMap();
        map.put("targetObject", targetObject);
        map.put("targetMethod", targetMethod);
        factoryBean.setJobDataAsMap(map);
        return factoryBean;
    }

    public static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression(cronExpression);
        return factoryBean;
    }

    public static SimpleTriggerFactoryBean createSimpleTrigger(JobDetail jobDetail, long repeatInterval, long startDelayTime) {
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setJobDetail(jobDetail);
        simpleTriggerFactoryBean.setRepeatInterval(repeatInterval);
        simpleTriggerFactoryBean.setStartDelay(startDelayTime);
        return simpleTriggerFactoryBean;
    }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.common.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

public class InvokingJobDetailFactory extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(InvokingJobDetailFactory.class);
    private String targetObject;
    private String targetMethod;
    private ApplicationContext ctx;
    private String params;

    public InvokingJobDetailFactory() {
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            this.params = context.getMergedJobDataMap().getString("JOB_PARAM_KEY");
            Object otargetObject = this.ctx.getBean(this.targetObject);
            Method m = null;

            try {
                if (this.params != null) {
                    m = otargetObject.getClass().getMethod(this.targetMethod, String.class);
                    m.invoke(otargetObject, this.params);
                } else {
                    m = otargetObject.getClass().getMethod(this.targetMethod);
                    m.invoke(otargetObject);
                }
            } catch (NoSuchMethodException | SecurityException var5) {
                logger.error("executeInternal error ", var5);
            }

        } catch (Exception var6) {
            throw new JobExecutionException(var6);
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.ctx = applicationContext;
    }

    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }
}

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

public class InvokingJobDetailFactoryParam extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(InvokingJobDetailFactoryParam.class);
    private String targetObject;
    private String targetMethod;
    private ApplicationContext ctx;
    private Object[] params;

    public InvokingJobDetailFactoryParam() {
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            Object param = context.getMergedJobDataMap().get("JOB_PARAM_KEY");
            Object[] obj = new Object[]{param};
            Object otargetObject = this.ctx.getBean(this.targetObject);
            Method m = null;

            try {
                if (param != null) {
                    m = otargetObject.getClass().getMethod(this.targetMethod, Object.class);
                    m.invoke(otargetObject, obj);
                } else {
                    m = otargetObject.getClass().getMethod(this.targetMethod);
                    m.invoke(otargetObject);
                }
            } catch (NoSuchMethodException | SecurityException var7) {
                logger.error("executeInternal error ", var7);
            }

        } catch (Exception var8) {
            throw new JobExecutionException(var8);
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

package com.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppContext implements ApplicationContextAware {

    private static ApplicationContext context = null;

    private static final Logger logger = LoggerFactory.getLogger(AppContext.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setContext(applicationContext);
    }

    public static void setContext(ApplicationContext applicationContext) throws BeansException {
        AppContext.context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return AppContext.context;
    }

    public static <T> T getBean(String name) {
        if (context == null) {
            throw new IllegalStateException("applicaitonContext un inject");
        }
        try {
            return (T) context.getBean(name);
        } catch (BeansException e) {
            logger.error("[0x00010001] - get Bean error", e);
        }
        return null;
    }

    public static <T> T getBeanByClass(Class className) {
        if (context == null) {
            throw new IllegalStateException("applicaitonContext un inject");
        }
        try {
            return (T) context.getBean(className);
        } catch (BeansException e) {
            logger.error("get Bean by className error", e);
        }
        return null;
    }
}
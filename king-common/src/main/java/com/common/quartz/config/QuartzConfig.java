//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.common.quartz.config;

import org.quartz.CronTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class QuartzConfig {
    @Value("${quartz.db.enable:false}")
    private boolean dbEnable;
    @Value("${quartz.delay:10}")
    private int delayTime;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired(required = false)
    private CronTrigger[] triggers;
    @Autowired(required = false)
    private DataSource dataSource;

    public QuartzConfig() {
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        if (this.dbEnable && this.dataSource != null) {
            factory.setConfigLocation(new ClassPathResource("/quartz.properties"));
            factory.setDataSource(this.dataSource);
            factory.setOverwriteExistingJobs(true);
        }

        factory.setStartupDelay(this.delayTime);
        factory.setAutoStartup(true);
        factory.setApplicationContextSchedulerContextKey("applicationContext");
        factory.setTaskExecutor(this.taskExecutor);
        if (this.triggers != null && this.triggers.length > 0) {
            factory.setTriggers(this.triggers);
        }

        return factory;
    }
}

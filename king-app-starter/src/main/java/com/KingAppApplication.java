package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication(scanBasePackages = {"com.**"})
public class KingAppApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(KingAppApplication.class, args);
    }


    /**
     * 用于支持打成war包部署
     *
     * @param application   启动
     *
     * @return  SpringApplicationBuilder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(KingAppApplication.class);
    }

}

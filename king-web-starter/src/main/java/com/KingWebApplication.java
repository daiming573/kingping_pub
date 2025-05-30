package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.util.ObjectUtils;


@SpringBootApplication(scanBasePackages = {"com.**"})
public class KingWebApplication extends SpringBootServletInitializer {

    /**
     * 获取系统的环境变量
     */
    public static final String ENV_HOME = "KING_WEB_HOME";


    public static void main(String[] args) {
        String property = System.getProperty(ENV_HOME);
        if (!ObjectUtils.isEmpty(property)) {
            //springboot默认的配置文件路径
            String addClassPath = "spring.config.additional-location:classpath:/";
            //自定义的配置文件路径
            addClassPath += "," + property;
            new SpringApplicationBuilder(KingWebApplication.class)
                    .properties("spring.config.name:application", addClassPath).build().run(args);
        } else {
            SpringApplication.run(KingWebApplication.class, args);
        }
    }


    /**
     * 用于支持打成war包部署
     *
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        String property = System.getProperty(ENV_HOME);
        System.out.println("env_home:" + property);
        if (!ObjectUtils.isEmpty(property)) {
            //springboot默认的配置文件路径
            String addClassPath = "spring.config.additional-location:classpath:/";
            //自定义的配置文件路径
            addClassPath += "," + property;
            application.properties("spring.config.name:application", addClassPath).build();
        }
        return application.sources(KingWebApplication.class);
    }

}

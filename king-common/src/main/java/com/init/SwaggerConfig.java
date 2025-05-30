package com.init;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.ant;

/**
 * @author daiming5
 */
@Configuration
@EnableSwagger2
@ConditionalOnExpression("${swagger.enable:false}==true")
public class SwaggerConfig implements WebMvcConfigurer {

    @Value("${swagger.enable:false}")
    private boolean swaggerEnable;

    @Value("${swagger.app.version:1.0.0}")
    private String appVersion;

    @Value("${swagger.app.name:king}")
    private String appName;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (swaggerEnable) {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

    @Bean
    public Docket createMiniApi(RelativePathProvider relativePathProvider) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("API接口文档")
                .apiInfo(apiInfo("APP接口"))
                .directModelSubstitute(LocalDateTime.class, Date.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.king"))
                .paths(Predicates.and(ant("/api/**")))
                .build()
                .globalOperationParameters(getRestApiToken())
                .pathProvider(relativePathProvider)
                .globalResponseMessage(RequestMethod.GET, getResponseMessageList())
                .globalResponseMessage(RequestMethod.POST, getResponseMessageList())
                .enable(swaggerEnable);
    }

    @Bean
    public Docket createWebApi(RelativePathProvider relativePathProvider) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("web接口文档")
                .apiInfo(apiInfo("web接口"))
                .directModelSubstitute(LocalDateTime.class, Date.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cat"))
//                .paths(PathSelectors.any())
                .paths(PathSelectors.regex("^((?!/api/).)*$"))
                .build()
                .pathProvider(relativePathProvider)
                .globalResponseMessage(RequestMethod.GET, getResponseMessageList())
                .globalResponseMessage(RequestMethod.POST, getResponseMessageList());
    }


    /**
     * RelativePathProvider用于设置basePath
     */
    @Bean
    public RelativePathProvider relativePathProvider(ServletContext servletContext){
        return new RelativePathProvider(servletContext) {
            @Override
            public String getApplicationBasePath() {
                return "/" + contextPath;
            }
        };
    }

    /**
     * API接口请求token
     * @return  List<Parameter>
     */
    private List<Parameter> getRestApiToken() {
        Parameter build = new ParameterBuilder().name("Token")
                .description("令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .allowableValues(new AllowableRangeValues("1", "128"))
                .build();
        return Collections.singletonList(build);
    }

    /**
     * 获取List<ResponseMessage>
     *
     * @return List<ResponseMessage>
     */
    private List<ResponseMessage> getResponseMessageList() {
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(401).message("Unauthorized").build());
        responseMessageList.add(new ResponseMessageBuilder().code(403).message("Forbidden").build());
        responseMessageList.add(new ResponseMessageBuilder().code(404).message("Not Found").build());
        responseMessageList.add(new ResponseMessageBuilder().code(500).message("Server Error").build());
        return responseMessageList;
    }

    /**
     * 构建API信息
     *
     * @param title 标题
     * @return ApiInfo
     */
    private ApiInfo apiInfo(String title) {
        return new ApiInfoBuilder()
                .title(title)
                .description(appName + "接口描述")
                .version(appVersion)
                .contact(new Contact(appName, "", appName + "@qq.com"))
                .build();
    }

}

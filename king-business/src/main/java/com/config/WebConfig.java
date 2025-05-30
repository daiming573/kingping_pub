package com.config;

import com.config.interctpor.ApiInterceptor;
import com.config.interctpor.WebInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author daiming5
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${web.url.prefix:''}")
    private String webUrlPrefix;

    @Value("${api.url.prefix:'/api'}")
    private String apiPrefix;

    @Value("${api.enable:true}")
    private boolean apiEnable;

    /** 运行跨域访问的的url */
    @Value("#{'${allowed.origins:*}'.split(',')}")
    private List<String> allowedOrigins;

    /** 允许访问的请求头 */
    @Value("#{'${allowed.headers:*}'.split(',')}")
    private List<String> allowedHeaders;

    /** 允许访问的请求方法 */
    @Value("#{'${allowed.methods:*}'.split(',')}")
    private List<String> allowedMethods;

    /** 跨域配置最大时间 */
    public static final long CORS_MAX_AGE = 24 * 60 * 60;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 添加静态资源配置
         */
        //        registry.addResourceHandler("/**")
        //                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebInterceptor()).addPathPatterns(webUrlPrefix + "/**");
        if (apiEnable) {
            registry.addInterceptor(new ApiInterceptor()).addPathPatterns(apiPrefix + "/**");
        }
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        WebMvcConfigurer.super.configurePathMatch(configurer);
        //开启后缀匹配模式
        configurer.setUseRegisteredSuffixPatternMatch(true);
        //开启后缀正则匹配模式
        configurer.setUseSuffixPatternMatch(true);
    }


    /**
     * 配置跨域信息
     * @return  CorsConfiguration
     */
    private CorsConfiguration buildCorsConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 1 设置访问源地址
        corsConfiguration.setAllowedOrigins(allowedOrigins);
        // 2 设置访问源请求头
        corsConfiguration.setAllowedHeaders(allowedHeaders);
        // 3 设置访问源请求方法
        corsConfiguration.setAllowedMethods(allowedMethods);
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(CORS_MAX_AGE);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 4 对接口配置跨域设置
        source.registerCorsConfiguration("/**", buildCorsConfig());
        return new CorsFilter(source);
    }

    /**
     * 跨域支持
     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowCredentials(true)
//                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
//                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name(),
//                        HttpMethod.PUT.name(), HttpMethod.PATCH.name())
//                .maxAge(24 * 60 * 60);
//    }


    /**
     * 添加转换器
     */
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        //将我们定义的时间格式转换器添加到转换器列表中,
//        //这样jackson格式化时候但凡遇到Date类型就会转换成我们定义的格式
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setObjectMapper(ObjectMapper());
//    }

    /**
     * 更改jackson默认配置
     */
//    @Bean
//    public ObjectMapper ObjectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        // 对于空的对象转json的时候不抛出错误
//        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//        // 禁用遇到未知属性抛出异常
//        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        // 序列化BigDecimal时不使用科学计数法输出
//        objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
//        // 日期和时间格式化
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
//        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
//        objectMapper.registerModule(javaTimeModule);
//        return objectMapper;
//    }

}

package com.common.http;/**
 *
 */

import com.common.log.rest.RestClientInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author daiming5
 * @date create in 2020/10/10 14:08
 * @description RestTemplate配置
 * @project rpo
 * @version 1.0
 * @since JDK 1.8
 *
 */
@Configuration
public class RestTemplateConfig {

    private static final Logger log = LoggerFactory.getLogger(RestTemplateConfig.class);

    @Autowired
    RestClientProperty restClientProperty;

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restConfig(ClientHttpRequestFactory factory) {
        RestTemplate rt = new RestTemplate(factory);
        //设置错误处理机制
        //        rt.setErrorHandler(new ErrorResponseHandler());
        //设置拦截器
        List<ClientHttpRequestInterceptor> interceptors = rt.getInterceptors();
        interceptors.add(new RestClientInterceptor());
        rt.setInterceptors(interceptors);
        //替换默认转换器
        List<HttpMessageConverter<?>> messageConverters = rt.getMessageConverters();
        messageConverters.set(1, restStringConverter());

        boolean existFlag = false;
        for (int i = 0; i < messageConverters.size(); i++) {

            HttpMessageConverter<?> messageConverter = messageConverters.get(i);
            if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
                existFlag = true;
                messageConverters.set(i, restMappingJackson2HttpMessageConverter());
            }
        }
        if (!existFlag) {
            messageConverters.add(restMappingJackson2HttpMessageConverter());
        }

        rt.setMessageConverters(messageConverters);
        //提供对传出/传入流的缓冲,可以让响应body多次读取(如果不配置,拦截器读取了Response流,再响应数据时会返回body=null)
        rt.setRequestFactory(new BufferingClientHttpRequestFactory(factory));
        return rt;
    }


    @Bean("restRequestFactory")
    @Primary
    public ClientHttpRequestFactory requestFactory(HttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(restClientProperty.getConnectionTimeout());
        factory.setReadTimeout(restClientProperty.getReadTimeout());
        // 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
        factory.setConnectionRequestTimeout(restClientProperty.getConnectionRequestTimeout());
        factory.setHttpClient(httpClient);
        return factory;
    }


    @Bean("restHttpClient")
    @Primary
    public HttpClient httpClient(HttpClientBuilder builder) {
        return builder.build();
    }


    @Bean("restHttpClientBuilder")
    @Primary
    public HttpClientBuilder httpClientBuilder(HttpClientConnectionManager manager) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(manager);
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true));
        //设置重定向规则，自动获取重定向内容
        httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
        return httpClientBuilder;
    }


    @Bean("restConnectionManagerConfig")
    @Primary
    public HttpClientConnectionManager connectionManagerConfig() {
        HttpClientBuilder b = HttpClientBuilder.create();
        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    return true;
                }
            }).build();
        } catch (Exception e) {
            log.error("create sslContext exception is", e);
            throw new RuntimeException("create sslContext exception is", e);
        }
        b.setSSLContext(sslContext);
        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        //设置支持http和https请求
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory).build();
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        //每个主机的并发
        manager.setDefaultMaxPerRoute(restClientProperty.getDefaultMaxPerRoute());
        //整个连接池的并发
        manager.setMaxTotal(restClientProperty.getMaxTotal());
        return manager;
    }


    @Bean("restStringConverter")
    @Primary
    public StringHttpMessageConverter restStringConverter() {
        StringHttpMessageConverter stringConvert = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        stringConvert.setWriteAcceptCharset(false);

        List<MediaType> list = new ArrayList<MediaType>() {{
            addAll(stringConvert.getSupportedMediaTypes());
        }};
        list.add(0, MediaType.TEXT_PLAIN);
        stringConvert.setSupportedMediaTypes(list);
        return stringConvert;
    }


    @Bean("restMappingJackson2HttpMessageConverter")
    public MappingJackson2HttpMessageConverter restMappingJackson2HttpMessageConverter() {
        //CustomJackson2HttpMessageConverter会让响应中时间格式的注解失效
        //MappingJackson2HttpMessageConverter  messageConverter =
        //        new MappingJackson2HttpMessageConverter(new CustomJackson2HttpMessageConverter().builder()) {
        //
        //        };

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();
        DateFormat df = new StdDateFormat()
                .withTimeZone(TimeZone.getDefault())
                .withLocale(Locale.SIMPLIFIED_CHINESE)
                .withColonInTimeZone(true)
                .withLenient(true);
        objectMapper.setDateFormat(df);
        //是否需要注解拦截 处理特殊字段
//        objectMapper.setAnnotationIntrospector(new DisableSensitiveFieldIntrospector());
        messageConverter.setObjectMapper(objectMapper);

        List<MediaType> list = new ArrayList<>(messageConverter.getSupportedMediaTypes());
        list.add(new MediaType("text", "json", StandardCharsets.UTF_8));
//        list.add(new MediaType("application", "octet-stream", StandardCharsets.UTF_8));
        list.add(MediaType.TEXT_PLAIN);
        list.add(MediaType.APPLICATION_OCTET_STREAM);
        messageConverter.setSupportedMediaTypes(list);
        return messageConverter;
    }

}

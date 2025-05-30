package com.init;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 数据源相关配置
 *
 */
@Configuration
public class DataSourceConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() throws Exception {
        return new DruidDataSource();
//        return _initDataSource(true);
    }

}

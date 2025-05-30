package com.init;

import com.baomidou.mybatisplus.mapper.ISqlInjector;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@MapperScan(basePackages = {"com.**.dao*"})
public class MybatisPlusConfig {

    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
    @Bean
    @Profile("dev")
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * 自定义分表插件
     *
     */
    //    @Bean
    //    public TableSplitInterceptor tableSplitInterceptor(){
    //        return new TableSplitInterceptor();
    //    }

    /**
     * 乐观锁插件
     *
     * @return
     */
    //    @Bean
    //    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
    //        return new OptimisticLockerInterceptor();
    //    }
    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     */
    //    @Bean
    //    public PaginationInterceptor paginationInterceptor() {
    //        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    //        paginationInterceptor.setLocalPage(true);// 开启 PageHelper 的支持
    //        /*
    //         * 【测试多租户】 SQL 解析处理拦截器<br>
    //         * 这里固定写成住户 1 实际情况你可以从cookie读取，因此数据看不到 【 麻花藤 】 这条记录（ 注意观察 SQL ）<br>
    //         */
    //        List<ISqlParser> sqlParserList = new ArrayList<>();
    //        TenantSqlParser tenantSqlParser = new TenantSqlParser();
    //        tenantSqlParser.setTenantHandler(new TenantHandler() {
    //            @Override
    //            public Expression getTenantId() {
    //                return new StringValue("sdf");
    //            }
    //
    //            @Override
    //            public String getTenantIdColumn() {
    //                return "id";
    //            }
    //
    //            @Override
    //            public boolean doTableFilter(String tableName) {
    //                // 这里可以判断是否过滤表
    //                /*
    //                if ("user".equals(tableName)) {
    //                    return true;
    //                }*/
    //                return false;
    //            }
    //        });
    //
    //
    //        sqlParserList.add(tenantSqlParser);
    //        paginationInterceptor.setSqlParserList(sqlParserList);
    //        paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {
    //            @Override
    //            public boolean doFilter(MetaObject metaObject) {
    //                MappedStatement ms = PluginUtils.getMappedStatement(metaObject);
    //                // 过滤自定义查询此时无租户信息约束【 麻花藤 】出现
    //                if ("com.hikvision.energy.mapper.PersonMapper.selectListBySQL".equals(ms.getId())) {
    //                    return true;
    //                }
    //                return false;
    //            }
    //        });
    //        return paginationInterceptor;
    //    }

}

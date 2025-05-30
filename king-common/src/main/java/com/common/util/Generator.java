package com.common.util;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 */

public class Generator {

    //表前缀，不需要则写空
    private static String tablePrefix = "t_";
    //    private static String driverName = "org.postgresql.Driver";
    //    private static String dbUrl="jdbc:postgresql://10.19.161.139:7017/ctm02pcrps_ctm02pcrpsdb?useUnicode=true&characterEncoding=UTF-8";
    //    private static String username="postgres";
    //    private static String password="xamv81gs";

    //数据库连接url
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static String dbUrl = "jdbc:mysql://120.55.165.139:3306/king?serverTimezone=Asia/Shanghai&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true";
    private static String username = "king";
    private static String password = "king123++";

    public static void main(String[] args) throws InterruptedException {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("E://output");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setAuthor("daiming");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sDao");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        //        dsc.setDbType(DbType.MYSQL);
        /*dsc.setTypeConvert(new MySqlTypeConvert(){
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                return super.processTypeConvert(fieldType);
            }
        });*/

        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName(driverName);
        dsc.setUsername(username);
        dsc.setPassword(password);
        dsc.setUrl(dbUrl);
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        strategy.setTablePrefix(new String[]{tablePrefix});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setEntityColumnConstant(true);//entity类中标字段常量
        //         strategy.setInclude(new String[] { "user" }); // 需要生成的表
        strategy.setExclude(new String[]{"test"}); // 排除生成的表
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //        pc.setModuleName("cat");
        pc.setParent("com.king.pig");
        pc.setEntity("entity");
        pc.setMapper("dao");
        pc.setService("service");
        pc.setController("controller");
        pc.setXml("mappper");
        mpg.setPackageInfo(pc);

        // 执行生成
        mpg.execute();
    }

}
package com.nuoian.common.database.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/6 16:50
 * @Description: 数据源配置
 * @Package: com.nuoian.common.database.config
 * @Version: 1.0
 */
@Configuration
public class DataSourceConfig {

    @Autowired(required = false)
    private Interceptor[] interceptors;

    @Value("${mybatis-plus.typeAliasesPackage}")
    private String packagePath;

    @Value("${mybatis-plus.mapper-locations}")
    private String mapperXmlPath;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
        return interceptor;
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis-plus.configuration")
    public MybatisConfiguration globalConfiguration(){
        MybatisConfiguration mybatisConfiguration=new MybatisConfiguration();
        mybatisConfiguration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        mybatisConfiguration.setJdbcTypeForNull(JdbcType.NULL);
        mybatisConfiguration.addInterceptor(mybatisPlusInterceptor());
        return mybatisConfiguration;
    }

    /**
     * 功能描述:
     * 〈配置读写据库信息〉
     * @return: javax.sql.DataSource
     * @author: 吴宇森
     * @date: 2022/2/14 16:53
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "haplink.datasource.write")
    public DataSource writeDataSource(){
        return new DruidDataSource();
    }

    /**
     * 功能描述:
     * 〈配置读数据库信息（可配置多个做负载均衡）〉
     * @return: javax.sql.DataSource
     * @author: 吴宇森
     * @date: 2022/2/14 16:54
     */
    @Bean
    @ConfigurationProperties(prefix = "haplink.datasource.read")
    public DataSource readDataSource(){
        return new DruidDataSource();
    }

    /**
     * 功能描述:
     * 〈配置数据源路由信息〉
     * @return: org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
     * @author: 吴宇森
     * @date: 2022/2/14 18:48
     */
    @Bean
    public AbstractRoutingDataSource routingDataSource(){
        MyAbstractRoutingDataSource proxy =new MyAbstractRoutingDataSource();
        Map<Object,Object> targetDataSource=new HashMap<>(2);
        //添加写数据库信息
        targetDataSource.put(DbContextHolder.WRITE,writeDataSource());
        //添加读数据库信息
        targetDataSource.put(DbContextHolder.READ,readDataSource());
        //默认数据库信息
        proxy.setDefaultTargetDataSource(writeDataSource());
        //将所有数据库信息添加到路由中
        proxy.setTargetDataSources(targetDataSource);
        return proxy;
    }

    /**
     * 功能描述:
     * 〈配置数据源工厂〉
     * @return: org.apache.ibatis.session.SqlSessionFactory
     * @author: 吴宇森
     * @date: 2022/2/14 18:49
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        //添加数据源
        bean.setDataSource(routingDataSource());
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //添加实体类地址
        bean.setTypeAliasesPackage(packagePath);
        //添加Mapper文件地址
        bean.setMapperLocations(resolver.getResources(mapperXmlPath));
        // 配置信息
        bean.setConfiguration(globalConfiguration());
        if (!ObjectUtils.isEmpty(this.interceptors)) {
            bean.setPlugins(this.interceptors);
        }
        return bean.getObject();
    }

    /**
     * 功能描述:
     * 〈设置事务〉
     * @return: org.springframework.jdbc.datasource.DataSourceTransactionManager
     * @author: 吴宇森
     * @date: 2022/2/14 18:49
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(){
        return new DataSourceTransactionManager(routingDataSource());
    }
}

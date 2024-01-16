package com.mirusoft.business.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;

@MapperScan(value = "com.mirusoft.business.mapper", annotationClass = MariaDBConnMapper.class, sqlSessionFactoryRef = "MariaDBSessionFactory")
@Configuration
public class MariaDBConfig {
    @Primary
    @Bean(name="MariaDBDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.maria")
    public DataSource mariaDBDataSource(){
        return DataSourceBuilder.create().build();
    }

    /**
     * SQL Session 생성
     * @param mariaDBDataSource
     * @param applicationContext
     * @return
     * @throws Exception
     */
    @Primary
    @Bean(name="MariaDBSessionFactory")
    public SqlSessionFactory mariaDBSessionFactory(@Qualifier("MariaDBDataSource") DataSource mariaDBDataSource, ApplicationContext applicationContext) throws Exception{

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(mariaDBDataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/mariadb/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    /**
     * SqlSessionTemplate : Mybatis 에서 제공하는 간편한 SQL Session 관리를 위한 Class 이다.
     * 기존의 SqlSession 을 래핑하고 트랜잭션 관리와 예외 처리등을 자동으로 처리해주는 편리한 기능을 제공한다.
     * @param mariaDBSessionFactory
     * @return
     */
    @Primary
    @Bean(name = "MariaDBSessionTemplate")
    public SqlSessionTemplate mariaDBSessionTemplate(@Qualifier("MariaDBSessionFactory") SqlSessionFactory mariaDBSessionFactory){

        return new SqlSessionTemplate(mariaDBSessionFactory);
    }

    @Primary
    @Bean(name = "MariaDBTransactionManager")
    public DataSourceTransactionManager primaryTransactionManager(@Qualifier("MariaDBDataSource") DataSource mariaDBDataSource){

        return new DataSourceTransactionManager(mariaDBDataSource);
    }
}

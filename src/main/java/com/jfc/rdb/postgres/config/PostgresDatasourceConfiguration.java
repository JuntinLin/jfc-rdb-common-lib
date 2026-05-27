package com.jfc.rdb.postgres.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "postgresEntityManagerFactory",
        transactionManagerRef = "postgresTransactionManager",
        basePackages = {"com.jfc.rdb.postgres.repository"})
public class PostgresDatasourceConfiguration {
	@Bean(name = "postgresProperties")
    @ConfigurationProperties("spring.datasource.postgres")
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "postgresDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.postgres")
    DataSource datasource(@Qualifier("postgresProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "postgresEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            EntityManagerFactoryBuilder builder,
            @Qualifier("postgresDatasource") DataSource dataSource) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.physical_naming_strategy",
                "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");

        // 強制 PostgreSQL 永遠不執行 DDL
        properties.put("hibernate.hbm2ddl.auto", "none");
        // 額外的保護：禁用 schema 更新
        properties.put("hibernate.hbm2ddl.schema-update.unique-constraint", "false");
        properties.put("hibernate.hbm2ddl.schema-update.foreign-key", "false");

        return builder
                .dataSource(dataSource)
                .packages("com.jfc.rdb.postgres.entity")
                .persistenceUnit("postgres")
                .properties(properties)
                .build();
    }

    @Bean(name = "postgresTransactionManager")
    @ConfigurationProperties("spring.jpa.postgres")
    PlatformTransactionManager transactionManager(
            @Qualifier("postgresEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

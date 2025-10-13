package com.jfc.rdb.tiptop.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "tiptopEntityManagerFactory"
	, transactionManagerRef = "tiptopTransactionManager"
	, basePackages = {"com.jfc.rdb.tiptop.repository" })
public class TiptopDatasourceConfiguration {
	@Primary
	@Bean(name = "tiptopProperties")
	@ConfigurationProperties("spring.datasource.oracle")
	DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}
	@Primary
	@Bean(name = "tiptopDatasource")
	@ConfigurationProperties(prefix = "spring.datasource.oracle")
	DataSource datasource(@Qualifier("tiptopProperties") DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().build();
	}
	@Primary
	@Bean(name = "tiptopEntityManagerFactory")
	LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
			EntityManagerFactoryBuilder builder,
			@Qualifier("tiptopDatasource") DataSource dataSource) {
		Map<String, Object> properties = new HashMap<>();
	    properties.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
	    properties.put("hibernate.physical_naming_strategy", 
	            "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
	    
	    // 強制 Oracle 永遠不執行 DDL
	    properties.put("hibernate.hbm2ddl.auto", "none");
	    // 額外的保護：禁用 schema 更新
	    properties.put("hibernate.hbm2ddl.schema-update.unique-constraint", "false");
	    properties.put("hibernate.hbm2ddl.schema-update.foreign-key", "false");
		return builder
				.dataSource(dataSource)
				.packages("com.jfc.rdb.tiptop.entity")
				.persistenceUnit("tiptop")
				.properties(properties)
				.build();
	}
	@Primary
	@Bean(name = "tiptopTransactionManager")
	@ConfigurationProperties("spring.jpa")
	PlatformTransactionManager transactionManager(
			@Qualifier("tiptopEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}

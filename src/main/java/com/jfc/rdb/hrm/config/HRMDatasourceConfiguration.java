package com.jfc.rdb.hrm.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "hrmEntityManagerFactory"
	, transactionManagerRef = "hrmTransactionManager"
	, basePackages = {"com.jfc.rdb.hrm.repository" })

public class HRMDatasourceConfiguration {

	@Bean(name = "hrmProperties")
	@ConfigurationProperties("spring.datasource.mssql")
	DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}
/*
	@Bean(name = "hrmDatasource")
	@ConfigurationProperties(prefix = "spring.datasource.mssql")
	DataSource datasource(@Qualifier("hrmProperties") DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().build();
	}
*/
	@Bean(name = "hrmDatasource")
	DataSource datasource(@Qualifier("hrmProperties") DataSourceProperties properties) {
		// Use HikariDataSource explicitly so we can configure it
		HikariDataSource dataSource = (HikariDataSource) properties.initializeDataSourceBuilder().build();
		
		// Add specific connection properties to force TLS 1.0 compatibility
		Properties hikariProps = new Properties();
		hikariProps.setProperty("encrypt", "false");
		hikariProps.setProperty("trustServerCertificate", "false");
		
		dataSource.setDataSourceProperties(hikariProps);
		return dataSource;
	}
	
	
	@Bean(name = "hrmEntityManagerFactory")
	LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
			EntityManagerFactoryBuilder builder,
			@Qualifier("hrmDatasource") DataSource dataSource) {
		Map<String, Object> properties = new HashMap<>();
	    properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
	    properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
	    
	 // 強制 MSSQL 永遠不執行 DDL
	    properties.put("hibernate.hbm2ddl.auto", "none");
	    // 額外的保護
	    properties.put("hibernate.hbm2ddl.schema-update.unique-constraint", "false");
	    properties.put("hibernate.hbm2ddl.schema-update.foreign-key", "false");
		return builder.dataSource(dataSource)
				.packages("com.jfc.rdb.hrm.entity")
				.persistenceUnit("hrm")
				.properties(properties)
				.build();
	}

	//@Primary
	@Bean(name = "hrmTransactionManager")
	@ConfigurationProperties("spring.jpa.mssql")
	PlatformTransactionManager transactionManager(
			@Qualifier("hrmEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}

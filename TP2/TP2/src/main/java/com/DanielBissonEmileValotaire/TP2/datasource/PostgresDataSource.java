package com.DanielBissonEmileValotaire.TP2.datasource;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class PostgresDataSource {
	
	@Bean
	@ConfigurationProperties(prefix = "app.datasource")
	public HikariDataSource hikariDataSource() {
		return DataSourceBuilder
				.create()
				.type(HikariDataSource.class)
				.build();
	}
}

package com.example.books;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
@EnableAutoConfiguration
public class ApplicationTestConfig {
  private static final PostgresqlDockerContainer postgresqlDockerContainer = PostgresqlDockerContainer.getInstance();

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder.build();
  }

  @Bean
  @Qualifier("dataSource")
  public DataSource dataSource() {
    return createHikariDataSource(PostgresqlDockerContainer.getPostgreSQLContainer());
  }

  private DataSource createHikariDataSource(PostgreSQLContainer postgreSQLContainer) {
    HikariConfig config = new HikariConfig();
    config.setUsername(postgreSQLContainer.getUsername());
    config.setPassword(postgreSQLContainer.getPassword());
    config.setDriverClassName(postgreSQLContainer.getDriverClassName());
    config.setJdbcUrl(postgreSQLContainer.getJdbcUrl());

    return new HikariDataSource(config);
  }
}

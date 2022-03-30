package com.example.books.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.example.books"})
public class ApplicationConfig {

  @Bean
  @Qualifier("dataSource")
  public DataSource dataSource() {
    return createHikariDataSource();
  }

  private DataSource createHikariDataSource() {
    HikariConfig config = new HikariConfig();
    config.setUsername("postgres");
    config.setPassword("mysecretpassword");
    config.setDriverClassName("org.postgresql.Driver");
    config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");

    return new HikariDataSource(config);
  }

}

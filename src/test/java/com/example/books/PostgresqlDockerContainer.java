package com.example.books;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
public final class PostgresqlDockerContainer {

  private static PostgresqlDockerContainer postgresqlDockerContainer;
  private static PostgreSQLContainer postgreSQLContainer;

  public static PostgresqlDockerContainer getInstance()
  {
    if (postgresqlDockerContainer == null) {
      //noinspection InstantiationOfUtilityClass
      postgresqlDockerContainer = new PostgresqlDockerContainer();
    }
    return postgresqlDockerContainer;
  }

  public static PostgreSQLContainer getPostgreSQLContainer() {
    return postgreSQLContainer;
  }

  public static void restartPostgreSQLContainer() {
    initializeNewContainer();
  }

  private PostgresqlDockerContainer() {
    initializeNewContainer();
  }

  private static void initializeNewContainer() {
    log.info("Starting Postgres Docker Image");
    if (postgreSQLContainer != null) {
      postgreSQLContainer.stop();
    }

    postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.4");
    postgreSQLContainer.start();
    runFlywayScript(postgreSQLContainer);
  }

  private static void runFlywayScript(PostgreSQLContainer postgreSQLContainer) {
    log.info("Beginning Flyway Migration");

    Flyway flyway = Flyway.configure()
      .locations("classpath:db/migration")
      .dataSource(getDataSource(postgreSQLContainer))
      .load();
    flyway.migrate();

    log.info("Done with Flyway Migration");
  }

  public static DataSource getDataSource(PostgreSQLContainer postgreSQLContainer) {

    HikariConfig config = new HikariConfig();
    config.setUsername(postgreSQLContainer.getUsername());
    config.setPassword(postgreSQLContainer.getPassword());
    config.setDriverClassName(postgreSQLContainer.getDriverClassName());
    config.setJdbcUrl(postgreSQLContainer.getJdbcUrl());

    return new HikariDataSource(config);
  }

}

package ru.romanow.protocols.graphql.config;

import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.Driver;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class DatabaseTestConfiguration {
    private static final String POSTGRES_LOCAL_IMAGE = "postgres:13";
    private static final String DATABASE_NAME = "graphql";
    private static final String USERNAME = "program";
    private static final String PASSWORD = "test";

    @Bean
    public PostgreSQLContainer<?> postgres() {
        final PostgreSQLContainer<?> postgres =
                new PostgreSQLContainer<>(POSTGRES_LOCAL_IMAGE)
                        .withUsername(USERNAME)
                        .withPassword(PASSWORD)
                        .withDatabaseName(DATABASE_NAME);
        postgres.start();
        return postgres;
    }

    @Primary
    @DependsOn("postgres")
    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource() {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(postgres().getJdbcUrl());
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setDriverClassName(Driver.class.getCanonicalName());
        return dataSource;
    }
}
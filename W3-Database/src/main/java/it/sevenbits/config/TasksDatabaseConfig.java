package it.sevenbits.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * A configuration for database.
 */
@Configuration
public class TasksDatabaseConfig {
    /**
     * A method that creates DataSource object.
     * @return a DataSource object.
     */
    @Bean
    @FlywayDataSource
    @Qualifier("tasksDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.tasks")
    public DataSource tasksDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * A method that creates a JdbcOperations object. It is needed to work with a database.
     * @param tasksDataSource a DataSource object.
     * @return created JdbcOperations object.
     */
    @Bean
    @Qualifier("tasksJdbcOperations")
    public JdbcOperations tasksJdbcOperations(final @Qualifier("tasksDataSource") DataSource tasksDataSource) {
        return new JdbcTemplate(tasksDataSource);
    }
}

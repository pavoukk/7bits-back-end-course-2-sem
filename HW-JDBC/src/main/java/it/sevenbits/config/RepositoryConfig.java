package it.sevenbits.config;

import it.sevenbits.core.repository.DatabaseTasksRepository;
import it.sevenbits.core.repository.ITasksRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * The class is a repository configuration.
 */
@Configuration
public class RepositoryConfig {
    /**
     * The method returns some repository.
     *
     * @param jdbcOperations is needed to work with database.
     * @return a repository.
     */
    @Bean
    public ITasksRepository tasksRepository(
            @Qualifier("tasksJdbcOperations") final JdbcOperations jdbcOperations) {
        return new DatabaseTasksRepository(jdbcOperations);
    }
}

package it.sevenbits.config;

import it.sevenbits.core.repository.tasks.DatabaseTasksRepository;
import it.sevenbits.core.repository.users.DatabaseUsersRepository;
import it.sevenbits.core.repository.tasks.ITasksRepository;
import it.sevenbits.core.repository.users.IUsersRepository;
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

    /**
     * The method returns some users repository.
     * @param jdbcOperations is needed to work with database.
     * @return a repository.
     */
    @Bean
    public IUsersRepository usersRepository(
            @Qualifier("tasksJdbcOperations") final JdbcOperations jdbcOperations) {
        return new DatabaseUsersRepository(jdbcOperations);
    }
}

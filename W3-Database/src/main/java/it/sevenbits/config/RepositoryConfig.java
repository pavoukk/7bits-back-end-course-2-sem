package it.sevenbits.config;

import it.sevenbits.core.repository.DatabaseTasksRepository;
import it.sevenbits.core.repository.HashMapTasksRepository;
import it.sevenbits.core.repository.ITasksRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

@Configuration
public class RepositoryConfig {
    @Bean
    public ITasksRepository tasksRepository(
            @Qualifier("tasksJdbcOperations") JdbcOperations jdbcOperations) {
        return new DatabaseTasksRepository(jdbcOperations);
    }
}

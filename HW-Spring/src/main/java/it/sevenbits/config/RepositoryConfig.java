package it.sevenbits.config;

import it.sevenbits.core.repository.HashMapTasksRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The class is a repository configuration.
 */
@Configuration
public class RepositoryConfig {
    /**
     * The method returns some repository.
     *
     * @return a repository.
     */
    @Bean
    public HashMapTasksRepository tasksRepository() {
        return HashMapTasksRepository.getTasksRepository();
    }
}

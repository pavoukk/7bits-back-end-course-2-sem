package it.sevenbits.config;

import it.sevenbits.core.repository.HashMapTasksRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    @Bean
    public HashMapTasksRepository tasksRepository() {
        return HashMapTasksRepository.getTasksRepository();
    }
}

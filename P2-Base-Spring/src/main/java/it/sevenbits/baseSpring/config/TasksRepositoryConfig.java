package it.sevenbits.baseSpring.config;

import it.sevenbits.baseSpring.core.repository.ITasksRepository;
import it.sevenbits.baseSpring.core.repository.TasksRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TasksRepositoryConfig {

    @Bean
    public ITasksRepository tasksRepository() {
        return TasksRepository.initialize();
    }
}

package it.sevenbits.baseSpring.core.repository;

import it.sevenbits.baseSpring.core.model.Task;

import java.util.List;

public interface ITasksRepository {

    List<Task> getAllTasks();

    Task create(String text);
}

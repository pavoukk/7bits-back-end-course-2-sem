package it.sevenbits.core.repository;

import it.sevenbits.core.model.Task;
import it.sevenbits.web.model.AddTaskRequest;

import java.util.List;

public interface ITasksRepository {
    List<Task> getAllTasks(String status);
    Task create(AddTaskRequest task);
    Task getTaskById(String id);
    void replace(String id, Task newTask);
    Task removeTask(String id);
}

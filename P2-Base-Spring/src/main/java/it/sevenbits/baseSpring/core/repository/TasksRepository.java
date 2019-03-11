package it.sevenbits.baseSpring.core.repository;

import it.sevenbits.baseSpring.core.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TasksRepository implements ITasksRepository {
    private List<Task> tasks;
    private static TasksRepository repository;

    private TasksRepository() {
        tasks = Collections.synchronizedList(new ArrayList<>());
    }

    public static ITasksRepository initialize() {
        if(repository == null) {
            repository = new TasksRepository();
        }
        return repository;
    }

    @Override
    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(tasks);
    }

    @Override
    public Task create(String text) {
        Task task = new Task(text);
        tasks.add(task);
        return task;
    }
}

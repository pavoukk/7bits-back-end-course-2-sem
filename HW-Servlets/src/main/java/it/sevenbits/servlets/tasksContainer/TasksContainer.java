package it.sevenbits.servlets.tasksContainer;

import it.sevenbits.servlets.tasksContainer.exceptions.TasksContainerException;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TasksContainer {
    private static TasksContainer container;
    private ConcurrentMap<String, Task> tasks;

    private TasksContainer() {
        tasks = new ConcurrentHashMap<>();
    }

    public static TasksContainer getTasksContainer() {
        if (container == null) {
            container = new TasksContainer();
        }
        return container;
    }

    public Task getTaskById(String id) throws TasksContainerException {
        if (!tasks.containsKey(id)) {
            throw new TasksContainerException("The tasks container does not contain any elements followed by the key");
        }
        return tasks.get(id);
    }

    public void addNewTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public Set<Task> getAllTasks() {
        return new HashSet<>(tasks.values());
    }

    public void removeTask(String id) throws TasksContainerException {
        if (!tasks.containsKey(id)) {
            throw new TasksContainerException("The tasks container does not contain any elements followed by the key");
        }
        tasks.remove(id);
    }
}


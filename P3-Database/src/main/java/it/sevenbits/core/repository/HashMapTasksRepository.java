package it.sevenbits.core.repository;

import it.sevenbits.core.model.Task;
import it.sevenbits.web.model.AddTaskRequest;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class HashMapTasksRepository implements ITasksRepository {
    private static HashMapTasksRepository container;
    private ConcurrentMap<String, Task> tasks;

    private HashMapTasksRepository() {
        tasks = new ConcurrentHashMap<>();
    }

    public static HashMapTasksRepository getTasksRepository() {
        if (container == null) {
            container = new HashMapTasksRepository();
        }
        return container;
    }

    @Override
    public Task getTaskById(String id) {
        return tasks.get(id);
    }

    @Override
    public void replace(String id, Task newTask) {
        tasks.replace(id, newTask);
    }

    @Override
    public Task create(AddTaskRequest taskRequest) {
        Timestamp timestamp = Timestamp.from(Instant.now(Clock.system(ZoneId.of("UTC"))));
        Task task = new Task(UUID.randomUUID().toString(), taskRequest.getText(), "inbox", timestamp.toString());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public List<Task> getAllTasks(String status) {
        return Collections.unmodifiableList(
                tasks.values()
                        .stream()
                        .filter(el -> el.getStatus().equals(status))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Task removeTask(String id) {
        return tasks.remove(id);
    }
}

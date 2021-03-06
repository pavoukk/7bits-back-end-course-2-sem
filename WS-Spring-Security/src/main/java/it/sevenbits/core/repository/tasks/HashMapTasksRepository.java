package it.sevenbits.core.repository.tasks;

import it.sevenbits.core.model.Task;
import it.sevenbits.web.model.tasks.request.AddTaskRequest;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * The class is an implementation of ITasksRepository. It is a repository for tasks based on ConcurrentHashMap.
 */
public final class HashMapTasksRepository implements ITasksRepository {
    private static HashMapTasksRepository container;
    private ConcurrentMap<String, Task> tasks;

    private HashMapTasksRepository() {
        tasks = new ConcurrentHashMap<>();
    }

    /**
     * The class must be a singleton. It has a private constructor. The method is used to create a single object.
     *
     * @return a single object of the class.
     */
    public static HashMapTasksRepository getTasksRepository() {
        if (container == null) {
            container = new HashMapTasksRepository();
        }
        return container;
    }

    @Override
    public Task getTaskById(final String id) {
        return tasks.get(id);
    }

    @Override
    public void replace(final String id, final Task newTask) {
        Task updatedTask = new Task(
                newTask.getId(),
                newTask.getText(),
                newTask.getStatus(),
                newTask.getCreatedAt(),
                Timestamp.from(Instant.now(Clock.system(ZoneId.of("UTC")))).toString(),
                null
        );
        tasks.replace(id, updatedTask);
    }

    @Override
    public Task create(final AddTaskRequest taskRequest) {
        Timestamp timestamp = Timestamp.from(Instant.now(Clock.system(ZoneId.of("UTC"))));
        Task task = new Task(
                UUID.randomUUID().toString(),
                taskRequest.getText(),
                "inbox", timestamp.toString(),
                timestamp.toString(),
                null
        );
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public List<Task> getAllTasks(final String status, final String order, final int page, final int size) {
        return Collections.unmodifiableList(
                tasks.values()
                        .stream()
                        .filter(el -> el.getStatus().equals(status))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public int getTotalCount() {
        return tasks.size();
    }

    @Override
    public Task removeTask(final String id) {
        return tasks.remove(id);
    }
}

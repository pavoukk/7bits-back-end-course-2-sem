package it.sevenbits.core.repository;

import it.sevenbits.core.model.Task;
import it.sevenbits.web.model.request.AddTaskRequest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class is an implementation of ITasksRepository. It is a repository for tasks based on database.
 */
public class DatabaseTasksRepository implements ITasksRepository {
    private JdbcOperations jdbcOperations;

    private RowMapper<Task> rowMapper;

    private Map<String, String> orderRequests;

    /**
     * A constructor that takes one parameter.
     *
     * @param jdbcOperations is a JdbcOperations object that is used to work with some database.
     */
    public DatabaseTasksRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
        rowMapper = new RowMapper<Task>() {
            @Override
            public Task mapRow(final ResultSet resultSet, final int i) throws SQLException {
                return new Task(
                        resultSet.getString("id"),
                        resultSet.getString("task"),
                        resultSet.getString("status"),
                        resultSet.getString("created_at"),
                        resultSet.getString("updated_at")
                );
            }
        };
        orderRequests = new ConcurrentHashMap<>();
        orderRequests.put("ASC", "SELECT id, task, status, created_at, updated_at " +
                "FROM task WHERE status = ? ORDER BY created_at ASC LIMIT ? OFFSET ?");
        orderRequests.put("DESC", "SELECT id, task, status, created_at, updated_at " +
                "FROM task WHERE status = ? ORDER BY created_at DESC LIMIT ? OFFSET ?");
    }

    @Override
    public List<Task> getAllTasks(final String status, final String order, final int page, final int size) {
        List<Task> tasks = jdbcOperations.query(
                orderRequests.get(order.toUpperCase()),
                rowMapper,
                status, size, (page - 1) * size
        );
        return Collections.unmodifiableList(tasks);
    }

    @Override
    public int getTotalCount() {
        List<Task> tasks = jdbcOperations.query("SELECT * FROM TASK", rowMapper);
        return tasks.size();
    }

    @Override
    public Task create(final AddTaskRequest task) {
        String id = UUID.randomUUID().toString();
        String text = task.getText();
        String status = "inbox";
        Timestamp createdAt = Timestamp.from(Instant.now(Clock.system(ZoneId.of("UTC"))));

        jdbcOperations.update(
                "INSERT INTO task (id, task, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?)",
                id,
                text,
                status,
                createdAt,
                createdAt
        );
        return new Task(id, text, status, createdAt.toString(), createdAt.toString());
    }

    @Override
    public Task getTaskById(final String id) {
        try {
            return jdbcOperations.queryForObject(
                    "SELECT id, task, status, created_at, updated_at FROM task WHERE id = ?",
                    rowMapper,
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void replace(final String id, final Task newTask) {
        jdbcOperations.update("UPDATE task SET task = ?, status = ?, updated_at = ? WHERE id = ?",
                newTask.getText(),
                newTask.getStatus(),
                Timestamp.from(Instant.now(Clock.system(ZoneId.of("UTC")))),
                id
        );
    }

    @Override
    public Task removeTask(final String id) {
        Task task = getTaskById(id);
        int rows = jdbcOperations.update(
                "DELETE FROM task WHERE id = ?",
                id);
        if (rows != 0) {
            return task;
        }
        return null;
    }
}

package it.sevenbits.core.repository.tasks;

import it.sevenbits.core.model.Task;
import it.sevenbits.web.model.tasks.request.AddTaskRequest;
import it.sevenbits.web.service.whoami.WhoAmIService;
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
    private WhoAmIService whoAmIService;

    private JdbcOperations jdbcOperations;

    private RowMapper<Task> rowMapper;

    private Map<String, String> orderRequests;

    /**
     * A constructor that takes one parameter.
     *
     * @param jdbcOperations is a JdbcOperations object that is used to work with some database.
     * @param whoAmIService is a service that determines a current user.
     */
    public DatabaseTasksRepository(final JdbcOperations jdbcOperations, final WhoAmIService whoAmIService) {
        this.whoAmIService = whoAmIService;
        this.jdbcOperations = jdbcOperations;
        rowMapper = new RowMapper<Task>() {
            @Override
            public Task mapRow(final ResultSet resultSet, final int i) throws SQLException {
                return new Task(
                        resultSet.getString("ID"),
                        resultSet.getString("task"),
                        resultSet.getString("status"),
                        resultSet.getString("created_at"),
                        resultSet.getString("updated_at"),
                        resultSet.getString("owner")
                );
            }
        };
        orderRequests = new ConcurrentHashMap<>();
        orderRequests.put("ASC", "SELECT ID, task, status, created_at, updated_at, owner " +
                "FROM task WHERE status = ? AND owner = ? ORDER BY created_at ASC LIMIT ? OFFSET ?");
        orderRequests.put("DESC", "SELECT ID, task, status, created_at, updated_at, owner " +
                "FROM task WHERE status = ? AND owner = ? ORDER BY created_at DESC LIMIT ? OFFSET ?");
    }

    @Override
    public List<Task> getAllTasks(final String status, final String order, final int page, final int size) {
        List<Task> tasks = jdbcOperations.query(
                orderRequests.get(order.toUpperCase()),
                rowMapper,
                status, whoAmIService.whoAmI().getId(), size, (page - 1) * size
        );
        return Collections.unmodifiableList(tasks);
    }

    @Override
    public int getTotalCount() {
        List<Task> tasks = jdbcOperations.query("SELECT * FROM TASK WHERE owner = ?", rowMapper, whoAmIService.whoAmI().getId());
        return tasks.size();
    }

    @Override
    public Task create(final AddTaskRequest task) {
        String id = UUID.randomUUID().toString();
        String text = task.getText();
        String status = "inbox";
        Timestamp createdAt = Timestamp.from(Instant.now(Clock.system(ZoneId.of("UTC"))));

        String userId = whoAmIService.whoAmI().getId();
        jdbcOperations.update(
                "INSERT INTO task (ID, task, status, created_at, updated_at, owner) VALUES (?, ?, ?, ?, ?, ?)",
                id,
                text,
                status,
                createdAt,
                createdAt,
                userId
        );
        return new Task(id, text, status, createdAt.toString(), createdAt.toString(), userId);
    }

    @Override
    public Task getTaskById(final String id) {
        try {
            return jdbcOperations.queryForObject(
                    "SELECT ID, task, status, created_at, updated_at, owner FROM task WHERE ID = ? AND owner = ?",
                    rowMapper,
                    id,
                    whoAmIService.whoAmI().getId()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void replace(final String id, final Task newTask) {
        jdbcOperations.update("UPDATE task SET task = ?, status = ?, updated_at = ? WHERE ID = ? AND owner = ?",
                newTask.getText(),
                newTask.getStatus(),
                Timestamp.from(Instant.now(Clock.system(ZoneId.of("UTC")))),
                id,
                whoAmIService.whoAmI().getId()
        );
    }

    @Override
    public Task removeTask(final String id) {
        Task task = getTaskById(id);
        int rows = jdbcOperations.update(
                "DELETE FROM task WHERE ID = ? AND owner = ?",
                id,
                whoAmIService.whoAmI().getId());
        if (rows != 0) {
            return task;
        }
        return null;
    }
}

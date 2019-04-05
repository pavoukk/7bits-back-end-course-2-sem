package it.sevenbits.core.repository;

import it.sevenbits.core.model.Task;
import it.sevenbits.web.model.AddTaskRequest;
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
import java.util.UUID;

public class DatabaseTasksRepository implements ITasksRepository {
    private JdbcOperations jdbcOperations;

    private RowMapper<Task> rowMapper;

    public DatabaseTasksRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
        rowMapper = new RowMapper<Task>() {
            @Override
            public Task mapRow(final ResultSet resultSet, final int i) throws SQLException {
                return new Task(
                        resultSet.getString("id"),
                        resultSet.getString("task"),
                        resultSet.getString("status"),
                        resultSet.getString("created_at")
                );
            }
        };
    }

    @Override
    public List<Task> getAllTasks(final String status) {
        List<Task> tasks = jdbcOperations.query(
                "SELECT id, task, status, created_at FROM task WHERE status = ?",
                rowMapper,
                status
        );
        return Collections.unmodifiableList(tasks);
    }

    @Override
    public Task create(final AddTaskRequest task) {
        String id = UUID.randomUUID().toString();
        String text = task.getText();
        String status = "inbox";
        Timestamp createdAt = Timestamp.from(Instant.now(Clock.system(ZoneId.of("UTC"))));

        jdbcOperations.update(
                "INSERT INTO task (id, task, status, created_at) VALUES (?, ?, ?, ?)",
                id,
                text,
                status,
                createdAt
        );
        return new Task(id, text, status, createdAt.toString());
    }

    @Override
    public Task getTaskById(final String id) {
        return null;
    }

    @Override
    public void replace(final String id, final Task newTask) {
        return;
    }

    @Override
    public Task removeTask(final String id) {
        return null;
    }
}

package it.sevenbits.core.repository;

import it.sevenbits.core.model.Task;
import it.sevenbits.web.model.AddTaskRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DatabaseTasksRepositoryTest {
    private JdbcOperations mockJdbcOperations;

    private DatabaseTasksRepository databaseTasksRepository;

    @Before
    public void setUp() {
        mockJdbcOperations = mock(JdbcOperations.class);

        databaseTasksRepository = new DatabaseTasksRepository(mockJdbcOperations);
    }

    @Test
    public void getAllTasks() {
        List<Task> mockTasksList = mock(List.class);

        when(mockJdbcOperations.query(
                anyString(),
                any(RowMapper.class),
                anyString()))
                .thenReturn(mockTasksList);

        String status = "inbox";
        List<Task> list = databaseTasksRepository.getAllTasks(status);

        verify(mockJdbcOperations, times(1)).query(
                eq("SELECT id, task, status, created_at, updated_at FROM task WHERE status = ?"),
                any(RowMapper.class),
                eq(status));
        assertEquals(Collections.unmodifiableList(mockTasksList), list);
    }

    @Test
    public void create() {
        String text = "text";
        AddTaskRequest mockAddTaskRequest = mock(AddTaskRequest.class);
        when(mockAddTaskRequest.getText()).thenReturn(text);

        when(mockJdbcOperations.update(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                any(Timestamp.class),
                any(Timestamp.class)))
                .thenReturn(1);
        Task task = databaseTasksRepository.create(mockAddTaskRequest);

        verify(mockJdbcOperations, times(1)).update(
                eq("INSERT INTO task (id, task, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?)"),
                eq(task.getId()),
                eq("text"),
                eq("inbox"),
                eq(Timestamp.valueOf(task.getCreatedAt())),
                eq(Timestamp.valueOf(task.getUpdatedAt())));

        assertEquals(text, task.getText());
        assertEquals("inbox", task.getStatus());
    }

    @Test
    public void getTaskById() {
        Task mockTask = mock(Task.class);
        String id = "id";
        when(mockJdbcOperations.queryForObject(
                anyString(),
                any(RowMapper.class),
                anyString()))
                .thenReturn(mockTask);
        Task task = databaseTasksRepository.getTaskById(id);

        verify(mockJdbcOperations, times(1)).queryForObject(
                eq("SELECT id, task, status, created_at, updated_at FROM task WHERE id = ?"),
                any(RowMapper.class),
                eq(id));

        assertEquals(mockTask, task);
    }

    @Test
    public void replace() {
        Task mockTask = mock(Task.class);
        String text = "text";
        String status = "done";
        when(mockTask.getStatus()).thenReturn(status);
        when(mockTask.getText()).thenReturn(text);
        when(mockJdbcOperations.update(anyString(),
                anyString(),
                anyString(),
                any(Timestamp.class),
                anyString())).thenReturn(1);

        String id = "id";
        databaseTasksRepository.replace(id, mockTask);


        verify(mockJdbcOperations, times(1)).update(
                eq("UPDATE task SET text = ?, status = ?, updated_at = ? WHERE id = ?"),
                eq(text),
                eq(status),
                any(Timestamp.class),
                eq(id));
    }

    @Test
    public void removeTask() {
        String id = "id";
        Task mockTask = mock(Task.class);
        when(mockJdbcOperations.queryForObject(
                anyString(),
                any(RowMapper.class),
                anyString()))
                .thenReturn(mockTask);

        when(mockJdbcOperations.update(
                anyString(),
                anyString()))
                .thenReturn(1);

        Task task = databaseTasksRepository.removeTask(id);
        verify(mockJdbcOperations, times(1)).update(
                eq("DELETE FROM task WHERE id = ?"),
                eq(id));
        assertEquals(mockTask, task);
    }
}
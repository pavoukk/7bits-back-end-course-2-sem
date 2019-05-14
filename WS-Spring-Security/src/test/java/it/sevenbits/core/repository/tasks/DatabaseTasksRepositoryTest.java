package it.sevenbits.core.repository.tasks;

import it.sevenbits.core.model.Task;
import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.tasks.DatabaseTasksRepository;
import it.sevenbits.web.model.tasks.request.AddTaskRequest;
import it.sevenbits.web.service.whoami.WhoAmIService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.util.ArrayList;
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
        WhoAmIService whoAmIServiceMock = mock(WhoAmIService.class);
        when(whoAmIServiceMock.whoAmI()).thenReturn(new User("some ID", "username", "PASSWORD", null));

        databaseTasksRepository = new DatabaseTasksRepository(mockJdbcOperations, whoAmIServiceMock);
    }

    @Test
    public void getAllTasks() {
        List<Task> tasksList = new ArrayList<>();

        when(mockJdbcOperations.query(
                anyString(),
                any(RowMapper.class),
                anyString(),
                anyInt(),
                anyInt()))
                .thenReturn(tasksList);

        String status = "inbox";
        String order = "desc";
        int page = 1;
        int size = 25;
        List<Task> response = databaseTasksRepository.getAllTasks(status, order, page, size);

        verify(mockJdbcOperations, times(1)).query(
                eq("SELECT ID, task, status, created_at, updated_at, owner " +
                        "FROM task WHERE status = ? AND owner = ? ORDER BY created_at DESC LIMIT ? OFFSET ?"),
                any(RowMapper.class),
                eq(status),
                anyString(),
                eq(size),
                eq((page - 1) * size));
        assertEquals(tasksList, response);
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
                any(Timestamp.class),
                anyString()))
                .thenReturn(1);
        Task task = databaseTasksRepository.create(mockAddTaskRequest);

        verify(mockJdbcOperations, times(1)).update(
                eq("INSERT INTO task (ID, task, status, created_at, updated_at, owner) VALUES (?, ?, ?, ?, ?, ?)"),
                eq(task.getId()),
                eq("text"),
                eq("inbox"),
                eq(Timestamp.valueOf(task.getCreatedAt())),
                eq(Timestamp.valueOf(task.getUpdatedAt())),
                anyString());

        assertEquals(text, task.getText());
        assertEquals("inbox", task.getStatus());
    }

    @Test
    public void getTaskById() {
        Task mockTask = mock(Task.class);
        String id = "ID";
        when(mockJdbcOperations.queryForObject(
                anyString(),
                any(RowMapper.class),
                anyString(),
                anyString()))
                .thenReturn(mockTask);
        Task task = databaseTasksRepository.getTaskById(id);

        verify(mockJdbcOperations, times(1)).queryForObject(
                eq("SELECT ID, task, status, created_at, updated_at, owner FROM task WHERE ID = ? AND owner = ?"),
                any(RowMapper.class),
                eq(id),
                anyString());

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

        String id = "ID";
        databaseTasksRepository.replace(id, mockTask);


        verify(mockJdbcOperations, times(1)).update(
                eq("UPDATE task SET task = ?, status = ?, updated_at = ? WHERE ID = ? AND owner = ?"),
                eq(text),
                eq(status),
                any(Timestamp.class),
                eq(id),
                anyString());
    }

    @Test
    public void removeTask() {
        String id = "ID";
        Task mockTask = mock(Task.class);
        when(mockJdbcOperations.queryForObject(
                anyString(),
                any(RowMapper.class),
                anyString(),
                anyString()))
                .thenReturn(mockTask);

        when(mockJdbcOperations.update(
                anyString(),
                anyString(),
                anyString()))
                .thenReturn(1);

        Task task = databaseTasksRepository.removeTask(id);
        verify(mockJdbcOperations, times(1)).update(
                eq("DELETE FROM task WHERE ID = ? AND owner = ?"),
                eq(id),
                anyString());
        assertEquals(mockTask, task);
    }
}
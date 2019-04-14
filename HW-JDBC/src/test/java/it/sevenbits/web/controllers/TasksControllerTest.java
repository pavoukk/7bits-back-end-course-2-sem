package it.sevenbits.web.controllers;

import it.sevenbits.core.model.Task;
import it.sevenbits.core.repository.ITasksRepository;
import it.sevenbits.web.model.request.AddTaskRequest;
import it.sevenbits.web.model.metadata.MetaDataDefault;
import it.sevenbits.web.model.request.UpdateTaskRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TasksControllerTest {
    private ITasksRepository mockTasksRepository;

    private TasksController tasksController;


    @Before
    public void setUp() {
        mockTasksRepository = mock(ITasksRepository.class);
        MetaDataDefault mockMetaDataDefault = mock(MetaDataDefault.class);

        tasksController = new TasksController(mockTasksRepository, mockMetaDataDefault);
    }

    /*
    @Test
    public void getList() {
        List<Task> mockList = mock(List.class);
        String status = "inbox";
        String order = "desc";
        int page = 1;
        int size = 25;
        when(mockTasksRepository.getAllTasks(status, order, page, size)).thenReturn(mockList);

        ResponseEntity<GetTasksResponse> response = tasksController.getList(status, order, String.valueOf(page), String.valueOf(size));
        verify(mockTasksRepository, times(1)).getAllTasks(status, order, page, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
    }*/

    @Test
    public void create() {
        AddTaskRequest addTaskRequest = mock(AddTaskRequest.class);
        Task mockTask = mock(Task.class);

        when(addTaskRequest.getText()).thenReturn("do practice");
        when(mockTasksRepository.create(addTaskRequest)).thenReturn(mockTask);

        ResponseEntity<?> response = tasksController.create(addTaskRequest);
        verify(mockTasksRepository, times(1)).create(addTaskRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void findTaskById() {
        Task mockTask = mock(Task.class);
        String id = UUID.randomUUID().toString();

        when(mockTasksRepository.getTaskById(anyString())).thenReturn(mockTask);

        ResponseEntity<Task> response = tasksController.findTaskById(id);
        verify(mockTasksRepository, times(1)).getTaskById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findTaskByIdIncorrectId() {
        String id = "id";
        ResponseEntity<Task> response = tasksController.findTaskById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void findTaskByIdNotFound() {
        String id = UUID.randomUUID().toString();

        when(mockTasksRepository.getTaskById(anyString())).thenReturn(null);

        ResponseEntity<Task> response = tasksController.findTaskById(id);
        verify(mockTasksRepository, times(1)).getTaskById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void updateTask() {
        String id = UUID.randomUUID().toString();
        Task mockTask = mock(Task.class);
        UpdateTaskRequest mockUpdateTaskRequest = mock(UpdateTaskRequest.class);

        when(mockUpdateTaskRequest.getStatus()).thenReturn("done");
        when(mockUpdateTaskRequest.getText()).thenReturn("text");
        when(mockTasksRepository.getTaskById(anyString())).thenReturn(mockTask);

        ResponseEntity<?> response = tasksController.updateTask(id, mockUpdateTaskRequest);
        verify(mockTasksRepository, times(1)).getTaskById(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void updateTaskNotFound() {
        String id = UUID.randomUUID().toString();
        UpdateTaskRequest mockUpdateTaskRequest = mock(UpdateTaskRequest.class);

        when(mockTasksRepository.getTaskById(anyString())).thenReturn(null);

        ResponseEntity<?> response = tasksController.updateTask(id, mockUpdateTaskRequest);
        verify(mockTasksRepository, times(1)).getTaskById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateTaskBadValidation() {
        String id = UUID.randomUUID().toString();
        Task mockTask = mock(Task.class);
        UpdateTaskRequest mockUpdateTaskRequest = mock(UpdateTaskRequest.class);

        when(mockUpdateTaskRequest.getStatus()).thenReturn("wrong status");
        when(mockUpdateTaskRequest.getText()).thenReturn(null);
        when(mockTasksRepository.getTaskById(anyString())).thenReturn(mockTask);

        ResponseEntity<?> response = tasksController.updateTask(id, mockUpdateTaskRequest);
        verify(mockTasksRepository, times(1)).getTaskById(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void removeTask() {
        String id = UUID.randomUUID().toString();
        Task mockTask = mock(Task.class);

        when(mockTasksRepository.removeTask(anyString())).thenReturn(mockTask);

        ResponseEntity<?> response = tasksController.removeTask(id);
        verify(mockTasksRepository, times(1)).removeTask(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void removeTaskNotFound() {
        String id = UUID.randomUUID().toString();
        when(mockTasksRepository.removeTask(anyString())).thenReturn(null);
        ResponseEntity<?> response = tasksController.removeTask(id);
        verify(mockTasksRepository, times(1)).removeTask(id);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
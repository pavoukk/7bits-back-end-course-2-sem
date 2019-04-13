package it.sevenbits.web.service;

import it.sevenbits.core.model.Task;
import it.sevenbits.core.repository.ITasksRepository;
import it.sevenbits.core.service.validators.PageSizeValidator;
import it.sevenbits.core.service.validators.PageValidator;
import it.sevenbits.core.service.validators.OrderValidator;
import it.sevenbits.core.service.validators.StatusValidator;
import it.sevenbits.core.service.validators.IdValidator;
import it.sevenbits.web.model.AddTaskRequest;
import it.sevenbits.web.model.GetTasksResponse;
import it.sevenbits.web.model.MetaData;
import it.sevenbits.web.model.UpdateTaskRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class TasksService implements ITasksService {
    private ITasksRepository tasksRepository;
    private final IdValidator idValidator;
    private final StatusValidator statusValidator;
    private final OrderValidator orderValidator;
    private final PageValidator pageValidator;
    private final PageSizeValidator pageSizeValidator;

    private final String STATUS_QUERY = "status";
    private final String ORDER_QUERY = "order";
    private final String PAGE_QUERY = "page";
    private final String SIZE_QUERY = "size";

    private final String MAIN_PATH = "/tasks";
    private final URI DEFAULT_URI = UriComponentsBuilder.fromPath(MAIN_PATH).build().toUri();


    public TasksService(final ITasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
        idValidator = new IdValidator();
        statusValidator = new StatusValidator();
        orderValidator = new OrderValidator();
        pageValidator = new PageValidator();
        pageSizeValidator = new PageSizeValidator();
    }


    private URI buildUri(final String status, final String order, final int page, final int size) {
        if (pageValidator.check(page)) {
            return UriComponentsBuilder.fromPath(MAIN_PATH)
                    .queryParam(STATUS_QUERY, status)
                    .queryParam(ORDER_QUERY, order)
                    .queryParam(PAGE_QUERY, page)
                    .queryParam(SIZE_QUERY, size)
                    .build().toUri();
        }
    return DEFAULT_URI;
    }

    @Override
    public ResponseEntity<GetTasksResponse> getTasks(final String status, final String order, final int page, final int size) {
        List<Task> result;
        MetaData metaData;
        if (!statusValidator.check(status)
                || !orderValidator.check(order)
                || !pageValidator.check(page)
                || !pageSizeValidator.check(size)) {
            result = new ArrayList<>();
            metaData = new MetaData(result.size(), page, size, DEFAULT_URI, DEFAULT_URI, DEFAULT_URI, DEFAULT_URI);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(new GetTasksResponse(result, metaData));
        }
        result = tasksRepository.getAllTasks(status, order, page, size);
        URI prev = buildUri(status, order, page - 1, size);
        URI next = buildUri(status, order, page + 1, size);
        URI first = buildUri(status, order, 1, size);
        URI last = buildUri(status, order, result.size() % size, size);
        metaData = new MetaData(result.size(), page, size, prev, next, first, last);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(new GetTasksResponse(result, metaData));
    }

    @Override
    public ResponseEntity<Task> getTaskById(final String id) {
        if (!idValidator.check(id)) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        Task task = tasksRepository.getTaskById(id);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .ok()
                .body(task);
    }

    @Override
    public ResponseEntity<?> updateTask(final String id, final UpdateTaskRequest updateTaskRequest) {
        if (!idValidator.check(id)) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        Task old = tasksRepository.getTaskById(id);

        if (old == null) {
            return ResponseEntity.notFound()
                    .build();
        }

        String status = updateTaskRequest.getStatus();
        String text = updateTaskRequest.getText();
        if (!statusValidator.check(status) && status != null && !status.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        if ((status == null || status.isEmpty()) && (text == null || text.isEmpty())) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
        String newText = text == null ? old.getText() : text;
        String newStatus = status == null ? old.getStatus() : status;
        Task newTask = new Task(old.getId(), newText, newStatus, old.getCreatedAt(), old.getUpdatedAt());
        tasksRepository.replace(id, newTask);
        return ResponseEntity
                .noContent()
                .build();
    }

    @Override
    public ResponseEntity<?> removeTask(final String id) {
        if (!idValidator.check(id)) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        if (tasksRepository.removeTask(id) == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity
                .ok()
                .build();
    }

    @Override
    public ResponseEntity<?> createtask(final AddTaskRequest addTaskRequest) {
        Task createdTask = tasksRepository.create(addTaskRequest);
        URI location = UriComponentsBuilder
                .fromPath("/tasks/")
                .path(createdTask.getId())
                .build()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return ResponseEntity
                .created(location)
                .headers(headers)
                .build();
    }
}

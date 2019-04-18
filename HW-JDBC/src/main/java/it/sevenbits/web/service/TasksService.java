package it.sevenbits.web.service;

import it.sevenbits.core.model.Task;
import it.sevenbits.core.repository.ITasksRepository;
import it.sevenbits.core.service.validators.PageSizeValidator;
import it.sevenbits.core.service.validators.PageValidator;
import it.sevenbits.core.service.validators.OrderValidator;
import it.sevenbits.core.service.validators.StatusValidator;
import it.sevenbits.core.service.validators.IdValidator;
import it.sevenbits.web.model.metadata.MetaData;
import it.sevenbits.web.model.metadata.MetaDataDefault;
import it.sevenbits.web.model.request.AddTaskRequest;
import it.sevenbits.web.model.request.UpdateTaskRequest;
import it.sevenbits.web.model.response.GetTasksResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * A service to work with tasks repository and controller.
 */
public class TasksService implements ITasksService {
    private ITasksRepository tasksRepository;
    private MetaDataDefault metaDataDefault;

    private final IdValidator idValidator;
    private final StatusValidator statusValidator;
    private final OrderValidator orderValidator;
    private final PageValidator pageValidator;
    private final PageSizeValidator pageSizeValidator;


    /**
     * The constructor.
     *
     * @param tasksRepository a tasks repository.
     * @param metaDataDefault an object that contains default values.
     */
    public TasksService(final ITasksRepository tasksRepository, final MetaDataDefault metaDataDefault) {
        this.tasksRepository = tasksRepository;
        this.metaDataDefault = metaDataDefault;
        idValidator = new IdValidator();
        statusValidator = new StatusValidator(metaDataDefault);
        orderValidator = new OrderValidator();
        pageValidator = new PageValidator(metaDataDefault);
        pageSizeValidator = new PageSizeValidator(metaDataDefault);
    }


    private URI buildUri(final String status, final String order, final int page, final int size) {
        if (pageValidator.check(page)) {
            return UriComponentsBuilder.fromPath(metaDataDefault.getQueryMainPath())
                    .queryParam(metaDataDefault.getQueryStatus(), status)
                    .queryParam(metaDataDefault.getQueryOrder(), order)
                    .queryParam(metaDataDefault.getQueryPage(), page)
                    .queryParam(metaDataDefault.getQuerySize(), size)
                    .build().toUri();
        }
        return UriComponentsBuilder.fromPath(metaDataDefault.getQueryMainPath()).build().toUri();
    }

    @Override
    public ResponseEntity<GetTasksResponse> getTasks(final String status, final String order, final int page, final int size) {
        List<Task> result;
        MetaData metaData;
        String newStatus = statusValidator.check(status) ? status : metaDataDefault.getStatus();
        String newOrder = orderValidator.check(order) ? order : metaDataDefault.getOrder();
        int newPage = pageValidator.check(page) ? page : metaDataDefault.getPage();
        int newSize = pageSizeValidator.check(size) ? size : metaDataDefault.getSize();

        result = tasksRepository.getAllTasks(newStatus, newOrder, newPage, newSize);

        int total = tasksRepository.getTotalCount();
        int tasksListSize = result.size();
        int nextPage = tasksListSize < newSize ? newPage : newPage + 1;
        int lastPage = tasksListSize < newSize ? metaDataDefault.getPage() : tasksListSize / newSize;
        int prevPage = newPage == 1 ? newPage : newPage - 1;

        URI prev = buildUri(newStatus, newOrder, prevPage, newSize);
        URI next = buildUri(newStatus, newOrder, nextPage, newSize);
        URI first = buildUri(newStatus, newOrder, metaDataDefault.getMinPage(), newSize);
        URI last = buildUri(newStatus, newOrder, lastPage, newSize);
        metaData = new MetaData(total, newPage, newSize, prev, next, first, last);

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

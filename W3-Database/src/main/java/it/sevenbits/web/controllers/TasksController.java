package it.sevenbits.web.controllers;

import it.sevenbits.core.model.Task;
import it.sevenbits.core.repository.ITasksRepository;
import it.sevenbits.core.repository.exceptions.TasksRepositoryException;
import it.sevenbits.core.service.validators.IdValidator;
import it.sevenbits.core.service.validators.StatusValidator;
import it.sevenbits.web.model.AddTaskRequest;
import it.sevenbits.web.model.UpdateTaskRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/tasks")
public class TasksController {
    private final ITasksRepository tasksRepository;
    private final IdValidator idValidator;
    private StatusValidator statusValidator;

    public TasksController(final ITasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
        idValidator = new IdValidator();
        statusValidator = new StatusValidator();
    }

    @RequestMapping
            (
                    method = RequestMethod.GET
            )
    @ResponseBody
    public ResponseEntity<List<Task>> getList(final @RequestParam(value = "status", required = false, defaultValue = "inbox") String status) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(tasksRepository.getAllTasks(status));
    }

    @RequestMapping
            (
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
            )
    @ResponseBody
    public ResponseEntity<Task> create(final @RequestBody @Valid AddTaskRequest taskRequest) {
        Task createdTask = tasksRepository.create(taskRequest);
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
                .body(createdTask);
    }

    @RequestMapping
            (
                    method = RequestMethod.GET,
                    value = "/{id}",
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
                    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            )
    @ResponseBody
    public ResponseEntity<Task> findTaskById(final @PathVariable(value = "id") String id) {
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

    @RequestMapping
            (
                    method = RequestMethod.PATCH,
                    value = "/{id}",
                    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            )
    @ResponseBody
    public ResponseEntity<?> updateTask(final @PathVariable(value = "id") String id, final @RequestBody @Valid UpdateTaskRequest taskRequest) {
        if (!idValidator.check(id)) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        Task old = tasksRepository.getTaskById(id);

        if (old == null) {
            return ResponseEntity.notFound().build();
        }

        if (!statusValidator.check(taskRequest.getStatus())) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        String text = taskRequest.getText() == null ? old.getText() : taskRequest.getText();
        Task newTask = new Task(old.getId(), text, taskRequest.getStatus(), old.getCreatedAt(), old.getUpdatedAt());
        tasksRepository.replace(id, newTask);
        return ResponseEntity
                .noContent()
                .build();
    }

    @RequestMapping
            (
                    method = RequestMethod.DELETE,
                    value = "/{id}",
                    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            )
    @ResponseBody
    public ResponseEntity<?> removeTask(final @PathVariable(value = "id") String id) {
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


}

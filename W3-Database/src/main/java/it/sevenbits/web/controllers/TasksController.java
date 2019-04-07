package it.sevenbits.web.controllers;

import it.sevenbits.core.model.Task;
import it.sevenbits.core.repository.ITasksRepository;
import it.sevenbits.core.service.validators.IdValidator;
import it.sevenbits.core.service.validators.StatusValidator;
import it.sevenbits.web.model.AddTaskRequest;
import it.sevenbits.web.model.UpdateTaskRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * It is a controller.
 */
@Controller
@RequestMapping(value = "/tasks")
public class TasksController {
    private final ITasksRepository tasksRepository;
    private final IdValidator idValidator;
    private StatusValidator statusValidator;

    /**
     * A constructor with one parameter.
     *
     * @param tasksRepository is some repository.
     */
    public TasksController(final ITasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
        idValidator = new IdValidator();
        statusValidator = new StatusValidator();
    }

    /**
     * The method works with type GET. It returns all tasks that has the same status.
     *
     * @param status the status of needed tasks.
     * @return a tasks list
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Task>> getList(
            final @RequestParam(
                    value = "status",
                    required = false,
                    defaultValue = "inbox") String status
    ) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(tasksRepository.getAllTasks(status));
    }

    /**
     * The method works with type POST. It creates a new task in repository using taskRequest info.
     *
     * @param taskRequest contains info for the new task.
     * @return the new task.
     */
    @RequestMapping
            (
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
            )
    @ResponseBody
    public ResponseEntity<?> create(final @RequestBody @Valid AddTaskRequest taskRequest) {
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
                .build();
    }

    /**
     * The method works with type GET. It returns a task that has the same id.
     *
     * @param id the id of needed task.
     * @return the task.
     */
    @RequestMapping
            (
                    method = RequestMethod.GET,
                    value = "/{id}",
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
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

    /**
     * The method works with type PATCH. It updates an existing task.
     *
     * @param id          the id of task to update.
     * @param taskRequest some info that is needed to update the task.
     * @return response status.
     */
    @RequestMapping
            (
                    method = RequestMethod.PATCH,
                    value = "/{id}",
                    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            )
    @ResponseBody
    public ResponseEntity<?> updateTask(
            final @PathVariable(value = "id") String id,
            final @RequestBody @Valid UpdateTaskRequest taskRequest
    ) {
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

    /**
     * The method works with type DELETE. It deletes a task with the same id.
     *
     * @param id the id of task to delete.
     * @return the deleted task.
     */
    @RequestMapping
            (
                    method = RequestMethod.DELETE,
                    value = "/{id}"
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

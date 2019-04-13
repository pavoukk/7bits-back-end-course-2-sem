package it.sevenbits.web.controllers;

import it.sevenbits.core.model.Task;
import it.sevenbits.core.repository.ITasksRepository;
import it.sevenbits.web.model.AddTaskRequest;
import it.sevenbits.web.model.GetTasksResponse;
import it.sevenbits.web.model.MetaDataDefault;
import it.sevenbits.web.model.UpdateTaskRequest;
import it.sevenbits.web.service.ITasksService;
import it.sevenbits.web.service.TasksService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

/**
 * It is a controller.
 */
@Controller
@RequestMapping(value = "/tasks")
public class TasksController {
    private final ITasksRepository tasksRepository;
    private ITasksService tasksService;
//    private final IdValidator idValidator;
//    private StatusValidator statusValidator;

    /**
     * A constructor with one parameter.
     *
     * @param tasksRepository is some repository.
     */
    public TasksController(final ITasksRepository tasksRepository, final MetaDataDefault metaDataDefault) {
        this.tasksRepository = tasksRepository;
//        idValidator = new IdValidator();
//        statusValidator = new StatusValidator();
        tasksService = new TasksService(tasksRepository, metaDataDefault);
    }

    /**
     * The method works with type GET. It returns all tasks that has the same status.
     *
     * @param status the status of needed tasks.
     * @return a tasks list
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GetTasksResponse> getList(
            final @RequestParam(
                    value = "status",
                    required = false) String status,
            final @RequestParam(
                    value = "order",
                    required = false
            ) String order,
            final @RequestParam(
                    value = "page",
                    required = false
            ) String page,
            final @RequestParam (
                    value = "size",
                    required = false
            ) String size
    ) {
        int pageArg = page == null || page.isEmpty() ? 0 : Integer.valueOf(page);
        int sizeArg = size == null || size.isEmpty() ? 0 : Integer.valueOf(size);
        return tasksService.getTasks(status, order, pageArg, sizeArg);
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
        return tasksService.createtask(taskRequest);
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
        return tasksService.getTaskById(id);
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
            final @RequestBody UpdateTaskRequest taskRequest
    ) {
        return tasksService.updateTask(id, taskRequest);
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
        return tasksService.removeTask(id);
    }


}

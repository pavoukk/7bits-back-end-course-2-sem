package it.sevenbits.web.controllers;

import it.sevenbits.core.model.Task;
import it.sevenbits.core.repository.tasks.ITasksRepository;
import it.sevenbits.web.model.tasks.request.AddTaskRequest;
import it.sevenbits.web.model.tasks.response.GetTasksResponse;
import it.sevenbits.web.model.metadata.MetaDataDefault;
import it.sevenbits.web.model.tasks.request.UpdateTaskRequest;
import it.sevenbits.web.service.tasks.ITasksService;
import it.sevenbits.web.service.tasks.TasksService;
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

    /**
     * A constructor with one parameter.
     *
     * @param tasksRepository is some repository.
     * @param metaDataDefault contains all default query values.
     */
    public TasksController(final ITasksRepository tasksRepository, final MetaDataDefault metaDataDefault) {
        this.tasksRepository = tasksRepository;
        tasksService = new TasksService(tasksRepository, metaDataDefault);
    }

    /**
     * The method works with type GET. It returns all tasks that has the same status.
     *
     * @param status the status of needed tasks.
     * @param order  an order in which all tasks will be sorted
     * @param page   a current page value
     * @param size   size of the page
     * @return a tasks list
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GetTasksResponse> getList(
            @RequestParam(
                    value = "status",
                    required = false) final String status,
            @RequestParam(
                    value = "order",
                    required = false
            ) final String order,
            @RequestParam(
                    value = "page",
                    required = false
            ) final String page,
            @RequestParam(
                    value = "size",
                    required = false
            ) final String size
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
    public ResponseEntity<?> create(@RequestBody @Valid final AddTaskRequest taskRequest) {
        return tasksService.createtask(taskRequest);
    }

    /**
     * The method works with type GET. It returns a task that has the same ID.
     *
     * @param id the ID of needed task.
     * @return the task.
     */
    @RequestMapping
            (
                    method = RequestMethod.GET,
                    value = "/{id}",
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
            )
    @ResponseBody
    public ResponseEntity<Task> findTaskById(@PathVariable(value = "id") final String id) {
        return tasksService.getTaskById(id);
    }

    /**
     * The method works with type PATCH. It updates an existing task.
     *
     * @param id          the ID of task to update.
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
            @PathVariable(value = "id") final String id,
            @RequestBody final UpdateTaskRequest taskRequest
    ) {
        return tasksService.updateTask(id, taskRequest);
    }

    /**
     * The method works with type DELETE. It deletes a task with the same ID.
     *
     * @param id the ID of task to delete.
     * @return the deleted task.
     */
    @RequestMapping
            (
                    method = RequestMethod.DELETE,
                    value = "/{id}"
            )
    @ResponseBody
    public ResponseEntity<?> removeTask(@PathVariable(value = "id") final String id) {
        return tasksService.removeTask(id);
    }


}

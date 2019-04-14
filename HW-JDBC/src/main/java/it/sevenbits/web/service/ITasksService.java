package it.sevenbits.web.service;

import it.sevenbits.core.model.Task;
import it.sevenbits.web.model.request.AddTaskRequest;
import it.sevenbits.web.model.response.GetTasksResponse;
import it.sevenbits.web.model.request.UpdateTaskRequest;
import org.springframework.http.ResponseEntity;

/**
 * The interface describes a service that works with some repository and sends answers to some controller.
 */
public interface ITasksService {
    /**
     * The method returns list of tasks filtered by parameters.
     *
     * @param status is used to filter all tasks by a status.
     * @param order  is used to set an order for tasks list.
     * @param page   is used to set a current page number.
     * @param size   is used to set a page size.
     * @return tasks list.
     */
    ResponseEntity<GetTasksResponse> getTasks(String status, String order, int page, int size);

    /**
     * The method returns a task with specified id.
     *
     * @param id the id.
     * @return needed task.
     */
    ResponseEntity<Task> getTaskById(String id);

    /**
     * The method updates some task with specified id using updateTaskRequest containing all needed info.
     *
     * @param id                the id.
     * @param updateTaskRequest all the needed info.
     * @return a response with some status.
     */
    ResponseEntity<?> updateTask(String id, UpdateTaskRequest updateTaskRequest);

    /**
     * Removes a task with specified id.
     *
     * @param id the id.
     * @return a response with some status.
     */
    ResponseEntity<?> removeTask(String id);

    /**
     * Creates a task using addTaskRequest containing all needed info.
     *
     * @param addTaskRequest the needed info.
     * @return a response that says if the task is created or not using status codes.
     */
    ResponseEntity<?> createtask(AddTaskRequest addTaskRequest);
}

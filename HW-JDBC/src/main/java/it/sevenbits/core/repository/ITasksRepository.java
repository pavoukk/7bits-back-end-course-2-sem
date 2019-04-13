package it.sevenbits.core.repository;

import it.sevenbits.core.model.Task;
import it.sevenbits.web.model.AddTaskRequest;

import java.util.List;

/**
 * The interface defines a repository that must store Task objects.
 */
public interface ITasksRepository {
    /**
     * The method returns all tasks from repository.
     *
     * @param status is used to filter tasks and get those that have the same status.
     * @param order  is used to set an order for tasks list
     * @param page   is used to set current page number
     * @param size   is used to set a page size
     * @return a list of tasks.
     */
    List<Task> getAllTasks(String status, String order, int page, int size);

    /**
     * The method creates a new task in repository.
     *
     * @param task is an object that contains information for the new task.
     * @return the new task.
     */
    Task create(AddTaskRequest task);

    /**
     * The method gets task by identificator.
     *
     * @param id is an identificator.
     * @return a task that has the same id.
     */
    Task getTaskById(String id);

    /**
     * The method replaces some old task with the same id to newTask.
     *
     * @param id      is used to find the old task.
     * @param newTask is the new task replacing the old one.
     */
    void replace(String id, Task newTask);

    /**
     * The method removes a task with the same id.
     *
     * @param id is used to remove the task
     * @return the removed task
     */
    Task removeTask(String id);
}

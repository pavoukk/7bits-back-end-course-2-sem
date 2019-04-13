package it.sevenbits.web.service;

import it.sevenbits.web.model.UpdateTaskRequest;
import org.springframework.http.ResponseEntity;

/**
 * The interface describes a service that works with some repository and sends answers to some controller.
 */
public interface IService {
    /**
     * The method updates some task with specified id using updateTaskRequest containing all needed info.
     *
     * @param id                the id.
     * @param updateTaskRequest all the needed info.
     * @return a response with some status.
     */
    ResponseEntity<?> updateTask(String id, UpdateTaskRequest updateTaskRequest);
}

package it.sevenbits.web.service;

import it.sevenbits.core.model.Task;
import it.sevenbits.core.repository.ITasksRepository;
import it.sevenbits.core.service.validators.IdValidator;
import it.sevenbits.core.service.validators.StatusValidator;
import it.sevenbits.web.model.UpdateTaskRequest;
import org.springframework.http.ResponseEntity;

/**
 * It is a service that works with tasks repository.
 */
public class TasksService implements IService {
    private ITasksRepository tasksRepository;

    private IdValidator idValidator;

    private StatusValidator statusValidator;

    /**
     * The constructor.
     *
     * @param tasksRepository is a repository to work with.
     * @param idValidator     is a validator that checks id correctness.
     * @param statusValidator is a validator that checks status correctness.
     */
    public TasksService(final ITasksRepository tasksRepository,
                        final IdValidator idValidator,
                        final StatusValidator statusValidator) {
        this.tasksRepository = tasksRepository;
        this.idValidator = idValidator;
        this.statusValidator = statusValidator;
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
}

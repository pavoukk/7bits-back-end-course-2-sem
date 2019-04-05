package it.sevenbits.core.repository.exceptions;

public class TasksRepositoryException extends Exception {
    public TasksRepositoryException() {
    }

    public TasksRepositoryException(String message) {
        super(message);
    }

    public TasksRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public TasksRepositoryException(Throwable cause) {
        super(cause);
    }
}

package it.sevenbits.core.repository.tasks.exceptions;

/**
 * A tasks repository exception class.
 */
public class TasksRepositoryException extends Exception {
    /**
     * A constructor with no parameters
     */
    public TasksRepositoryException() {
    }

    /**
     * A constructor with String parameter.
     *
     * @param message an error message.
     */
    public TasksRepositoryException(final String message) {
        super(message);
    }

    /**
     * A constructor with a message and a cause parameter.
     *
     * @param message an error message.
     * @param cause   an error cause.
     */
    public TasksRepositoryException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * A constructor with a cause parameter.
     *
     * @param cause an error cause.
     */
    public TasksRepositoryException(final Throwable cause) {
        super(cause);
    }
}

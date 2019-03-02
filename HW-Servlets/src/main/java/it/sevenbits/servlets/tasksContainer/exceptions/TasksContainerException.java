package it.sevenbits.servlets.tasksContainer.exceptions;

public class TasksContainerException extends Throwable {
    public TasksContainerException() {
        super();
    }

    public TasksContainerException(String message) {
        super(message);
    }

    public TasksContainerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TasksContainerException(Throwable cause) {
        super(cause);
    }
}

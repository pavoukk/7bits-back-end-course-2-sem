package it.sevenbits.core.model;

/**
 * A model of a task. It has unique id, text of the task, status and when the task was created and updated.
 */
public class Task {
    private String id;

    private String text;

    private String status;

    /**
     * A constructor with parameters.
     *
     * @param id     a unique identificator.
     * @param text   text of some task.
     * @param status status of the task.
     */
    public Task(final String id, final String text, final String status) {
        this.id = id;
        this.text = text;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getStatus() {
        return status;
    }
}

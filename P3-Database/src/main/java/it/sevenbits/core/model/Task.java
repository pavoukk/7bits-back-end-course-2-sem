package it.sevenbits.core.model;

import java.util.Objects;

/**
 * A model of a task. It has unique id, text of the task, status and when the task was created and updated.
 */
public class Task {
    private String id;

    private String text;

    private String status;

    private String createdAt;

    /**
     * A constructor with parameters.
     *
     * @param id        a unique identificator.
     * @param text      text of some task.
     * @param status    status of the task.
     * @param createdAt creation date.
     */
    public Task(final String id, final String text, final String status, final String createdAt) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.createdAt = createdAt;
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

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(text, task.text) &&
                Objects.equals(status, task.status) &&
                Objects.equals(createdAt, task.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, status, createdAt);
    }
}

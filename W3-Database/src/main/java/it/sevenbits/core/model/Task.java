package it.sevenbits.core.model;

import java.util.Objects;

public class Task {
    private String id;

    private String text;

    private String status;

    private String createdAt;

    private String updatedAt;

    public Task(String id, String text, String status, String createdAt, String updatedAt) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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

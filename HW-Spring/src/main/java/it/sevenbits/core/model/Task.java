package it.sevenbits.core.model;

public class Task {
    private String id;

    private String text;

    private String status;

    public Task(String id, String text, String status) {
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

package it.sevenbits.tasksContainer;

import java.util.UUID;

public class Task {
    private String id;
    private String name;

    public Task(String name) {
        id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
}
}

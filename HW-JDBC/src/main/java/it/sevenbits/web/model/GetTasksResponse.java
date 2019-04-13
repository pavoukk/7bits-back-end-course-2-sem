package it.sevenbits.web.model;

import it.sevenbits.core.model.Task;

import java.util.List;

public class GetTasksResponse {
    private List<Task> tasks;

    private MetaData metaData;

    public GetTasksResponse(final List<Task> tasks, final MetaData metaData) {
        this.tasks = tasks;
        this.metaData = metaData;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public MetaData getMetaData() {
        return metaData;
    }
}

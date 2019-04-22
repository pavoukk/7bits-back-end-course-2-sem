package it.sevenbits.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.core.model.Task;
import it.sevenbits.web.model.metadata.MetaData;

import java.util.List;

/**
 * The class is used to contain a response to GET request: meta data and list of tasks.
 */
public class GetTasksResponse {
    private List<Task> tasks;

    @JsonProperty("__meta")
    private MetaData metaData;

    /**
     * The constructor.
     *
     * @param tasks    list of tasks to return
     * @param metaData list of meta data bounded up with the list.
     */
    public GetTasksResponse(final List<Task> tasks,  final MetaData metaData) {
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

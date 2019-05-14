package it.sevenbits.web.model.tasks.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A model that contains information that is needed to update a task.
 */
public class UpdateTaskRequest {
    private String text;

    private String status;

    /**
     * A constructor with parameters.
     *
     * @param text   is a text of the task.
     * @param status is a status of the task.
     */
    @JsonCreator
    public UpdateTaskRequest(@JsonProperty("text") final String text, @JsonProperty("status") final String status) {
        this.status = status;
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }
}

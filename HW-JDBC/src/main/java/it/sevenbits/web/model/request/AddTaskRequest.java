package it.sevenbits.web.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * Is Used to transform a text that describes a task.
 */
public class AddTaskRequest {
    @NotBlank
    private String text;

    /**
     * A constructor that takes one parameter.
     *
     * @param text describes a task.
     */
    @JsonCreator
    public AddTaskRequest(@JsonProperty("text") final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}



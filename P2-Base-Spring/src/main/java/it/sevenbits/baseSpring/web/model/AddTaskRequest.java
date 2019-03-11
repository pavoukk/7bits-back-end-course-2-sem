package it.sevenbits.baseSpring.web.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddTaskRequest {
    private String text;

    @JsonCreator
    public AddTaskRequest(@JsonProperty("text") String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

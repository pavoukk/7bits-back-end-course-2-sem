package it.sevenbits.baseSpring.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Task {
    private String id;

    private String text;

    @JsonCreator
    public Task(@JsonProperty("text") String text) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}

package it.sevenbits.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class UpdateTaskRequest {
    @NotBlank
    private String text;

    @NotBlank
    private String status;

    @JsonCreator
    public UpdateTaskRequest(final @JsonProperty("text") String text, final @JsonProperty("status") String status) {
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

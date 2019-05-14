package it.sevenbits.web.model.users.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * A model for sign in request.
 */
public class SignInRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    /**
     * Request comes from json body.
     *
     * @param username a user's name.
     * @param password a user's PASSWORD.
     */
    @JsonCreator
    public SignInRequest(@JsonProperty("username") final String username,
                         @JsonProperty("password")final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

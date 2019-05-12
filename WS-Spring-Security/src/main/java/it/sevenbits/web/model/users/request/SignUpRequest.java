package it.sevenbits.web.model.users.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * A model for sign up request info.
 */
public class SignUpRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    /**
     * A constructor.
     *
     * @param username new user's username.
     * @param password new user's PASSWORD.
     */
    @JsonCreator
    public SignUpRequest(@JsonProperty("username") final String username, @JsonProperty("PASSWORD") final String password) {
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

package it.sevenbits.web.model.users.response;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * A model for sign in response.
 */
public class SignInResponse {
    private String token;

    /**
     * A constructor.
     * @param token some token.
     */
    @JsonCreator
    public SignInResponse(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

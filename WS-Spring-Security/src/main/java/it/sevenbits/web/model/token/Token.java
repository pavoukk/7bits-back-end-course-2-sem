package it.sevenbits.web.model.token;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A model for token.
 */
public class Token {
    private final String token;

    /**
     * A constructor.
     *
     * @param token some token.
     */
    @JsonCreator
    public Token(@JsonProperty("token") final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

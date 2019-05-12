package it.sevenbits.web.model.users.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A model for update user request.
 */
public class UpdateUserRequest {
    private List<String> authorities;

    private Boolean enabled;

    /**
     * A constructor.
     * @param enabled an account status.
     * @param authorities new user's authorities.
     */
    @JsonCreator
    public UpdateUserRequest(@JsonProperty("enabled") final Boolean enabled,
                             @JsonProperty("authorities") final List<String> authorities) {
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public Boolean isEnabled() {
        return enabled;
    }
}

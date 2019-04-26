package it.sevenbits.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * A model of a user.
 */
public class User {
    @NotBlank
    @JsonProperty("username")
    private final String username;

    @NotBlank
    @JsonProperty("authorities")
    private final List<String> authorities;

    @JsonIgnore
    private final String password;

    /**
     * A constructor. Is needed to provide a work in users repository.
     *
     * @param username    a user's name.
     * @param password    a user's password.
     * @param authorities user's roles. Are needed to decide whether
     *                    the user has access to some resources or has not.
     */
    public User(final String username, final String password, final List<String> authorities) {
        this.username = username;
        this.authorities = authorities;
        this.password = password;
    }

    /**
     * A constructor. Is needed to work with sign in requests or provide
     * some other work.
     *
     * @param username    user's name.
     * @param authorities user's roles. Are needed to decide whether
     *                    the user has access to some resources or has not.
     */
    @JsonCreator
    public User(final String username, final List<String> authorities) {
        this.username = username;
        this.password = null;
        this.authorities = authorities;
    }

    //TODO: fill the javaDoc

    /**
     * A constructor.
     *
     * @param authentication an Authentication object.
     */
    public User(final Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        password = null;

        authorities = new ArrayList<>();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            authorities.add(authority.getAuthority());
        }
    }

    public String getUsername() {
        return username;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }
}

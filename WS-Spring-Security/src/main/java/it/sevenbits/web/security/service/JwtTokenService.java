package it.sevenbits.web.security.service;

import it.sevenbits.core.model.User;
import org.springframework.security.core.Authentication;

import java.time.Duration;

/**
 * It's an interface that describes token work. All implementing objects is
 * used to code and decode tokens.
 */
public interface JwtTokenService {
    /**
     * The method creates a token.
     *
     * @param user is needed to create a token.
     * @return the token.
     */
    String createToken(User user);

    /**
     * The method returns time to live for the token.
     *
     * @return time to live.
     */
    Duration getTokenExpiredIn();

    /**
     * The method parses a token.
     *
     * @param token a token to parse.
     * @return Authentication object.
     */
    Authentication parseToken(String token);
}

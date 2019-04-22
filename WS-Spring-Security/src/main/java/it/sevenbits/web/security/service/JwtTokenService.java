package it.sevenbits.web.security.service;

import it.sevenbits.core.model.User;
import org.springframework.security.core.Authentication;

import java.time.Duration;

public interface JwtTokenService {
    String createToken(User user);

    Duration getTokenExpiredIn();

    Authentication parseToken(String token);
}

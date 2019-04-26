package it.sevenbits.web.security.provider;

import it.sevenbits.web.security.exceptions.JwtAuthenticationException;
import it.sevenbits.web.security.service.JwtTokenService;
import it.sevenbits.web.security.user_views.JwtToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * The class is needed to process authentication request and
 * return fully authenticated Authentication object filled with credentials.
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final JwtTokenService tokenService;

    /**
     * A constructor that gets a token service to provide a token work.
     *
     * @param tokenService the service.
     */
    public JwtAuthenticationProvider(final JwtTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return (JwtToken.class.isAssignableFrom(authentication));
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        String token = String.valueOf(authentication.getCredentials());
        logger.debug("Authenticating {}", token);

        try {
            return tokenService.parseToken(token);
        } catch (Exception e) {
            throw new JwtAuthenticationException("Invalid token received", e);
        }
    }
}
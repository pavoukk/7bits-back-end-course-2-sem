package it.sevenbits.web.security.filter;

import it.sevenbits.web.security.userviews.JwtToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * It is an abstract class that is used to work with tokens. A method
 * that gets a token is defined in concrete classes extending it.
 */
public abstract class JwtAuthFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * Is needed to log events.
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * A constructor that gets a request matcher.
     *
     * @param matcher the matcher.
     */
    public JwtAuthFilter(final RequestMatcher matcher) {
        super(matcher);
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request,
                                                final HttpServletResponse response)
            throws AuthenticationException {
        String token;
        try {
            token = takeToken(request);
        } catch (Exception e) {
            logger.warn("Failed to get token: {}", e.getMessage());
            return anonymousToken();
        }
        return new JwtToken(token);
    }

    /**
     * A method that takes a token. Is defined in extending classes.
     *
     * @param request some request.
     * @return a token.
     * @throws AuthenticationException if something goes wrong, for example if there's an invalid header or cookie.
     */
    protected abstract String takeToken(HttpServletRequest request) throws AuthenticationException;

    private Authentication anonymousToken() {
        return new AnonymousAuthenticationToken(
                "ANONYMOUS", "ANONYMOUS",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))
        );
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request,
                                            final HttpServletResponse response,
                                            final FilterChain chain,
                                            final Authentication authResult)
            throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }
}
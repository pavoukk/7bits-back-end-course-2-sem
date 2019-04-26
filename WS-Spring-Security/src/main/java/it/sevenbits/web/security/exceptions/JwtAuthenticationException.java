package it.sevenbits.web.security.exceptions;


import org.springframework.security.core.AuthenticationException;

/**
 * The exception is thrown if authentication process goes wrong.
 */
public class JwtAuthenticationException extends AuthenticationException {

    /**
     * A constructor.
     * @param message some exception message.
     */
    public JwtAuthenticationException(final String message) {
        super(message);
    }

    /**
     * A constructor.
     * @param message some exception message.
     * @param cause some cause of the exception.
     */
    public JwtAuthenticationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}

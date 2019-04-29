package it.sevenbits.web.service.signin.exceptions;

/**
 * The exception is thrown if sign in process goes wrong.
 */
public class SignInFailedException extends Exception {
    /**
     * A constructor.
     */
    public SignInFailedException() {
    }

    /**
     * A constructor.
     *
     * @param message some exception message.
     */
    public SignInFailedException(final String message) {
        super(message);
    }

    /**
     * A constructor.
     *
     * @param message some exception message.
     * @param cause   some cause of the exception.
     */
    public SignInFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * A constructor.
     *
     * @param cause some cause of the exception.
     */
    public SignInFailedException(final Throwable cause) {
        super(cause);
    }
}

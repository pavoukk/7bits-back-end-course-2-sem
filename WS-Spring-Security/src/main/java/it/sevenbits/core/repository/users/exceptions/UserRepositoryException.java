package it.sevenbits.core.repository.users.exceptions;

/**
 * A users repository exception.
 */
public class UserRepositoryException extends Exception {
    /**
     * A default constructor.
     */
    public UserRepositoryException() {
    }

    /**
     * A constructor that takes an error cause string.
     *
     * @param s the error cause string.
     */
    public UserRepositoryException(final String s) {
        super(s);
    }

    /**
     * A constructor that takes an error cause string and error cause Throwable object.
     *
     * @param s         the error cause string.
     * @param throwable the error cause Throwable object.
     */
    public UserRepositoryException(final String s, final Throwable throwable) {
        super(s, throwable);
    }

    /**
     * A constructor that takes an error cause Throwable object.
     *
     * @param throwable the error cause Throwable object.
     */
    public UserRepositoryException(final Throwable throwable) {
        super(throwable);
    }
}

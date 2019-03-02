package it.sevenbits.servlets.sessions.exceptions;

public class SessionsException extends Throwable {
    public SessionsException() {
        super();
    }

    public SessionsException(String message) {
        super(message);
    }

    public SessionsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionsException(Throwable cause) {
        super(cause);
    }
}

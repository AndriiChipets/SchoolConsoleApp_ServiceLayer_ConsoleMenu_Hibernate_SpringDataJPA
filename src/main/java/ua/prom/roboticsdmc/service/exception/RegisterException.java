package ua.prom.roboticsdmc.service.exception;

public class RegisterException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RegisterException() {
    }

    public RegisterException(String message) {
        super(message);
    }

    public RegisterException(String message, Exception cause) {
        super(message, cause);
    }
}

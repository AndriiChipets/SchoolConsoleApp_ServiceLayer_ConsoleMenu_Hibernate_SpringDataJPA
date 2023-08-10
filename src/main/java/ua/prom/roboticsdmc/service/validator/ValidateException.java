package ua.prom.roboticsdmc.service.validator;

public class ValidateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ValidateException() {
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Exception cause) {
        super(message, cause);
    }
}

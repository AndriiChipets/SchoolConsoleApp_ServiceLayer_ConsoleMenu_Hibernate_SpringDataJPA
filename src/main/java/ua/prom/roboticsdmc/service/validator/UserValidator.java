package ua.prom.roboticsdmc.service.validator;

import java.util.function.Function;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import ua.prom.roboticsdmc.domain.UserRegistrationRequest;

@Service
public class UserValidator implements Validator<UserRegistrationRequest> {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&*+/=?`{}~^.-]+@[a-zA-Z0-9.-]+$");
    private static final Pattern PASSWORD_PATTERN = Pattern
            .compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");

    @Override
    public void validate(UserRegistrationRequest registrationRequest) {
        if (registrationRequest == null) {
            throw new ValidateException("User is absent");
        }
        validateEmail(registrationRequest);
        validatePassword(registrationRequest);
    }

    private static void validateEmail(UserRegistrationRequest registrationRequest) {
        validateString(EMAIL_PATTERN, registrationRequest, UserRegistrationRequest::getEmail,
                "Email do not match the pattern");
    }

    private static void validatePassword(UserRegistrationRequest registrationRequest) {
        validateString(PASSWORD_PATTERN, registrationRequest, UserRegistrationRequest::getPassword,
                "Password do not match the pattern");
    }

    private static void validateString(Pattern pattern, UserRegistrationRequest registrationRequest,
            Function<UserRegistrationRequest, String> function, String exceptionMessage) {
        if (!pattern.matcher(function.apply(registrationRequest)).matches()) {
            throw new ValidateException(exceptionMessage);
        }
    }
}

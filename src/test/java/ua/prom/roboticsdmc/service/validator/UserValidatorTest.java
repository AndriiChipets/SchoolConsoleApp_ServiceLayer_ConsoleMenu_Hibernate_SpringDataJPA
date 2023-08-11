package ua.prom.roboticsdmc.service.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import ua.prom.roboticsdmc.config.SchoolApplicationConfig;
import ua.prom.roboticsdmc.domain.User;

@ActiveProfiles("test")
@JdbcTest
@ContextConfiguration(classes=SchoolApplicationConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("UserValidatorTest")
class UserValidatorTest {
    
    @Autowired
    UserValidator validator;

    @Test
    @DisplayName("validate method should do nothing when Email and Password are correct")
    void validate_shouldDoNothing_whenEmailAndPasswordAreCorrect() {

        String firstName = "Patricia";
        String lastName = "Jackson";
        String email = "patricia.jackson@gmail.com";
        String password = "12aBcd@1";
        User user = User.builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withPassword(password)
                .build();

        assertDoesNotThrow(() -> validator.validate(user));
    }
    
    @Test
    @DisplayName("validate method should throw ValidateException when Password isn't correct")
    void validate_shouldThrowValidateException_whenPasswordIsNotCorrect() {

        String firstName = "Patricia";
        String lastName = "Jackson";
        String email = "patricia.jackson@gmail.com";
        String password = "1";
        User user = User.builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withPassword(password)
                .build();

        Exception exception = assertThrows(ValidateException.class, 
                () -> validator.validate(user));
        assertEquals("Password do not match the pattern", exception.getMessage());
    }
    
    @Test
    @DisplayName("validate method should throw ValidateException when Email isn't correct")
    void validate_shouldThrowValidateException_whenEmailIsNotCorrect() {

        String firstName = "Patricia";
        String lastName = "Jackson";
        String email = "not correct password";
        String password = "12aBcd@1";
        User user = User.builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withPassword(password)
                .build();

        Exception exception = assertThrows(ValidateException.class, 
                () -> validator.validate(user));
        assertEquals("Email do not match the pattern", exception.getMessage());
    }

    @Test
    @DisplayName("validate method should throw ValidateException when User is null")
    void validate_shouldThrowValidateException_whenUserIsNull() {

        User user = null;

        Exception exception = assertThrows(ValidateException.class, 
                () -> validator.validate(user));
        assertEquals("User is absent", exception.getMessage());
    }
}

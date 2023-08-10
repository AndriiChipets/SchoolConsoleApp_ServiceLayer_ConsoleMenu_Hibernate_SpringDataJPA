package ua.prom.roboticsdmc.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.prom.roboticsdmc.dao.UserDao;
import ua.prom.roboticsdmc.domain.User;
import ua.prom.roboticsdmc.domain.UserRegistrationRequest;
import ua.prom.roboticsdmc.service.exception.RegisterException;
import ua.prom.roboticsdmc.service.validator.Validator;

@SpringBootTest(classes = {UserServiceImpl.class})
@DisplayName("UserServiceImplTest")
@ExtendWith(value = { MockitoExtension.class })

class UserServiceImplTest {

    @MockBean
    UserDao userDao;
    
    @MockBean
    PasswordEncriptor passwordEncriptor;
    
    @MockBean
    Validator<User> userValidator;

    @Autowired
    UserServiceImpl userServiceImpl;
    
    @Test
    @DisplayName("register method should add new User to data base when entered data are correct")
    void register_shouldAddNewUser_whenThereIsNotUserAndEnteredDataAreCorrect() {

        String firstName = "Patricia";
        String lastName = "Jackson";
        String email = "patricia.jackson@gmail.com";
        String password = "12aBcd@1";
        String encriptPassword = "#%DJHG04";
        UserRegistrationRequest registrationRequest = UserRegistrationRequest.builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withPassword(password)
                .build();

        when(passwordEncriptor.encript(anyString())).thenReturn(encriptPassword);
        when(userDao.findByEmail(anyString())).thenReturn(Optional.empty());
        
        User userWithEncriptPassword = User.builder()
                .withEmail(registrationRequest.getEmail())
                .withPassword(encriptPassword)
                .withFirstName(registrationRequest.getFirstName())
                .withLastName(registrationRequest.getLastName())
                .build();
        
        userServiceImpl.register(registrationRequest);

        verify(userDao).save(userWithEncriptPassword);
    }
    
    @Test
    @DisplayName("register method should throw RegisterException when User is present")
    void register_shouldThrowRegisterException_whenUserIsAlreadyPresent() {

        String firstName = "Patricia";
        String lastName = "Jackson";
        String email = "patricia.jackson@gmail.com";
        String password = "12aBcd@1";
        String encriptPassword = "#%DJHG04";
        UserRegistrationRequest registrationRequest = UserRegistrationRequest.builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withPassword(password)
                .build();
        User userWithEncriptPassword = User.builder()
                .withEmail(registrationRequest.getEmail())
                .withPassword(encriptPassword)
                .withFirstName(registrationRequest.getFirstName())
                .withLastName(registrationRequest.getLastName())
                .build();
        
        when(passwordEncriptor.encript(anyString())).thenReturn(encriptPassword);
        when(userDao.findByEmail(anyString())).thenReturn(Optional.of(userWithEncriptPassword));
        
        Exception exception = assertThrows(RegisterException.class,
                () -> userServiceImpl.register(registrationRequest));
        assertEquals("User is already registred", exception.getMessage());
    }
    
    @Test
    @DisplayName("login method should return False when entered Email and Password is not present")
    void login_shouldReturnFalse_whenEnteredEmailAndPasswordIsNotPresent() {

        String email = "patricia.jackson@gmail.com";
        String password = "12aBcd@1";
        
        when(userDao.findByEmail(anyString())).thenReturn(Optional.empty());
        
        assertFalse(userServiceImpl.login(email, password));
        
        verify(userDao).findByEmail(email);
    }
    
    @Test
    @DisplayName("login method should return True when entered Email and Password is present")
    void login_shouldReturnTrue_whenEnteredEmailAndPasswordIsPresent() {

        String firstName = "Patricia";
        String lastName = "Jackson";
        String email = "patricia.jackson@gmail.com";
        String password = "12aBcd@1";
        String encriptPassword = "#%DJHG04";
        User userWithEncriptPassword = User.builder()
                .withEmail(email)
                .withPassword(encriptPassword)
                .withFirstName(firstName)
                .withLastName(lastName)
                .build();

        when(userDao.findByEmail(anyString())).thenReturn(Optional.of(userWithEncriptPassword));

        assertFalse(userServiceImpl.login(email, password));
        
        verify(userDao).findByEmail(email);
    }
}

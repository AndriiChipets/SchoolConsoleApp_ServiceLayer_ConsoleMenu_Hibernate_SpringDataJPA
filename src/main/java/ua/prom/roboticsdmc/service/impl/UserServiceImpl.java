package ua.prom.roboticsdmc.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.domain.UserRegistrationRequest;
import ua.prom.roboticsdmc.repository.UserRepository;
import ua.prom.roboticsdmc.domain.User;
import ua.prom.roboticsdmc.service.UserService;
import ua.prom.roboticsdmc.service.exception.RegisterException;
import ua.prom.roboticsdmc.service.validator.Validator;

@Service
@Log4j2
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncriptor passwordEncriptor;
    private final Validator<User> userValidator;

    @Override
    public boolean login(String email, String password) {
        log.info("Check if user with email = " + email + " exists in the data base");
        User userToValidate = User.builder()
                .withEmail(email)
                .withPassword(password)
                .build();
        log.info("Validate user with email = " + email);
        userValidator.validate(userToValidate);
        log.info("user with email = " + email + " validated");
        String encriptPassword = passwordEncriptor.encript(password);
        log.info("Return result is user with email = " + email + " exists");
        Optional<User> userByEmail = userRepository.findByEmail(email);
        return userByEmail
                .map(User::getPassword)
                .filter(pass -> pass.equals(encriptPassword))
                .isPresent();
    }

    @Override
    public void register(UserRegistrationRequest registrationRequest) {
        log.info("Register new user");
        User userToValidate = User.builder()
                .withEmail(registrationRequest.getEmail())
                .withPassword(registrationRequest.getPassword())
                .build();
        log.info("Validate new user");
        userValidator.validate(userToValidate);
        log.info("New user is validated");
        log.info("Check if user exists in the data base");
        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            log.warn("User is already registred");
            throw new RegisterException("User is already registred");
        }
        String encriptPassword = passwordEncriptor.encript(registrationRequest.getPassword());
        User userWithEncriptPassword = User.builder()
                .withEmail(registrationRequest.getEmail())
                .withPassword(encriptPassword)
                .withFirstName(registrationRequest.getFirstName())
                .withLastName(registrationRequest.getLastName())
                .build();
        log.info("Add new user to the data base");
        userRepository.save(userWithEncriptPassword);
        log.info("New user added to the data base");
    }
    
}

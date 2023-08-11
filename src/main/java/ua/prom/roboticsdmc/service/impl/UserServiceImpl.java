package ua.prom.roboticsdmc.service.impl;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ua.prom.roboticsdmc.dao.UserDao;
import ua.prom.roboticsdmc.domain.UserRegistrationRequest;
import ua.prom.roboticsdmc.domain.User;
import ua.prom.roboticsdmc.service.UserService;
import ua.prom.roboticsdmc.service.exception.RegisterException;
import ua.prom.roboticsdmc.service.validator.Validator;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncriptor passwordEncriptor;
    private final Validator<User> userValidator;

    @Override
    public boolean login(String email, String password) {

        User userToValidate = User.builder()
                .withEmail(email)
                .withPassword(password)
                .build();
        userValidator.validate(userToValidate);
        String encriptPassword = passwordEncriptor.encript(password);
        return userDao.findByEmail(email)
                .map(User::getPassword)
                .filter(pass -> pass.equals(encriptPassword))
                .isPresent();
    }

    @Override
    public void register(UserRegistrationRequest registrationRequest) {

        User userToValidate = User.builder()
                .withEmail(registrationRequest.getEmail())
                .withPassword(registrationRequest.getPassword())
                .build();
        userValidator.validate(userToValidate);
        if (userDao.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new RegisterException("User is already registred");
        }
        String encriptPassword = passwordEncriptor.encript(registrationRequest.getPassword());
        User userWithEncriptPassword = User.builder()
                .withEmail(registrationRequest.getEmail())
                .withPassword(encriptPassword)
                .withFirstName(registrationRequest.getFirstName())
                .withLastName(registrationRequest.getLastName())
                .build();
        userDao.save(userWithEncriptPassword);
    }
}

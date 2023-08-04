package ua.prom.roboticsdmc.service.impl;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ua.prom.roboticsdmc.dao.UserDao;
import ua.prom.roboticsdmc.domain.UserRegistrationRequest;
import ua.prom.roboticsdmc.domain.User;
import ua.prom.roboticsdmc.service.UserService;
import ua.prom.roboticsdmc.service.validator.Validator;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncriptor passwordEncriptor;
    private final Validator<UserRegistrationRequest> userValidator;

    @Override
    public boolean login(String email, String password) {

        UserRegistrationRequest userRegistrationRequest = UserRegistrationRequest.builder()
                .withEmail(email)
                .withPassword(password)
                .build();
        userValidator.validate(userRegistrationRequest);
        String encriptPassword = passwordEncriptor.encript(password);
        return userDao.findByEmail(email)
                .map(User::getPassword)
                .filter(pass -> pass.equals(encriptPassword))
                .isPresent();
    }

    @Override
    public void register(UserRegistrationRequest registrationRequest) {
        
        userValidator.validate(registrationRequest);
        User userWithEncriptPassword = null;
        if (userDao.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User is already registred");
        } else {
            String encriptPassword = passwordEncriptor.encript(registrationRequest.getPassword());
            userWithEncriptPassword = User.builder()
                    .withEmail(registrationRequest.getEmail())
                    .withPassword(encriptPassword)
                    .withFirstName(registrationRequest.getFirstName())
                    .withLastName(registrationRequest.getLastName())
                    .build();
            userDao.save(userWithEncriptPassword);
        }
    }
}

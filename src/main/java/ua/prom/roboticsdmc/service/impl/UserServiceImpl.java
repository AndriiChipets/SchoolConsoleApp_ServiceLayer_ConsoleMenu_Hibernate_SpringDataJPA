package ua.prom.roboticsdmc.service.impl;

import ua.prom.roboticsdmc.dao.UserDao;
import ua.prom.roboticsdmc.domain.User;
import ua.prom.roboticsdmc.service.PasswordEncriptor;
import ua.prom.roboticsdmc.service.UserService;
import ua.prom.roboticsdmc.service.validator.Validator;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncriptor passwordEncriptor;
    private final Validator<User> userValidator;
    private final User user;

    public UserServiceImpl(UserDao userDao, PasswordEncriptor passwordEncriptor, Validator<User> userValidator,
            User user) {
        this.userDao = userDao;
        this.passwordEncriptor = passwordEncriptor;
        this.userValidator = userValidator;
        this.user = user;
    }

    @Override
    public boolean login(String email, String password) {

        userValidator.validate(user);
        String encriptPassword = passwordEncriptor.encript(password);
        return userDao.findByEmail(email)
                .map(User::getPassword)
                .filter(pass -> pass.equals(encriptPassword))
                .isPresent();
    }

    @Override
    public User register(User user) {
        
        userValidator.validate(user);
        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User is already registred");
        } else {
            userDao.save(user);
        }
        return user;
    }
}

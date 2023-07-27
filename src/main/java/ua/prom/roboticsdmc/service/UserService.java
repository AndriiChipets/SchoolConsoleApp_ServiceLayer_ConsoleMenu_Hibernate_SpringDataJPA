package ua.prom.roboticsdmc.service;

import ua.prom.roboticsdmc.domain.User;

public interface UserService {

    boolean login(String email, String password);

    User register(User user);

}

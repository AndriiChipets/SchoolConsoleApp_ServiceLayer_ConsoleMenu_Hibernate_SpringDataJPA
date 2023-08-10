package ua.prom.roboticsdmc.service;

import ua.prom.roboticsdmc.domain.UserRegistrationRequest;

public interface UserService {

    boolean login(String email, String password);

    void register(UserRegistrationRequest registrationRequest);

}

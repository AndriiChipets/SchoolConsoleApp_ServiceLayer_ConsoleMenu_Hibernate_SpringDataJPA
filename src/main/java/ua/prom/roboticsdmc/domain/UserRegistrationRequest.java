package ua.prom.roboticsdmc.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
public class UserRegistrationRequest {
    
    private final String email;
    private final String password;
    private final String repeatPassword;
    private final String firstName;
    private final String lastName;

}

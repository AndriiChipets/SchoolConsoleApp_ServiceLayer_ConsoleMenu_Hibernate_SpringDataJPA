package ua.prom.roboticsdmc.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder(setterPrefix = "with")
public class UserRegistrationRequest {

    private final String email;
    private final String password;
    private final String repeatPassword;
    private final String firstName;
    private final String lastName;

}

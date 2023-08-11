package ua.prom.roboticsdmc.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode
@Getter
@SuperBuilder(setterPrefix = "with")
public class UserDto {

    private final int userId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;

    @Override
    public String toString() {
        return "userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email;
    }
}

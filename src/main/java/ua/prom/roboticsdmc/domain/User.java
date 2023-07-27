package ua.prom.roboticsdmc.domain;

import lombok.Getter;

@Getter
public class User {

    private final int userId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String repeatPassword;

    public User(int userId, String firstName, String lastName, String email, String password, String repeatPassword) {
        this.userId = userId;
        this.firstName = trimString(firstName);
        this.lastName = trimString(lastName);
        this.email = trimString(email);
        this.password = trimString(password);
        this.repeatPassword = trimString(repeatPassword);
    }

    private final String trimString(String property) {
        return property != null ? property.trim() : null;
    }

    @Override
    public String toString() {
        return "userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email;
    }
}

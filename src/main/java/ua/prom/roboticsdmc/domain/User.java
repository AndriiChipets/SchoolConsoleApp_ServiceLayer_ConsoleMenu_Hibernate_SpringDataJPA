package ua.prom.roboticsdmc.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class User {

    private final int userId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;

    public User(Builder builder) {
        this.userId = builder.userId;
        this.firstName = trimString(builder.firstName);
        this.lastName = trimString(builder.lastName);
        this.email = trimString(builder.email);
        this.password = trimString(builder.password);
    }

    private final String trimString(String property) {
        return property != null ? property.trim() : null;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email;
    }

    public static class Builder<S extends Builder> {

        private int userId;
        private String firstName;
        private String lastName;
        private String email;
        private String password;

        protected Builder() {
        }

        public S withUserId(int userId) {
            this.userId = userId;
            return (S) this;
        }

        public S withFirstName(String firstName) {
            this.firstName = firstName;
            return (S) this;
        }

        public S withLastName(String lastName) {
            this.lastName = lastName;
            return (S) this;
        }

        public S withEmail(String email) {
            this.email = email;
            return (S) this;
        }

        public S withPassword(String password) {
            this.password = password;
            return (S) this;
        }

        public User build() {
            return new User(this);
        }
    }
}

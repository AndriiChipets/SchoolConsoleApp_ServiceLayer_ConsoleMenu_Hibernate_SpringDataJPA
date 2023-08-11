package ua.prom.roboticsdmc.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode
@Getter
@SuperBuilder(setterPrefix = "with")
public class User {

    private final Integer userId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;

    public abstract static class UserBuilder<C extends User, B extends UserBuilder<C, B>> {

        public B withFirstName(String firstName) {
            this.firstName = trimString(firstName);
            return (B) this;
        }

        public B withLastName(String lastName) {
            this.lastName = trimString(lastName);
            return (B) this;
        }

        public B withEmail(String email) {
            this.email = trimString(email);
            return (B) this;
        }

        public B withPassword(String password) {
            this.password = trimString(password);
            return (B) this;
        }

        private final String trimString(String property) {
            return property != null ? property.trim() : null;
        }
    }
}

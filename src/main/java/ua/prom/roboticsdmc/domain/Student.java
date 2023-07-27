package ua.prom.roboticsdmc.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Student extends User {

    private final int groupId;

    @Builder(setterPrefix = "with")
    public Student(int groupId, int userId, String firstName, String lastName, String email, String password,
            String repeatPassword) {
        super(userId, firstName, lastName, email, password, repeatPassword);
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Student [" + super.toString() + ", groupId=" + groupId + "]";
    }
}

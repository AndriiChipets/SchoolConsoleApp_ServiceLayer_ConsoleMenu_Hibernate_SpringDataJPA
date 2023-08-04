package ua.prom.roboticsdmc.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder (setterPrefix = "with")
public class Student extends User {

    private final int groupId;

}

package ua.prom.roboticsdmc.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Getter
@SuperBuilder(setterPrefix = "with")
public class StudentDto extends UserDto {

    private final int groupId;

    @Override
    public String toString() {
        return "Student [" + super.toString() + ", groupId=" + groupId + "]";
    }
}

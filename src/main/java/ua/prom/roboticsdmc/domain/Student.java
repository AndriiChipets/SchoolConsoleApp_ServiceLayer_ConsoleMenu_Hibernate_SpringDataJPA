package ua.prom.roboticsdmc.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public class Student extends User {

    private final int groupId;

    public Student(Builder builder) {
        super(builder);
        this.groupId = builder.groupId;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "Student [" + super.toString() + ", groupId=" + groupId + "]";
    }

    public static class Builder extends User.Builder<Builder> {

        private int groupId;

        public Builder() {
        }

        public Builder withGroupId(int groupId) {
            this.groupId = groupId;
            return this;
        }

        @Override
        public Student build() {
            return new Student(this);
        }
    }
}

package ua.prom.roboticsdmc.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Course {

    private final int courseId;
    private final String courseName;
    private final String courseDescription;

    public static class CourseBuilder {

        public CourseBuilder withCourseName(String courseName) {
            this.courseName = courseName != null ? courseName.trim() : null;
            return this;
        }
    }
}

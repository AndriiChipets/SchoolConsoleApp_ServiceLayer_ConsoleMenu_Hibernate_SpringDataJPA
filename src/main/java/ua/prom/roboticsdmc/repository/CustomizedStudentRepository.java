package ua.prom.roboticsdmc.repository;

import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Student;

public interface CustomizedStudentRepository {

    void addStudentToGroup(Student student);

    void removeStudentFromCourse(Student student, Course course);

    void addStudentToCourse(Student student, Course course);

}

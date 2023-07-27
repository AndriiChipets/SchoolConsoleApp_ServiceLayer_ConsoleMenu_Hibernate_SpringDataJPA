package ua.prom.roboticsdmc.service;

import java.util.List;

import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Group;
import ua.prom.roboticsdmc.domain.Student;

public interface StudentService {

    List<Group> findAllGroupsWithLessOrEqualsStudentCount(Integer studentQuantity);

    List<Student> findAllStudentsRelatedToCourseWithGivenName(String courseName);

    void addNewStudent(Student student);

    void deleteStudentByStudent_Id(Integer studentId);

    void addStudentToCourse(Integer studentId, Integer courseId);

    void removeStudentFromOneOfTheirCourses(Integer studentId, Integer courseId);

    List<Course> findAllStudentsCources();

    List<Course> findAllStudentCoursesByStudentId(Integer studentId);

}

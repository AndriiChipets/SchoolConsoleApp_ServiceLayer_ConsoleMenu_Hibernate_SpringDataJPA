package ua.prom.roboticsdmc.service;

import java.util.List;

import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Group;
import ua.prom.roboticsdmc.domain.Student;

public interface DataGenerator {

    List<Student> createRandomStudent(int studentsNumber);

    List<Group> createRandomGroup(int groupNumber);

    List<Course> createCourse();

    List<Student> assignStudentToGroup(List<Group> groups, List<Student> studentsint, int minStudentInGroup,
            int maxStudentInGroup);

    List<List<Integer>> assignStudentToCourses(List<Student> students, List<Course> courses, int minStudentCourses,
            int maxStudentCourses);

}

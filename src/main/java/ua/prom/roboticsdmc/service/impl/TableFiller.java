package ua.prom.roboticsdmc.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Group;
import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.repository.CourseRepository;
import ua.prom.roboticsdmc.repository.GroupRepository;
import ua.prom.roboticsdmc.repository.StudentRepository;
import ua.prom.roboticsdmc.service.DataGenerator;

@Component
@AllArgsConstructor
@Log4j2
public class TableFiller {

    private final DataGenerator dataGenerator;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    private static final int TOTAL_STUDENT_NUMBER = 200;
    private static final int TOTAL_GROUP_NUMBER = 10;
    private static final int MIN_NUMBER_STUDENTS_IN_GROUP = 10;
    private static final int MAX_NUMBER_STUDENTS_IN_GROUP = 30;
    private static final int MIN_NUMBER_COURCES_FOR_STUDENT = 1;
    private static final int MAX_NUMBER_COURCES_FOR_STUDENT = 3;

    protected void fillData() {
        log.info("Fill data to the tables");
        List<Student> students = dataGenerator.createRandomStudent(TOTAL_STUDENT_NUMBER);
        List<Course> courses = dataGenerator.createCourse();
        List<Group> groups = dataGenerator.createRandomGroup(TOTAL_GROUP_NUMBER);
        groupRepository.saveAll(groups);
        courseRepository.saveAll(courses);
        studentRepository.saveAll(students);
        groups = new ArrayList<>(groupRepository.findAll());
        students = new ArrayList<>(studentRepository.findAll());
        students = dataGenerator.assignStudentToGroup(groups, students, MIN_NUMBER_STUDENTS_IN_GROUP,
                MAX_NUMBER_STUDENTS_IN_GROUP);
        studentRepository.saveAll(students);
        students = studentRepository.findAll();
        courses = courseRepository.findAll();
        List<Student> studentsAssignedToCourses = dataGenerator.assignStudentToCourses(students, courses,
                MIN_NUMBER_COURCES_FOR_STUDENT, MAX_NUMBER_COURCES_FOR_STUDENT);
        studentRepository.saveAll(studentsAssignedToCourses);
        log.info("Data filled to the tables");
    }
}

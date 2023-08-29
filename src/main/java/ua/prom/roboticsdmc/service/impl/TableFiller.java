package ua.prom.roboticsdmc.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.dao.CourseDao;
import ua.prom.roboticsdmc.dao.GroupDao;
import ua.prom.roboticsdmc.dao.StudentDao;
import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Group;
import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.service.DataGenerator;

@Component
@AllArgsConstructor
@Log4j2
public class TableFiller {

    private final DataGenerator dataGenerator;
    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final GroupDao groupDao;
    private static final int TOTAL_STUDENT_NUMBER = 200;
    private static final int TOTAL_GROUP_NUMBER = 10;
    private static final int MIN_NUMBER_STUDENTS_IN_GROUP = 10;
    private static final int MAX_NUMBER_STUDENTS_IN_GROUP = 30;
    private static final int MIN_NUMBER_COURCES_FOR_STUDENT = 1;
    private static final int MAX_NUMBER_COURCES_FOR_STUDENT = 2;

    protected void fillData() {
        log.trace("Fill data to the tables");
        List<Student> students = dataGenerator.createRandomStudent(TOTAL_STUDENT_NUMBER);
        List<Course> courses = dataGenerator.createCourse();
        List<Group> groups = dataGenerator.createRandomGroup(TOTAL_GROUP_NUMBER);
        studentDao.saveAll(students);
        courseDao.saveAll(courses);
        groupDao.saveAll(groups);
        groups = new ArrayList<>(groupDao.findAll());
        students = new ArrayList<>(studentDao.findAll());
        students = dataGenerator.assignStudentToGroup(groups, students, MIN_NUMBER_STUDENTS_IN_GROUP,
                MAX_NUMBER_STUDENTS_IN_GROUP);
        studentDao.distributeStudentsToGroups(students);
        students = studentDao.findAll();
        courses = courseDao.findAll();
        List<List<Integer>> studentsAssignedToCourses = dataGenerator.assignStudentToCourses(students, courses,
                MIN_NUMBER_COURCES_FOR_STUDENT, MAX_NUMBER_COURCES_FOR_STUDENT);
        courseDao.fillRandomStudentCourseTable(studentsAssignedToCourses);
        log.trace("Data filled to the tables");
    }
}

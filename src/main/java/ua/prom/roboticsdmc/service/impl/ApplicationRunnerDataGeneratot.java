package ua.prom.roboticsdmc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import ua.prom.roboticsdmc.dao.CourseDao;
import ua.prom.roboticsdmc.dao.GroupDao;
import ua.prom.roboticsdmc.dao.StudentDao;
import ua.prom.roboticsdmc.dao.UserDao;
import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Group;
import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.service.DataGenerator;

@ConditionalOnExpression("${userDao.isAnyTableInDbSchema():true}")
@Profile("!test")
@Component
@Order(2)
@AllArgsConstructor
public class ApplicationRunnerDataGeneratot implements ApplicationRunner {

    private final DataGenerator dataGenerator;
    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final GroupDao groupDao;
    private final UserDao userDao;
    private static final int TOTAL_STUDENT_NUMBER = 200;
    private static final int TOTAL_GROUP_NUMBER = 10;
    private static final int MIN_NUMBER_STUDENTS_IN_GROUP = 10;
    private static final int MAX_NUMBER_STUDENTS_IN_GROUP = 30;
    private static final int MIN_NUMBER_COURCES_FOR_STUDENT = 1;
    private static final int MAX_NUMBER_COURCES_FOR_STUDENT = 2;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (userDao.isAnyTableInDbSchema()) {
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
        }
    }
}

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
    private static final int MAX_NUMBER_COURCES_FOR_STUDENT = 3;

    protected void fillData() {
        log.info("Fill data to the tables");
        List<Student> students = dataGenerator.createRandomStudent(TOTAL_STUDENT_NUMBER);
        List<Course> courses = dataGenerator.createCourse();
        List<Group> groups = dataGenerator.createRandomGroup(TOTAL_GROUP_NUMBER);
        log.info("Save all Groups to the table");
        groupDao.saveAll(groups);
        log.info("All Groups saved to the table");
        log.info("Save all Courses to the table");
        courseDao.saveAll(courses);
        log.info("All Courses saved to the table");
        log.info("Save all Students to the table");
        studentDao.saveAll(students);
        log.info("All Students saved to the table");
        log.info("Get all Groups from the table");
        groups = new ArrayList<>(groupDao.findAll());
        log.info("All Groups got from the table");
        log.info("Get all Students from the table");
        students = new ArrayList<>(studentDao.findAll());
        log.info("All Students got from the table");
        log.info("Assign Students to the Groups");
        students = dataGenerator.assignStudentToGroup(groups, students, MIN_NUMBER_STUDENTS_IN_GROUP,
                MAX_NUMBER_STUDENTS_IN_GROUP);
        studentDao.distributeStudentsToGroups(students);
        log.info("Students assigned to the Groups");
        log.info("Get all Students from the table");
        students = studentDao.findAll();
        log.info("All Students got from the table");
        log.info("Get all Courses from the table");
        courses = courseDao.findAll();
        log.info("All Courses got from the table");
        log.info("Assign Students to the Courses");
        List<Student> studentsAssignedToCourses = dataGenerator.assignStudentToCourses(students, courses,
                MIN_NUMBER_COURCES_FOR_STUDENT, MAX_NUMBER_COURCES_FOR_STUDENT);
        studentDao.fillRandomStudentCourseTable(studentsAssignedToCourses);
        log.info("Students assigned to the Courses");
        log.info("Data filled to the tables");
    }
}

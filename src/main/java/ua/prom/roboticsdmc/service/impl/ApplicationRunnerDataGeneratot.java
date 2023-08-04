package ua.prom.roboticsdmc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import ua.prom.roboticsdmc.dao.CourseDao;
import ua.prom.roboticsdmc.dao.GroupDao;
import ua.prom.roboticsdmc.dao.StudentDao;
import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Group;
import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.service.DataGenerator;

@Component
@Order(2)
@AllArgsConstructor
public class ApplicationRunnerDataGeneratot implements ApplicationRunner {

    private final DataGenerator dataGenerator;
    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final GroupDao groupDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<Student> students = dataGenerator.createRandomStudent();
        List<Course> courses = dataGenerator.createCourse();
        List<Group> groups = dataGenerator.createRandomGroup();
        studentDao.saveAll(students);
        courseDao.saveAll(courses);
        groupDao.saveAll(groups);
        groups = new ArrayList<>(groupDao.findAll());
        students = new ArrayList<>(studentDao.findAll());
        students = dataGenerator.assignStudentToGroup(groups, students);
        studentDao.distributeStudentsToGroups(students);
        students = studentDao.findAll();
        courses = courseDao.findAll();
        List<List<Integer>> studentsAssignedToCourses = dataGenerator.assignStudentToCourses(students, courses);
        courseDao.fillRandomStudentCourseTable(studentsAssignedToCourses);
    }
}

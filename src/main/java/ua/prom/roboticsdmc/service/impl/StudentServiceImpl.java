package ua.prom.roboticsdmc.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.prom.roboticsdmc.dao.CourseDao;
import ua.prom.roboticsdmc.dao.GroupDao;
import ua.prom.roboticsdmc.dao.StudentDao;

import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Group;
import ua.prom.roboticsdmc.domain.Student;

import ua.prom.roboticsdmc.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;
    private final GroupDao groupDao;
    private final CourseDao courseDao;

    public StudentServiceImpl(StudentDao studentDao, GroupDao groupDao, CourseDao courseDao) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
        this.courseDao = courseDao;
    }

    @Override
    public List<Group> findAllGroupsWithLessOrEqualsStudentCount(Integer studentQuantity) {
        return groupDao.findGroupWithLessOrEqualsStudentQuantity(studentQuantity);
    }

    @Override
    public List<Student> findAllStudentsRelatedToCourseWithGivenName(String courseName) {
        return studentDao.findStudentsByCourseName(courseName);
    }

    @Override
    public void addNewStudent(Student student) {
        studentDao.save(student);
    }

    @Override
    public void deleteStudentByStudent_Id(Integer studentId) {
        studentDao.deleteById(studentId);
    }

    @Override
    public void addStudentToCourse(Integer studentId, Integer courseId) {
        courseDao.addStudentToCourse(studentId, courseId);
    }

    @Override
    public void removeStudentFromOneOfTheirCourses(Integer studentId, Integer courseId) {
        courseDao.removeStudentFromCourse(studentId, courseId);
    }

    @Override
    public List<Course> findAllStudentsCources() {
        return courseDao.findAll();
    }

    @Override
    public List<Course> findAllStudentCoursesByStudentId(Integer studentId) {
        return courseDao.getAllStudentCoursesByStudentID(studentId);
    }
}

package ua.prom.roboticsdmc.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ua.prom.roboticsdmc.dao.CourseDao;
import ua.prom.roboticsdmc.dao.GroupDao;
import ua.prom.roboticsdmc.dao.StudentDao;
import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Group;
import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.dto.CourseDto;
import ua.prom.roboticsdmc.dto.GroupDto;
import ua.prom.roboticsdmc.dto.StudentDto;
import ua.prom.roboticsdmc.mapper.CourseMapper;
import ua.prom.roboticsdmc.mapper.GroupMapper;
import ua.prom.roboticsdmc.mapper.StudentMapper;
import ua.prom.roboticsdmc.service.StudentService;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;
    private final StudentMapper studentMapper;
    private final GroupDao groupDao;
    private final GroupMapper groupMapper;
    private final CourseDao courseDao;
    private final CourseMapper courseMapper;

    @Override
    public List<GroupDto> findAllGroupsWithLessOrEqualsStudentCount(Integer studentQuantity) {
        List<Group> groups = groupDao.findGroupWithLessOrEqualsStudentQuantity(studentQuantity);
        return groups.stream().map(groupMapper::mapEntityToDomain).toList();
    }

    @Override
    public List<StudentDto> findAllStudentsRelatedToCourseWithGivenName(String courseName) {
        List<Student> students = studentDao.findStudentsByCourseName(courseName);
        return students.stream().map(studentMapper::mapEntityToDomain).toList();
    }

    @Override
    public void deleteUserByUser_Id(Integer studentId) {
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
    public List<CourseDto> findAllStudentsCources() {
        List<Course> courses = courseDao.findAll();
        return courses.stream().map(courseMapper::mapEntityToDomain).toList();
    }

    @Override
    public List<CourseDto> findAllStudentCoursesByStudentId(Integer studentId) {
        List<Course> courses = courseDao.getAllStudentCoursesByStudentID(studentId);
        return courses.stream().map(courseMapper::mapEntityToDomain).toList();
    }

    @Override
    public void addStudentToGroup(Integer groupId, Integer studentId) {
        studentDao.addStudentToGroup(groupId, studentId);
    }
}

package ua.prom.roboticsdmc.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.dao.CourseDao;
import ua.prom.roboticsdmc.dao.GroupDao;
import ua.prom.roboticsdmc.dao.StudentDao;
import ua.prom.roboticsdmc.dao.UserDao;
import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Group;
import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.dto.CourseDto;
import ua.prom.roboticsdmc.dto.GroupDto;
import ua.prom.roboticsdmc.dto.StudentDto;
import ua.prom.roboticsdmc.mapper.CourseMapperStruct;
import ua.prom.roboticsdmc.mapper.GroupMapperStruct;
import ua.prom.roboticsdmc.mapper.StudentMapperStruct;
import ua.prom.roboticsdmc.service.StudentService;

@Service
@AllArgsConstructor
@Log4j2
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;
    private final GroupDao groupDao;
    private final CourseDao courseDao;
    private final UserDao userDao;
    
    @Autowired
    GroupMapperStruct gropMapperStruct = GroupMapperStruct.INSTANCE;
    @Autowired
    StudentMapperStruct studentMapperStruct = StudentMapperStruct.INSTANCE;
    @Autowired
    CourseMapperStruct courseMapperStruct = CourseMapperStruct.INSTANCE;

    @Override
    public List<GroupDto> findAllGroupsWithLessOrEqualsStudentCount(Integer studentQuantity) {
        log.info("Method start");
        List<Group> groups = groupDao.findGroupWithLessOrEqualsStudentQuantity(studentQuantity);
        log.info("Method end");
        return groups.stream().map(gropMapperStruct::mapGroupToGroupDto).toList();
    }

    @Override
    public List<StudentDto> findAllStudentsRelatedToCourseWithGivenName(String courseName) {
        log.info("Method start");
        List<Student> courseStudents = null;
        Optional <Course> course = courseDao.findCourseByCourseName(courseName);
        if (!course.isEmpty()) {
        courseStudents = new ArrayList<>(course.get().getStudents());
        } else {
            log.info("Course with course name " + courseName + " is absent in the table");
            throw new IllegalArgumentException("Course with course name " + courseName + " is absent in the table");
        }
        log.info("Method end");
        return courseStudents.stream()
                .map(studentMapperStruct::mapStudentToStudentDto)
                .sorted(Comparator.comparingInt(StudentDto::getUserId))
                .toList();
    }

    @Override
    public void deleteUserByUser_Id(Integer studentId) {
        log.info("Method start");
        userDao.deleteById(studentId);
        log.info("Method end");
    }

    @Override
    public void addStudentToCourse(Integer studentId, Integer courseId) {
        log.info("Method start");
        Optional<Course> courseOptional = courseDao.findById(courseId);
        if (courseOptional.isEmpty()) {
            log.warn("Course with ID = " + courseId + " is absent in the table");
            throw new IllegalArgumentException("Course with ID = " + courseId + " is absent in the table");
        }
        Course course = courseOptional.get();
        Optional <Student> studentOptional = studentDao.findById(studentId);
        if (studentOptional.isEmpty()) {
            log.warn("Student with ID = " + studentId + " is absent in the table");
            throw new IllegalArgumentException("Student with ID = " + studentId + " is absent in the table");
        }
        Student student = studentOptional.get();
        Set<Course> studentCourses = new HashSet<>(student.getCourses());
        studentCourses.add(course);
        Student studentAddedToCourse = Student.builder()
                .withUserId(student.getUserId())
                .withFirstName(student.getFirstName())
                .withLastName(student.getLastName())
                .withEmail(student.getEmail())
                .withPassword(student.getPassword())
                .withGroupId(student.getGroupId())
                .withCourses(studentCourses)
                .build();
        studentDao.update(studentAddedToCourse);
        log.info("Method end");
    }

    @Override
    public void removeStudentFromOneOfTheirCourses(Integer studentId, Integer courseId) {
        log.info("Method start");
        Optional<Course> courseOptional = courseDao.findById(courseId);
        if (courseOptional.isEmpty()) {
            log.warn("Course with ID = " + courseId + " is absent in the table");
            throw new IllegalArgumentException("Course with ID = " + courseId + " is absent in the table");
        }
        Course course = courseOptional.get();
        Optional <Student> studentOptional = studentDao.findById(studentId);
        if (studentOptional.isEmpty()) {
            log.warn("Student with ID = " + studentId + " is absent in the table");
            throw new IllegalArgumentException("Student with ID = " + studentId + " is absent in the table");
        }
        Student student = studentOptional.get();
        Set<Course> studentCourses = new HashSet<>(student.getCourses());
        studentCourses.remove(course);
        Student studentAddedToCourse = Student.builder()
                .withUserId(student.getUserId())
                .withFirstName(student.getFirstName())
                .withLastName(student.getLastName())
                .withEmail(student.getEmail())
                .withPassword(student.getPassword())
                .withGroupId(student.getGroupId())
                .withCourses(studentCourses)
                .build();
        studentDao.update(studentAddedToCourse);
        log.info("Method end");
    }

    @Override
    public List<CourseDto> findAllStudentsCources() {
        log.info("Method start");
        List<Course> courses = courseDao.findAll();
        log.info("Method end");
        return courses.stream()
                .map(courseMapperStruct::mapCourseToCourseDto)
                .sorted(Comparator.comparingInt(CourseDto::getCourseId))
                .toList();
    }

    @Override
    public List<CourseDto> findAllStudentCoursesByStudentId(Integer studentId) {
        List<Course> studentCourses = null;
        log.info("Method start");
        Student student = studentDao.findById(studentId).get();
        if (student != null) {
            studentCourses = new ArrayList<>(student.getCourses());
        } else {
            log.warn("Student with ID = " + studentId + " is absent in the table");
            throw new IllegalArgumentException("Student is absent in the table");
        }
        log.info("Method end");
        return studentCourses.stream()
                .map(courseMapperStruct::mapCourseToCourseDto)
                .sorted(Comparator.comparingInt(CourseDto::getCourseId))
                .toList();
    }

    @Override
    public void addStudentToGroup(Integer groupId, Integer studentId) {
        log.info("Method start");
        studentDao.addStudentToGroup(groupId, studentId);
        log.info("Method end");
    }
}

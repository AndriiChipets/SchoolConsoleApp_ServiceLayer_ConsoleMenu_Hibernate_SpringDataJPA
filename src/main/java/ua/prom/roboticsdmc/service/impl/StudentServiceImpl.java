package ua.prom.roboticsdmc.service.impl;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.dto.CourseDto;
import ua.prom.roboticsdmc.dto.GroupDto;
import ua.prom.roboticsdmc.dto.StudentDto;
import ua.prom.roboticsdmc.mapper.CourseMapperStruct;
import ua.prom.roboticsdmc.mapper.GroupMapperStruct;
import ua.prom.roboticsdmc.mapper.StudentMapperStruct;
import ua.prom.roboticsdmc.repository.CourseRepository;
import ua.prom.roboticsdmc.repository.GroupRepository;
import ua.prom.roboticsdmc.repository.StudentRepository;
import ua.prom.roboticsdmc.repository.UserRepository;
import ua.prom.roboticsdmc.service.StudentService;

@Service
@AllArgsConstructor
@Log4j2
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    
    @Autowired
    GroupMapperStruct groupMapperStruct = GroupMapperStruct.INSTANCE;
    @Autowired
    StudentMapperStruct studentMapperStruct = StudentMapperStruct.INSTANCE;
    @Autowired
    CourseMapperStruct courseMapperStruct = CourseMapperStruct.INSTANCE;

    @Override
    public List<GroupDto> findAllGroupsWithLessOrEqualsStudentCount(Integer studentQuantity) {
        log.info("Find all Groups with less or equals Student count = " + studentQuantity);
        return groupRepository.findGroupWithLessOrEqualsStudentQuantity(studentQuantity)
                .stream()
                .map(groupMapperStruct::mapGroupToGroupDto)
                .toList();
    }

    @Override
    public List<StudentDto> findAllStudentsRelatedToCourseWithGivenName(String courseName) {
        return courseRepository.findCourseByCourseName(courseName).get()
                .getStudents()
                .stream().map(studentMapperStruct::mapStudentToStudentDto)
                .sorted(Comparator.comparingInt(StudentDto::getUserId))
                .toList();
    }

    @Override
    public void deleteUserById(Integer usertId) {
        log.info("Delete User with user ID = " + usertId);
        userRepository.deleteById(usertId);
        log.info("User with user ID = " + usertId + "deleted");
    }

    @Override
    public void addStudentToCourse(Integer usertId, Integer courseId) {
        Student student = studentRepository.findById(usertId).get();
        Course course = courseRepository.findById(courseId).get();
        studentRepository.addStudentToCourse(student, course);
    }

    @Override
    public void removeStudentFromOneOfTheirCourses(Integer studentId, Integer courseId) {
        Student student = studentRepository.findById(studentId).get();
        Course course = courseRepository.findById(courseId).get();
        studentRepository.removeStudentFromCourse(student, course);  
    }

    @Override
    public List<CourseDto> findAllCources() {
        log.info("Find all Courses");
        return courseRepository.findAll()
                .stream()
                .map(courseMapperStruct::mapCourseToCourseDto)
                .sorted(Comparator.comparingInt(CourseDto::getCourseId))
                .toList();
    }

    @Override
    public List<CourseDto> findAllStudentCoursesByStudentId(Integer studentId) {
        return studentRepository.findById(studentId).get()
                .getCourses()
                .stream()
                .map(courseMapperStruct::mapCourseToCourseDto)
                .sorted(Comparator.comparingInt(CourseDto::getCourseId))
                .toList();
    }

    @Override
    public void addStudentToGroup(Integer userId, Integer groupId) {
        log.info("Add Student with Student ID = " + userId + " to Group with Group ID = " + groupId);
        Student student = Student.builder().withUserId(userId).withGroupId(groupId).build();
        studentRepository.addStudentToGroup(student);
        log.info("Student added to Group");
    }
    
}

package ua.prom.roboticsdmc.service.impl;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.dao.CourseDao;
import ua.prom.roboticsdmc.dao.GroupDao;
import ua.prom.roboticsdmc.dao.StudentDao;
import ua.prom.roboticsdmc.dao.UserDao;
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
    GroupMapperStruct groupMapperStruct = GroupMapperStruct.INSTANCE;
    @Autowired
    StudentMapperStruct studentMapperStruct = StudentMapperStruct.INSTANCE;
    @Autowired
    CourseMapperStruct courseMapperStruct = CourseMapperStruct.INSTANCE;

    @Override
    public List<GroupDto> findAllGroupsWithLessOrEqualsStudentCount(Integer studentQuantity) {
        log.info("Find all Groups with less or equals Student count = " + studentQuantity);
        return groupDao.findGroupWithLessOrEqualsStudentQuantity(studentQuantity)
                .stream()
                .map(groupMapperStruct::mapGroupToGroupDto)
                .toList();
    }

    @Override
    public List<StudentDto> findAllStudentsRelatedToCourseWithGivenName(String courseName) {
        return courseDao.findStudentsByCourseName(courseName)
                .stream().map(studentMapperStruct::mapStudentToStudentDto)
                .sorted(Comparator.comparingInt(StudentDto::getUserId))
                .toList();
    }

    @Override
    public void deleteUserByUser_Id(Integer studentId) {
        log.info("Delete User with user ID = " + studentId);
        userDao.deleteById(studentId);
        log.info("User with user ID = " + studentId + "deleted");
    }

    @Override
    public void addStudentToCourse(Integer studentId, Integer courseId) {
        studentDao.addStudentToCourse(studentId, courseId);
    }

    @Override
    public void removeStudentFromOneOfTheirCourses(Integer studentId, Integer courseId) {
        studentDao.removeStudentFromCourse(studentId, courseId);  
    }

    @Override
    public List<CourseDto> findAllCources() {
        log.info("Find all Courses");
        return courseDao.findAll()
                .stream()
                .map(courseMapperStruct::mapCourseToCourseDto)
                .sorted(Comparator.comparingInt(CourseDto::getCourseId))
                .toList();
    }

    @Override
    public List<CourseDto> findAllStudentCoursesByStudentId(Integer studentId) {
        return studentDao.getAllStudentCoursesByStudentID(studentId)
                .stream()
                .map(courseMapperStruct::mapCourseToCourseDto)
                .sorted(Comparator.comparingInt(CourseDto::getCourseId))
                .toList();
    }

    @Override
    public void addStudentToGroup(Integer groupId, Integer studentId) {
        log.info("Add Student with Student ID = " + studentId + "to Group with Group ID = " + groupId);
        studentDao.addStudentToGroup(groupId, studentId);
        log.info("Student added to Group");
    }
}

package ua.prom.roboticsdmc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import ua.prom.roboticsdmc.mapper.CourseMapperStruct;
import ua.prom.roboticsdmc.mapper.GroupMapperStruct;
import ua.prom.roboticsdmc.mapper.StudentMapperStruct;
import ua.prom.roboticsdmc.service.StudentService;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;
    private final GroupDao groupDao;
    private final CourseDao courseDao;
    
    @Autowired
    GroupMapperStruct gropMapperStruct = GroupMapperStruct.INSTANCE;
    @Autowired
    StudentMapperStruct studentMapperStruct = StudentMapperStruct.INSTANCE;
    @Autowired
    CourseMapperStruct courseMapperStruct = CourseMapperStruct.INSTANCE;

    @Override
    public List<GroupDto> findAllGroupsWithLessOrEqualsStudentCount(Integer studentQuantity) {
        List<Group> groups = groupDao.findGroupWithLessOrEqualsStudentQuantity(studentQuantity);
        return groups.stream().map(gropMapperStruct::mapGroupToGroupDto).toList();
    }

    @Override
    public List<StudentDto> findAllStudentsRelatedToCourseWithGivenName(String courseName) {
        List<Student> students = studentDao.findStudentsByCourseName(courseName);
        return students.stream().map(studentMapperStruct::mapStudentToStudentDto).toList();
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
        return courses.stream().map(courseMapperStruct::mapCourseToCourseDto).toList();
    }

    @Override
    public List<CourseDto> findAllStudentCoursesByStudentId(Integer studentId) {
        List<Course> courses = courseDao.getAllStudentCoursesByStudentID(studentId);
        return courses.stream().map(courseMapperStruct::mapCourseToCourseDto).toList();
    }

    @Override
    public void addStudentToGroup(Integer groupId, Integer studentId) {
        studentDao.addStudentToGroup(groupId, studentId);
    }
}

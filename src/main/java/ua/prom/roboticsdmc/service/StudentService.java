package ua.prom.roboticsdmc.service;

import java.util.List;

import ua.prom.roboticsdmc.dto.CourseDto;
import ua.prom.roboticsdmc.dto.GroupDto;
import ua.prom.roboticsdmc.dto.StudentDto;

public interface StudentService {

    List<GroupDto> findAllGroupsWithLessOrEqualsStudentCount(Integer studentQuantity);

    List<StudentDto> findAllStudentsRelatedToCourseWithGivenName(String courseName);

    void deleteUserById(Integer userId);

    void addStudentToCourse(Integer userId, Integer courseId);

    void removeStudentFromOneOfTheirCourses(Integer userId, Integer courseId);

    List<CourseDto> findAllCources();

    List<CourseDto> findAllStudentCoursesByStudentId(Integer userId);

    void addStudentToGroup(Integer userId, Integer groupId);

}

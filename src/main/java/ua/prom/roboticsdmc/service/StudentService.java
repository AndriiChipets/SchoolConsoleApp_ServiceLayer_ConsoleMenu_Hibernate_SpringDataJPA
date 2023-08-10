package ua.prom.roboticsdmc.service;

import java.util.List;

import ua.prom.roboticsdmc.dto.CourseDto;
import ua.prom.roboticsdmc.dto.GroupDto;
import ua.prom.roboticsdmc.dto.StudentDto;

public interface StudentService {

    List<GroupDto> findAllGroupsWithLessOrEqualsStudentCount(Integer studentQuantity);

    List<StudentDto> findAllStudentsRelatedToCourseWithGivenName(String courseName);

    void deleteUserByUser_Id(Integer studentId);

    void addStudentToCourse(Integer studentId, Integer courseId);

    void removeStudentFromOneOfTheirCourses(Integer studentId, Integer courseId);

    List<CourseDto> findAllStudentsCources();

    List<CourseDto> findAllStudentCoursesByStudentId(Integer studentId);

    void addStudentToGroup(Integer groupId, Integer studentId);

}

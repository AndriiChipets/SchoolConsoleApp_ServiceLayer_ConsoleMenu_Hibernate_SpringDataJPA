package ua.prom.roboticsdmc.dao;

import java.util.List;
import java.util.Set;

import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Student;

public interface StudentDao extends CrudDao<Integer, Student> {

    void distributeStudentsToGroups(List<Student> students);

    void addStudentToGroup(Integer studentId, Integer groupId);

    void fillRandomStudentCourseTable(List<Student> studentsAssignedToCourses);
    
    void removeStudentFromCourse(Integer studentId, Integer courseId);
    
    void addStudentToCourse(Integer studentId, Integer courseId);
    
    Set<Course> getAllStudentCoursesByStudentID(Integer studentId);
}

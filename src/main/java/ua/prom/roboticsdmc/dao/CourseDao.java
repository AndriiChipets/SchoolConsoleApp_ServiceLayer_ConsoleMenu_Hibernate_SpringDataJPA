package ua.prom.roboticsdmc.dao;

import java.util.Optional;
import java.util.Set;

import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Student;

public interface CourseDao extends CrudDao<Integer, Course> {

    Optional<Course> findCourseByCourseName(String courseName);
    
    Set<Student> findStudentsByCourseName(String courseName);

}

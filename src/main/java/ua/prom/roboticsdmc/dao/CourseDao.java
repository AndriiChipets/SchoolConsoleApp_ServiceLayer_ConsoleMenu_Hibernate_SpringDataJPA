package ua.prom.roboticsdmc.dao;

import java.util.Optional;

import ua.prom.roboticsdmc.domain.Course;

public interface CourseDao extends CrudDao<Integer, Course> {

    Optional<Course> findCourseByCourseName(String courseName);

}

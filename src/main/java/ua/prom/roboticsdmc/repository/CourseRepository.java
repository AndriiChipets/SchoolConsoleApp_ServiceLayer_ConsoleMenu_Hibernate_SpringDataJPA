package ua.prom.roboticsdmc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.prom.roboticsdmc.domain.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    Optional<Course> findCourseByCourseName(String courseName);

}

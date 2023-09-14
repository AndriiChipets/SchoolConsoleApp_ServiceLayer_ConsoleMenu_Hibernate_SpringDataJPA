package ua.prom.roboticsdmc.dao.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.dao.CourseDao;
import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Student;

@Repository
@Log4j2
public class CourseDaoImpl extends AbstractCrudDaoImpl<Integer, Course> implements CourseDao {

    private static final String FIND_BY_ID_QUERY_HQL = "SELECT c FROM Course c WHERE c.courseId=:id";
    private static final String FIND_BY_COURSE_NAME_QUERY_HQL = "SELECT c FROM Course c WHERE c.courseName=:name";
    private static final String FIND_ALL_QUERY_HQL = "SELECT c FROM Course c ORDER BY c.courseId ASC";
    private static final String DELETE_BY_ID_QUERY_HQL = "DELETE FROM Course c WHERE c.courseId=:id";

    @PersistenceContext
    private EntityManager entityManager;

    public CourseDaoImpl() {
        super(FIND_BY_ID_QUERY_HQL, FIND_ALL_QUERY_HQL, DELETE_BY_ID_QUERY_HQL);
    }

    @Override
    public Optional<Course> findCourseByCourseName(String courseName) {
        log.info("Find course by courseName = " + courseName);
        Course course = null;
        try {
            course = entityManager.createQuery(FIND_BY_COURSE_NAME_QUERY_HQL, Course.class)
                    .setParameter("name", courseName).getSingleResult();
        } catch (NoResultException e) {
            log.warn("Entity is absent in the table");
            return Optional.empty();
        }
        log.info("Return entity by courseName = " + courseName);
        return Optional.of(course);
    }
    
    @Override
    public Set<Student> findStudentsByCourseName(String courseName) {
        log.trace("Find student by course name = " + courseName);
        return findCourseByCourseName(courseName).get().getStudents();
    }
}

package ua.prom.roboticsdmc.repository;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Student;

@Component
@Log4j2
public class CustomizedStudentRepositoryImpl implements CustomizedStudentRepository {

    private static final String DISTRIBUTE_STUDENT_TO_GROUP_QUERY_HQL = "UPDATE Student s SET s.groupId=:groupId WHERE s.userId=:userId";
    private static final String CONVERT_USER_TO_STUDENT_SQL = "UPDATE school_app_schema.users SET dtype=? WHERE user_id=?";
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addStudentToGroup(Student student) {
        Integer userId = student.getUserId();
        Integer groupId = student.getGroupId();
        
        log.info("Add student with ID = " + userId + " to group with ID = " + groupId);
        convertUserToStudent(userId);
        entityManager.createQuery(DISTRIBUTE_STUDENT_TO_GROUP_QUERY_HQL)
        .setParameter("groupId", groupId)
        .setParameter("userId", userId)
        .executeUpdate();
        log.info("Student with ID = " + userId + " added to group with ID = " + groupId);
    }
    
    @Override
    @Transactional
    public void removeStudentFromCourse(Student student, Course course) {
        
        Integer userId = student.getUserId();
        Integer courseId = course.getCourseId();
        
        log.info("Delete Student with ID = " + userId + " to Course with ID = " + courseId);
        student.removeCourse(course);
        entityManager.merge(student);
        log.info("Student with ID = " + userId + " deleted to Course with ID = " + courseId);
    }
    
    @Override
    @Transactional
    public void addStudentToCourse(Student student, Course course) {
        
        Integer userId = student.getUserId();
        Integer courseId = course.getCourseId();
        
        log.info("Add Student with ID = " + userId + " to Course with ID = " + courseId);
        student.addCourse(course);
        entityManager.merge(student);
        log.info("Student with ID = " + userId + " added to Course with ID = " + courseId);
    }
    
    private void convertUserToStudent(Integer userId) {
        log.info("Convert User to Student");
        entityManager.createNativeQuery(CONVERT_USER_TO_STUDENT_SQL)
        .setParameter(1, "Student")
        .setParameter(2, userId)
        .executeUpdate();
        log.info("User converted to Student");
    }
}

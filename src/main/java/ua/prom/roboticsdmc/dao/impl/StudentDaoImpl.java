package ua.prom.roboticsdmc.dao.impl;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.dao.StudentDao;
import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Student;

@Repository
@Log4j2
public class StudentDaoImpl extends AbstractCrudDaoImpl<Integer, Student> implements StudentDao {

    private static final String FIND_BY_ID_QUERY_HQL = "SELECT s FROM Student s WHERE s.userId=:id";
    private static final String FIND_ALL_QUERY_HQL = "SELECT s FROM Student s ORDER BY s.userId ASC";
    private static final String DELETE_BY_ID_QUERY_HQL = "DELETE FROM Student s WHERE s.userId=:id";
    private static final String DISTRIBUTE_STUDENT_TO_GROUP_QUERY_HQL = "UPDATE Student s SET s.groupId=:groupId WHERE s.userId=:id";
    private static final String CONVERT_USER_TO_STUDENT_SQL = "UPDATE school_app_schema.users SET dtype=? WHERE user_id=?";
    private static final String FIND_COURSE_BY_ID_QUERY_HQL = "SELECT c FROM Course c WHERE c.courseId=:id";

    @PersistenceContext
    private EntityManager entityManager;

    public StudentDaoImpl() {
        super(FIND_BY_ID_QUERY_HQL, FIND_ALL_QUERY_HQL, DELETE_BY_ID_QUERY_HQL);
    }

    @Override
    @Transactional
    public void distributeStudentsToGroups(List<Student> students) {
        log.info("Distribute Students to Groups");
        int batchSize = students.size() - 1;
        for (int i = 0; i < students.size(); i++) {
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
            entityManager.createQuery(DISTRIBUTE_STUDENT_TO_GROUP_QUERY_HQL)
                    .setParameter("groupId", students.get(i).getGroupId())
                    .setParameter("id", students.get(i).getUserId())
                    .executeUpdate();
        }
        log.info("Students distributed to Groups");
    }

    @Override
    @Transactional
    public void addStudentToGroup(Integer groupId, Integer studentId) {
        log.info("Add student with ID = " + studentId + " to group with ID = " + groupId);
        convertUserToStudent(studentId);
        entityManager.createQuery(DISTRIBUTE_STUDENT_TO_GROUP_QUERY_HQL)
        .setParameter("groupId", groupId)
        .setParameter("id", studentId)
        .executeUpdate();
        log.info("Student with ID = " + studentId + " added to group with ID = " + groupId);
    }

    @Override
    @Transactional
    public void fillRandomStudentCourseTable(List<Student> studentsAssignedToCourses) {
        int batchSize = studentsAssignedToCourses.size() - 1;
        log.info("Fill random student to course table");
        for (int i = 0; i < studentsAssignedToCourses.size(); i++) {
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
            entityManager.merge(studentsAssignedToCourses.get(i));
        }
        log.info("Random student filled to course table");
    }
    
    @Override
    @Transactional
    public void removeStudentFromCourse(Integer studentId, Integer courseId) {
        log.info("Delete Student with ID = " + studentId + " to Course with ID = " + courseId);
        Student student = findById(studentId).get();
        Course course = entityManager.createQuery(FIND_COURSE_BY_ID_QUERY_HQL, Course.class)
                .setParameter("id", courseId).getSingleResult();
        student.removeCourse(course);
        entityManager.persist(student);
        log.info("Student with ID = " + studentId + " deleted to Course with ID = " + courseId);
    }
    
    @Override
    @Transactional
    public void addStudentToCourse(Integer studentId, Integer courseId) {
        log.info("Add Student with ID = " + studentId + " to Course with ID = " + courseId);
        convertUserToStudent(studentId);
        Student student = findById(studentId).get();
        Course course = entityManager.createQuery(FIND_COURSE_BY_ID_QUERY_HQL, Course.class)
                .setParameter("id", courseId).getSingleResult();
        student.addCourse(course);
        entityManager.persist(student);
        log.info("Student with ID = " + studentId + " added to Course with ID = " + courseId);
    }
    
    @Override
    public Set<Course> getAllStudentCoursesByStudentID(Integer studentId) {
        log.trace("Get all student courses by student ID = " + studentId);
        return findById(studentId).get().getCourses();
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

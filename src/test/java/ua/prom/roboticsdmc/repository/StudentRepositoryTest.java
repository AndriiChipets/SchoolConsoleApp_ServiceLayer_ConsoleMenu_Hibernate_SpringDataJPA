package ua.prom.roboticsdmc.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.ClassRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ua.prom.roboticsdmc.config.SchoolApplicationConfig;
import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.testcontainer.PostgresqlTestContainer;

@ActiveProfiles("test")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        StudentRepository.class }))
@ContextConfiguration(classes=SchoolApplicationConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = { 
                "/sql/schema.sql", 
                "/sql/dataCourses.sql", 
                "/sql/dataGroups.sql", 
                "/sql/dataUsers.sql",
                "/sql/dataStudentCourse.sql" }, 
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@DisplayName("StudentRepositoryTest")
class StudentRepositoryTest {
    
    @ClassRule
    public static PostgreSQLContainer<?> postgreSQLContainer = PostgresqlTestContainer.getInstance();

    @Autowired
    StudentRepository studentRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
      
    @Test
    @DisplayName("addStudentToGroup method should add Student to Group")
    void addStudentToGroup_shouldAddStudentToGroup_whenEnteredDataIsCorrect() {

        int userId = 3;
        int groupId = 1;
        Student student = Student.builder().withUserId(userId).withGroupId(groupId).build();

        studentRepository.addStudentToGroup(student);

        assertEquals(groupId, studentRepository.findById(userId).get().getGroupId());
    }
    
    @Test
    @DisplayName("addStudentToCourse method should add Student to Course")
    void addStudentToCourse_shouldAddStudentToCourse_whenEnteredDataIsCorrect() {

        int userId = 2;
        int courseId = 5;
        Student student = Student.builder().withUserId(userId).build();
        Course course = Course.builder().withCourseId(courseId).build();
        Set<Course> actualStudentCourses = null;

        studentRepository.addStudentToCourse(student, course);
        actualStudentCourses = studentRepository.findById(userId).get().getCourses();

        assertTrue(actualStudentCourses.stream().anyMatch(c -> c.getCourseId() == courseId));
    }

    @Test
    @DisplayName("removeStudentFromCourse method should deleteStudent from Course")
    void removeStudentFromCourse_shoulRemoveStudentFromCourse_whenEnteredDataIsCorrect() {

        int userId = 1;
        int courseId = 1;
        Student student = Student.builder().withUserId(userId).build();
        Course course = Course.builder().withCourseId(courseId).build();

        studentRepository.removeStudentFromCourse(student, course);
        Set<Course> actualStudentCourses = studentRepository.findById(userId).get().getCourses();

        assertTrue(actualStudentCourses.stream().noneMatch(c -> c.getCourseId() == courseId));
    }
    
}

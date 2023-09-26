package ua.prom.roboticsdmc.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.ClassRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @DisplayName("save method should add Student to the table")
    void save_shouldAddStudentToTheTable_whenEnteredDataIsCorrect() {

        int expectedUserId = 10;
        int groupId = 4;
        String firstName = "James";
        String lastName = "Garcia";
        String email = "james.garsia@gmail.com";
        
        Student addedStudent = Student.builder()
                .withGroupId(groupId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .build();

        Optional<Student> expectedStudent = Optional.of(
                Student.builder()
                .withUserId(expectedUserId)
                .withGroupId(groupId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .build());

        studentRepository.save(addedStudent);

        assertEquals(expectedStudent, studentRepository.findById(expectedUserId));
    }

    @Test
    @DisplayName("saveAll method should add Students from the table")
    void saveAll_shouldAddStudentsToTable_whenEnteredDataIsCorrect() {
        
        Set<Course> userId1Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(1).withCourseName("Math").build(),
                Course.builder().withCourseId(3).withCourseName("Philosophy").build()));
        Set<Course> userId2Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(3).withCourseName("Philosophy").build(),
                Course.builder().withCourseId(6).withCourseName("Chemistry").build()));
        Set<Course> userId3Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(2).withCourseName("Biology").build(),
                Course.builder().withCourseId(4).withCourseName("Literature").build(),
                Course.builder().withCourseId(5).withCourseName("English").build()));
        Set<Course> userId4Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(2).withCourseName("Biology").build()));
        Set<Course> userId5Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(4).withCourseName("Literature").build()));
        Set<Course> userId6Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(2).withCourseName("Biology").build(),
                Course.builder().withCourseId(3).withCourseName("Philosophy").build()));
        Set<Course> userId7Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(2).withCourseName("Biology").build()));
        Set<Course> userId8Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(4).withCourseName("Literature").build()));
        Set<Course> userId9Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(2).withCourseName("Biology").build(),
                Course.builder().withCourseId(4).withCourseName("Literature").build()));
        Set<Course> userId10Courses = new HashSet<>();
        Set<Course> userId11Courses = new HashSet<>();

        List<Student> addedStudents = new ArrayList<Student>(Arrays.asList(
                Student.builder()
                .withGroupId(5)
                .withFirstName("Christopher")
                .withLastName("Thomas")
                .withEmail("christopher.thomas@gmail.com")
                .withPassword("christopher1234")
                .build(),
                Student.builder()
                .withGroupId(3)
                .withFirstName("Patricia")
                .withLastName("Wilson")
                .withEmail("patricia.wilson@gmail.com")
                .withPassword("patricia1234")
                .build()));
        
        List<Student> expectedStudents = new ArrayList<Student>(Arrays.asList(
                Student.builder()
                .withUserId(1)
                .withGroupId(1)
                .withFirstName("Michael")
                .withLastName("Thomas")
                .withEmail("michael.thomas@gmail.com")
                .withPassword("michael1234")
                .withCourses(userId1Courses)
                .build(),
                Student.builder()
                .withUserId(2)
                .withGroupId(2)
                .withFirstName("Christopher")
                .withLastName("Garcia")
                .withEmail("christopher.garcia@gmail.com")
                .withPassword("christopher1234")
                .withCourses(userId2Courses)
                .build(),
                Student.builder()
                .withUserId(3)
                .withGroupId(2)
                .withFirstName("Patricia")
                .withLastName("Garcia")
                .withEmail("patricia.garcia@gmail.com")
                .withPassword("patricia1234")
                .withCourses(userId3Courses)
                .build(),
                Student.builder()
                .withUserId(4)
                .withGroupId(4)
                .withFirstName("Patricia")
                .withLastName("Jackson")
                .withEmail("patricia.jackson@gmail.com")
                .withPassword("patricia2345")
                .withCourses(userId4Courses)
                .build(),
                Student.builder()
                .withUserId(5)
                .withGroupId(5)
                .withFirstName("William")
                .withLastName("Wilson")
                .withEmail("william.wilson@gmail.com")
                .withPassword("william1234")
                .withCourses(userId5Courses)
                .build(),
                Student.builder()
                .withUserId(6)
                .withGroupId(4)
                .withFirstName("James")
                .withLastName("Williams")
                .withEmail("james.williams@gmail.com")
                .withPassword("james1234")
                .withCourses(userId6Courses)
                .build(),
                Student.builder()
                .withUserId(7)
                .withGroupId(2)
                .withFirstName("Robert")
                .withLastName("Rodriguez")
                .withEmail("robert.rodriguez@gmail.com")
                .withPassword("robert1234")
                .withCourses(userId7Courses)
                .build(),
                Student.builder()
                .withUserId(8)
                .withGroupId(1)
                .withFirstName("John")
                .withLastName("Martinez")
                .withEmail("john.martinez@gmail.com")
                .withPassword("john1234")
                .withCourses(userId8Courses)
                .build(),
                Student.builder()
                .withUserId(9)
                .withGroupId(5)
                .withFirstName("Karen")
                .withLastName("Garcia")
                .withEmail("karen.garcia@gmail.com")
                .withPassword("karen1234")
                .withCourses(userId9Courses)
                .build(),
                Student.builder()
                .withUserId(10)
                .withGroupId(5)
                .withFirstName("Christopher")
                .withLastName("Thomas")
                .withEmail("christopher.thomas@gmail.com")
                .withPassword("christopher1234")
                .withCourses(userId10Courses)
                .build(),
                Student.builder()
                .withUserId(11)
                .withGroupId(3)
                .withFirstName("Patricia")
                .withLastName("Wilson")
                .withEmail("patricia.wilson@gmail.com")
                .withPassword("patricia1234")
                .withCourses(userId11Courses)
                .build()));

        studentRepository.saveAll(addedStudents);
        assertEquals(expectedStudents, studentRepository.findAll());
    }

    @Test
    @DisplayName("findById method should return Student if Student exists")
    void findById_shouldReturnStudent_whenThereIsSomeStudentInTableWithEnteredStudentId() {

        int userId = 1;
        Set<Course> courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(1).withCourseName("Math").build(),
                Course.builder().withCourseId(3).withCourseName("Philosophy").build()));
        
        Optional<Student> expectedStudent = Optional.of(
                Student.builder()
                .withUserId(1)
                .withGroupId(1)
                .withFirstName("Michael")
                .withLastName("Thomas")
                .withEmail("michael.thomas@gmail.com")
                .withPassword("michael1234")
                .withCourses(courses)
                .build());

        assertEquals(expectedStudent, studentRepository.findById(userId));
    }

    @Test
    @DisplayName("findById method should return empty Optional if Student not exists")
    void findById_shouldReturnEmptyOptional_whenThereIsNotAnyStudentInTableWithEnteredStudentId() {

        int userId = 100;

        assertEquals(Optional.empty(), studentRepository.findById(userId));
    }

    @Test
    @DisplayName("findAll method without pagination should return all Students if Students exist")
    void findAll_shouldReturnAllStudents_whenThereAreSomeStudentsInTable() {
        
        Set<Course> userId1Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(1).withCourseName("Math").build(),
                Course.builder().withCourseId(3).withCourseName("Philosophy").build()));
        Set<Course> userId2Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(3).withCourseName("Philosophy").build(),
                Course.builder().withCourseId(6).withCourseName("Chemistry").build()));
        Set<Course> userId3Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(2).withCourseName("Biology").build(),
                Course.builder().withCourseId(4).withCourseName("Literature").build(),
                Course.builder().withCourseId(5).withCourseName("English").build()));
        Set<Course> userId4Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(2).withCourseName("Biology").build()));
        Set<Course> userId5Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(4).withCourseName("Literature").build()));
        Set<Course> userId6Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(2).withCourseName("Biology").build(),
                Course.builder().withCourseId(3).withCourseName("Philosophy").build()));
        Set<Course> userId7Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(2).withCourseName("Biology").build()));
        Set<Course> userId8Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(4).withCourseName("Literature").build()));
        Set<Course> userId9Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(2).withCourseName("Biology").build(),
                Course.builder().withCourseId(4).withCourseName("Literature").build()));

        List<Student> expectedStudents = new ArrayList<Student>(Arrays.asList(
                Student.builder()
                .withUserId(1)
                .withGroupId(1)
                .withFirstName("Michael")
                .withLastName("Thomas")
                .withEmail("michael.thomas@gmail.com")
                .withPassword("michael1234")
                .withCourses(userId1Courses)
                .build(),
                Student.builder()
                .withUserId(2)
                .withGroupId(2)
                .withFirstName("Christopher")
                .withLastName("Garcia")
                .withEmail("christopher.garcia@gmail.com")
                .withPassword("christopher1234")
                .withCourses(userId2Courses)
                .build(),
                Student.builder()
                .withUserId(3)
                .withGroupId(2)
                .withFirstName("Patricia")
                .withLastName("Garcia")
                .withEmail("patricia.garcia@gmail.com")
                .withPassword("patricia1234")
                .withCourses(userId3Courses)
                .build(),
                Student.builder()
                .withUserId(4)
                .withGroupId(4)
                .withFirstName("Patricia")
                .withLastName("Jackson")
                .withEmail("patricia.jackson@gmail.com")
                .withPassword("patricia2345")
                .withCourses(userId4Courses)
                .build(),
                Student.builder()
                .withUserId(5)
                .withGroupId(5)
                .withFirstName("William")
                .withLastName("Wilson")
                .withEmail("william.wilson@gmail.com")
                .withPassword("william1234")
                .withCourses(userId5Courses)
                .build(),
                Student.builder()
                .withUserId(6)
                .withGroupId(4)
                .withFirstName("James")
                .withLastName("Williams")
                .withEmail("james.williams@gmail.com")
                .withPassword("james1234")
                .withCourses(userId6Courses)
                .build(),
                Student.builder()
                .withUserId(7)
                .withGroupId(2)
                .withFirstName("Robert")
                .withLastName("Rodriguez")
                .withEmail("robert.rodriguez@gmail.com")
                .withPassword("robert1234")
                .withCourses(userId7Courses)
                .build(),
                Student.builder()
                .withUserId(8)
                .withGroupId(1)
                .withFirstName("John")
                .withLastName("Martinez")
                .withEmail("john.martinez@gmail.com")
                .withPassword("john1234")
                .withCourses(userId8Courses)
                .build(),
                Student.builder()
                .withUserId(9)
                .withGroupId(5)
                .withFirstName("Karen")
                .withLastName("Garcia")
                .withEmail("karen.garcia@gmail.com")
                .withPassword("karen1234")
                .withCourses(userId9Courses)
                .build()));

        assertEquals(expectedStudents, studentRepository.findAll());
    }

    @Test
    @DisplayName("findAll method with pagination should return Students with defined offset and limit")
    void findAll_withPaginationShouldReturnDefinedListOfStudents_whenThereAreStudentsInTableWithOffsetAndLimit() {

        int pageNumber = 1;
        int studentOnPage = 2;
        Pageable pegination = PageRequest.of(pageNumber, studentOnPage, Sort.by("userId"));
        
        Set<Course> userId3Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(2).withCourseName("Biology").build(),
                Course.builder().withCourseId(4).withCourseName("Literature").build(),
                Course.builder().withCourseId(5).withCourseName("English").build()));
        Set<Course> userId4Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(2).withCourseName("Biology").build()));
        
        List<Student> expectedStudents = new ArrayList<Student>(Arrays.asList(
                Student.builder()
                .withUserId(3)
                .withGroupId(2)
                .withFirstName("Patricia")
                .withLastName("Garcia")
                .withEmail("patricia.garcia@gmail.com")
                .withPassword("patricia1234")
                .withCourses(userId3Courses)
                .build(),
                Student.builder()
                .withUserId(4)
                .withGroupId(4)
                .withFirstName("Patricia")
                .withLastName("Jackson")
                .withEmail("patricia.jackson@gmail.com")
                .withPassword("patricia2345")
                .withCourses(userId4Courses)
                .build()));
        
        
        Page <Student> studentPage = studentRepository.findAll(pegination);
        List<Student> actualStudents = studentPage.getContent();

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    @DisplayName("update method should update Student in the table")
    void update_shouldUpdateStudentInTable_whenEnteredDataIsCorrect() {

        int userId = 3;
        int groupId = 5;
        String firstName = "James";
        String lastName = "Garcia";
        String email = "james.garcia@gmail.com";
        Student updatedStudent = Student.builder()
                .withUserId(userId)
                .withGroupId(groupId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .build();
        Optional<Student> expectedStudent = Optional.of(
                Student.builder()
                .withUserId(userId)
                .withGroupId(groupId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .build());

        studentRepository.save(updatedStudent);

        assertEquals(expectedStudent, studentRepository.findById(userId));
    }

    @Test
    @DisplayName("deleteById method should delete Student from the table")
    void deleteById_shouldDeleteCStudent_whenThereIsSomeStudentInTableWithEnteredStudentId() {

        int userId = 1;

        studentRepository.deleteById(userId);

        assertEquals(Optional.empty(), studentRepository.findById(userId));
    }
      
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

package ua.prom.roboticsdmc.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

import ua.prom.roboticsdmc.config.SchoolApplicationConfig;
import ua.prom.roboticsdmc.dao.StudentDao;
import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.testcontainer.PostgresqlTestContainer;

@ActiveProfiles("test")
@JdbcTest
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
@DisplayName("StudentDaoImplTest")
class StudentDaoImplTest {
    
    @ClassRule
    public static PostgreSQLContainer<?> postgreSQLContainer = PostgresqlTestContainer.getInstance();

    @Autowired
    JdbcTemplate jdbcTemplate;
    StudentDao studentDao;

    @BeforeEach
    void setUp() {
        studentDao = new StudentDaoImpl(jdbcTemplate);
    }

    @Test
    @DisplayName("save method should add Student to the table")
    void save_shouldAddStudentToTheTable_whenEnteredDataIsCorrect() {

        int expectedStudentId = 10;
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
                .withUserId(expectedStudentId)
                .withGroupId(groupId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .build());

        studentDao.save(addedStudent);

        assertEquals(expectedStudent, studentDao.findById(expectedStudentId));
    }

    @Test
    @DisplayName("saveAll method should add Students from the table")
    void saveAll_shouldAddStudentsToTable_whenEnteredDataIsCorrect() {

        List<Student> addedStudents = new ArrayList<Student>(Arrays.asList(
                Student.builder()
                .withGroupId(5)
                .withFirstName("Christopher")
                .withLastName("Thomas")
                .withEmail("christopher.thomas@gmail.com")
                .build(),
                Student.builder()
                .withGroupId(3)
                .withFirstName("Patricia")
                .withLastName("Wilson")
                .withEmail("patricia.wilson@gmail.com")
                .build()));
        List<Student> expectedStudents = new ArrayList<Student>(Arrays.asList(
                Student.builder()
                .withUserId(1)
                .withGroupId(1)
                .withFirstName("Michael")
                .withLastName("Thomas")
                .withEmail("michael.thomas@gmail.com")
                .build(),
                Student.builder()
                .withUserId(2)
                .withGroupId(2)
                .withFirstName("Christopher")
                .withLastName("Garcia")
                .withEmail("christopher.garcia@gmail.com")
                .build(),
                Student.builder()
                .withUserId(3)
                .withGroupId(2)
                .withFirstName("Patricia")
                .withLastName("Garcia")
                .withEmail("patricia.garcia@gmail.com")
                .build(),
                Student.builder()
                .withUserId(4)
                .withGroupId(4)
                .withFirstName("Patricia")
                .withLastName("Jackson")
                .withEmail("patricia.jackson@gmail.com")
                .build(),
                Student.builder()
                .withUserId(5)
                .withGroupId(5)
                .withFirstName("William")
                .withLastName("Wilson")
                .withEmail("william.wilson@gmail.com")
                .build(),
                Student.builder()
                .withUserId(6)
                .withGroupId(4)
                .withFirstName("James")
                .withLastName("Williams")
                .withEmail("james.williams@gmail.com")
                .build(),
                Student.builder()
                .withUserId(7)
                .withGroupId(2)
                .withFirstName("Robert")
                .withLastName("Rodriguez")
                .withEmail("robert.rodriguez@gmail.com")
                .build(),
                Student.builder()
                .withUserId(8)
                .withGroupId(1)
                .withFirstName("John")
                .withLastName("Martinez")
                .withEmail("john.martinez@gmail.com")
                .build(),
                Student.builder()
                .withUserId(9)
                .withGroupId(5)
                .withFirstName("Karen")
                .withLastName("Garcia")
                .withEmail("karen.garcia@gmail.com")
                .build(),
                Student.builder()
                .withUserId(10)
                .withGroupId(5)
                .withFirstName("Christopher")
                .withLastName("Thomas")
                .withEmail("christopher.thomas@gmail.com")
                .build(),
                Student.builder()
                .withUserId(11)
                .withGroupId(3)
                .withFirstName("Patricia")
                .withLastName("Wilson")
                .withEmail("patricia.wilson@gmail.com")
                .build()));

        studentDao.saveAll(addedStudents);
        assertEquals(expectedStudents, studentDao.findAll());
    }

    @Test
    @DisplayName("findById method should return Student if Student exists")
    void findById_shouldReturnStudent_whenThereIsSomeStudentInTableWithEnteredStudentId() {

        int studentId = 1;
        Optional<Student> expectedStudent = Optional.of(
                Student.builder()
                .withUserId(1)
                .withGroupId(1)
                .withFirstName("Michael")
                .withLastName("Thomas")
                .withEmail("michael.thomas@gmail.com")
                .build());

        assertEquals(expectedStudent, studentDao.findById(studentId));
    }

    @Test
    @DisplayName("findById method should return empty Optional if Student not exists")
    void findById_shouldReturnEmptyOptional_whenThereIsNotAnyStudentInTableWithEnteredStudentId() {

        int studentId = 100;

        assertEquals(Optional.empty(), studentDao.findById(studentId));
    }

    @Test
    @DisplayName("findAll method without pagination should return all Students if Students exist")
    void findAll_shouldReturnAllStudents_whenThereAreSomeStudentsInTable() {

        List<Student> expectedStudents = new ArrayList<Student>(Arrays.asList(
                Student.builder()
                .withUserId(1)
                .withGroupId(1)
                .withFirstName("Michael")
                .withLastName("Thomas")
                .withEmail("michael.thomas@gmail.com")
                .build(),
                Student.builder()
                .withUserId(2)
                .withGroupId(2)
                .withFirstName("Christopher")
                .withLastName("Garcia")
                .withEmail("christopher.garcia@gmail.com")
                .build(),
                Student.builder()
                .withUserId(3)
                .withGroupId(2)
                .withFirstName("Patricia")
                .withLastName("Garcia")
                .withEmail("patricia.garcia@gmail.com")
                .build(),
                Student.builder()
                .withUserId(4)
                .withGroupId(4)
                .withFirstName("Patricia")
                .withLastName("Jackson")
                .withEmail("patricia.jackson@gmail.com")
                .build(),
                Student.builder()
                .withUserId(5)
                .withGroupId(5)
                .withFirstName("William")
                .withLastName("Wilson")
                .withEmail("william.wilson@gmail.com")
                .build(),
                Student.builder()
                .withUserId(6)
                .withGroupId(4)
                .withFirstName("James")
                .withLastName("Williams")
                .withEmail("james.williams@gmail.com")
                .build(),
                Student.builder()
                .withUserId(7)
                .withGroupId(2)
                .withFirstName("Robert")
                .withLastName("Rodriguez")
                .withEmail("robert.rodriguez@gmail.com")
                .build(),
                Student.builder()
                .withUserId(8)
                .withGroupId(1)
                .withFirstName("John")
                .withLastName("Martinez")
                .withEmail("john.martinez@gmail.com")
                .build(),
                Student.builder()
                .withUserId(9)
                .withGroupId(5)
                .withFirstName("Karen")
                .withLastName("Garcia")
                .withEmail("karen.garcia@gmail.com")
                .build()));

        assertEquals(expectedStudents, studentDao.findAll());
    }

    @Test
    @DisplayName("findAll method with pagination should return Students with defined offset and limit")
    void findAll_withPaginationShouldReturnDefinedListOfStudents_whenThereAreStudentsInTableWithOffsetAndLimit() {

        int rowOffset = 2;
        int rowLimit = 2;
        List<Student> expectedStudents = new ArrayList<Student>(Arrays.asList(
                Student.builder()
                .withUserId(3)
                .withGroupId(2)
                .withFirstName("Patricia")
                .withLastName("Garcia")
                .withEmail("patricia.garcia@gmail.com")
                .build(),
                Student.builder()
                .withUserId(4)
                .withGroupId(4)
                .withFirstName("Patricia")
                .withLastName("Jackson")
                .withEmail("patricia.jackson@gmail.com")
                .build()));

        assertEquals(expectedStudents, studentDao.findAll(rowOffset, rowLimit));
    }

    @Test
    @DisplayName("update method should update Student in the table")
    void update_shouldUpdateStudentInTable_whenEnteredDataIsCorrect() {

        int studentId = 3;
        int groupId = 5;
        String firstName = "James";
        String lastName = "Garcia";
        String email = "james.garcia@gmail.com";
        Student updatedStudent = Student.builder()
                .withUserId(studentId)
                .withGroupId(groupId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .build();
        Optional<Student> expectedStudent = Optional.of(
                Student.builder()
                .withUserId(studentId)
                .withGroupId(groupId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .build());

        studentDao.update(updatedStudent);

        assertEquals(expectedStudent, studentDao.findById(studentId));
    }

    @Test
    @DisplayName("deleteById method should delete Student from the table")
    void deleteById_shouldDeleteCStudent_whenThereIsSomeStudentInTableWithEnteredStudentId() {

        int studentId = 1;

        studentDao.deleteById(studentId);

        assertEquals(Optional.empty(), studentDao.findById(studentId));
    }

    @Test
    @DisplayName("findStudentsByCourseName method should return List of Students")
    void findStudentsByCourseName_shouldReturnListOfStudentsRelatedToCourse_whenThereAreAnyStudentsRelatedToEnteredCourseName() {

        String courseName = "Biology";
        List<Student> expectedStudents = new ArrayList<Student>(Arrays.asList(
                Student.builder()
                .withUserId(3)
                .withGroupId(2)
                .withFirstName("Patricia")
                .withLastName("Garcia")
                .withEmail("patricia.garcia@gmail.com")
                .build(),
                Student.builder()
                .withUserId(4)
                .withGroupId(4)
                .withFirstName("Patricia")
                .withLastName("Jackson")
                .withEmail("patricia.jackson@gmail.com")
                .build(),
                Student.builder()
                .withUserId(6)
                .withGroupId(4)
                .withFirstName("James")
                .withLastName("Williams")
                .withEmail("james.williams@gmail.com")
                .build(),
                Student.builder()
                .withUserId(7)
                .withGroupId(2)
                .withFirstName("Robert")
                .withLastName("Rodriguez")
                .withEmail("robert.rodriguez@gmail.com")
                .build(),
                Student.builder()
                .withUserId(9)
                .withGroupId(5)
                .withFirstName("Karen")
                .withLastName("Garcia")
                .withEmail("karen.garcia@gmail.com")
                .build()));

        assertEquals(expectedStudents, studentDao.findStudentsByCourseName(courseName));
    }
    
    @Test
    @DisplayName("distributeStudentsToGroups method should add Students to Groups")
    void distributeStudentsToGroups_shouldAddStudentsToGroups_whenEnteredDataIsCorrect() {
        
        List<Student> distributedDtudents = new ArrayList<Student>(Arrays.asList(
                Student.builder()
                .withUserId(1)
                .withGroupId(2)
                .build(),
                Student.builder()
                .withUserId(2)
                .withGroupId(1)
                .build(),
                Student.builder()
                .withUserId(3)
                .withGroupId(4)
                .build(),
                Student.builder()
                .withUserId(4)
                .withGroupId(2)
                .build(),
                Student.builder()
                .withUserId(5)
                .withGroupId(4)
                .build(),
                Student.builder()
                .withUserId(6)
                .withGroupId(5)
                .build(),
                Student.builder()
                .withUserId(7)
                .withGroupId(1)
                .build(),
                Student.builder()
                .withUserId(8)
                .withGroupId(2)
                .build(),
                Student.builder()
                .withUserId(9)
                .withGroupId(4)
                .build()));

        List<Student> expectedStudents = new ArrayList<Student>(Arrays.asList(
                Student.builder()
                .withUserId(1)
                .withGroupId(2)
                .withFirstName("Michael")
                .withLastName("Thomas")
                .withEmail("michael.thomas@gmail.com")
                .build(),
                Student.builder()
                .withUserId(2)
                .withGroupId(1)
                .withFirstName("Christopher")
                .withLastName("Garcia")
                .withEmail("christopher.garcia@gmail.com")
                .build(),
                Student.builder()
                .withUserId(3)
                .withGroupId(4)
                .withFirstName("Patricia")
                .withLastName("Garcia")
                .withEmail("patricia.garcia@gmail.com")
                .build(),
                Student.builder()
                .withUserId(4)
                .withGroupId(2)
                .withFirstName("Patricia")
                .withLastName("Jackson")
                .withEmail("patricia.jackson@gmail.com")
                .build(),
                Student.builder()
                .withUserId(5)
                .withGroupId(4)
                .withFirstName("William")
                .withLastName("Wilson")
                .withEmail("william.wilson@gmail.com")
                .build(),
                Student.builder()
                .withUserId(6)
                .withGroupId(5)
                .withFirstName("James")
                .withLastName("Williams")
                .withEmail("james.williams@gmail.com")
                .build(),
                Student.builder()
                .withUserId(7)
                .withGroupId(1)
                .withFirstName("Robert")
                .withLastName("Rodriguez")
                .withEmail("robert.rodriguez@gmail.com")
                .build(),
                Student.builder()
                .withUserId(8)
                .withGroupId(2)
                .withFirstName("John")
                .withLastName("Martinez")
                .withEmail("john.martinez@gmail.com")
                .build(),
                Student.builder()
                .withUserId(9)
                .withGroupId(4)
                .withFirstName("Karen")
                .withLastName("Garcia")
                .withEmail("karen.garcia@gmail.com")
                .build()));
        
        studentDao.distributeStudentsToGroups(distributedDtudents);
        
        assertEquals(expectedStudents, studentDao.findAll());
    }
    
    @Test
    @DisplayName("addStudentToGroup method should add Student to Group")
    void addStudentToGroup_shouldAddStudentToGroup_whenEnteredDataIsCorrect() {

        int studentId = 3;
        int groupId = 1;

        studentDao.addStudentToGroup(groupId, studentId);

        assertEquals(groupId, studentDao.findById(studentId).get().getGroupId());
    }

}

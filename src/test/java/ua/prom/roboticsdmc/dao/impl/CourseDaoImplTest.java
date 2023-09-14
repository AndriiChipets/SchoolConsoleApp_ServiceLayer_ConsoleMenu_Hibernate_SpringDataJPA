package ua.prom.roboticsdmc.dao.impl;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

import ua.prom.roboticsdmc.config.SchoolApplicationConfig;
import ua.prom.roboticsdmc.dao.CourseDao;
import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.testcontainer.PostgresqlTestContainer;

@ActiveProfiles("test")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        CourseDaoImpl.class }))
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
@DisplayName("CourseDaoImplTest")
class CourseDaoImplTest {
    
    @ClassRule
    public static PostgreSQLContainer<?> postgreSQLContainer = PostgresqlTestContainer.getInstance();

    @Autowired
    CourseDao courseDao;

    @Test
    @DisplayName("save method should add Course to the table")
    void save_shouldAddCourseToTable_whenEnteredDataIsCorrect() {

        int expectedCourseId = 7;
        String courseName = "Ukranian";
        Course addedCourse = Course.builder().withCourseName(courseName).build();
        Optional<Course> expectedCourse = Optional.of(Course.builder()
                        .withCourseId(expectedCourseId)
                        .withCourseName("Ukranian")
                        .build());

        courseDao.save(addedCourse);

        assertEquals(expectedCourse, courseDao.findById(expectedCourseId));
    }

    @Test
    @DisplayName("saveAll method should add Courses to the table")
    void saveAll_shouldAddAllCoursesToTable_whenEnteredDataIsCorrect() {

        List<Course> addedCourses = new ArrayList<Course>(
                Arrays.asList(Course.builder()
                        .withCourseName("Ukranian")
                        .build(),
                        Course.builder()
                        .withCourseName("Physics")
                        .build()));

        List<Course> expectedCourses = new ArrayList<Course>(
                Arrays.asList(
                        Course.builder()
                        .withCourseId(1)
                        .withCourseName("Math")
                        .build(),
                        Course.builder()
                        .withCourseId(2)
                        .withCourseName("Biology")
                        .build(),
                        Course.builder()
                        .withCourseId(3)
                        .withCourseName("Philosophy")
                        .build(),
                        Course.builder()
                        .withCourseId(4)
                        .withCourseName("Literature")
                        .build(),
                        Course.builder()
                        .withCourseId(5)
                        .withCourseName("English")
                        .build(),
                        Course.builder()
                        .withCourseId(6)
                        .withCourseName("Chemistry")
                        .build(),
                        Course.builder()
                        .withCourseId(7)
                        .withCourseName("Ukranian").build(),
                        Course.builder()
                        .withCourseId(8)
                        .withCourseName("Physics")
                        .build()));

        courseDao.saveAll(addedCourses);
        assertEquals(expectedCourses, courseDao.findAll());
    }

    @Test
    @DisplayName("finfById method should return Course from the table id Course exists")
    void findById_shouldReturnCourse_whenThereIsSomeCourseInTableWithEnteredCourseId() {

        int courseId = 1;
        Optional<Course> expectedCourse = Optional.of(
                Course.builder()
                .withCourseId(1)
                .withCourseName("Math")
                .build());

        assertEquals(expectedCourse, courseDao.findById(courseId));
    }

    @Test
    @DisplayName("findById method should return empty Optional if Course not exists")
    void findById_shouldReturnEmptyOptional_whenThereIsNotAnyCourseInTableWithEnteredCourseId() {

        int courseId = 100;

        assertEquals(Optional.empty(), courseDao.findById(courseId));
    }

    @Test
    @DisplayName("findAll method without pagination should return all Courses from the table if Courses exist")
    void findAll_shouldReturnAllListOfCourses_whenThereAreSomeCoursesInTable() {

        List<Course> expectedCourses = new ArrayList<Course>(
                Arrays.asList(
                        Course.builder()
                        .withCourseId(1)
                        .withCourseName("Math")
                        .build(),
                        Course.builder()
                        .withCourseId(2)
                        .withCourseName("Biology")
                        .build(),
                        Course.builder()
                        .withCourseId(3)
                        .withCourseName("Philosophy")
                        .build(),
                        Course.builder()
                        .withCourseId(4)
                        .withCourseName("Literature")
                        .build(),
                        Course.builder()
                        .withCourseId(5)
                        .withCourseName("English")
                        .build(),
                        Course.builder()
                        .withCourseId(6)
                        .withCourseName("Chemistry")
                        .build()));

        assertEquals(expectedCourses, courseDao.findAll());
    }

    @Test
    @DisplayName("findAll method with pagination should return Courses with defined offset and limit")
    void findAll_withPaginationShouldReturnDefinedListOfCourses_whenThereAreCoursesInTableWithOffsetAndLimit() {

        int rowOffset = 2;
        int rowLimit = 3;
        List<Course> expectedCourses = new ArrayList<Course>(Arrays.asList(
                        Course.builder()
                        .withCourseId(3)
                        .withCourseName("Philosophy")
                        .build(),
                        Course.builder()
                        .withCourseId(4)
                        .withCourseName("Literature")
                        .build(),
                        Course.builder()
                        .withCourseId(5)
                        .withCourseName("English")
                        .build()));

        assertEquals(expectedCourses, courseDao.findAll(rowOffset, rowLimit));
    }

    @Test
    @DisplayName("update method should update Course in the table")
    void update_shouldUpdateCourseInTable_whenEnteredDataIsCorrect() {

        int courseId = 1;
        String courseName = "Geometry";
        Course updatedCourse = Course.builder().withCourseId(courseId).withCourseName(courseName).build();
        Optional<Course> expectedCourse = Optional.of(
                Course.builder()
                .withCourseId(courseId)
                .withCourseName(courseName)
                .build());
        
        courseDao.update(updatedCourse);

        assertEquals(expectedCourse, courseDao.findById(courseId));
    }

    @Test
    @DisplayName("deleteById method should delete Course from the table")
    void deleteById_shouldDeleteCourse_whenThereIsSomeCourseInTableWithEnteredCourseId() {

        int courseId = 1;

        courseDao.deleteById(courseId);

        assertEquals(Optional.empty(), courseDao.findById(courseId));
    }
    
    @Test
    @DisplayName("findStudentsByCourseName method should return List of Students")
    void findStudentsByCourseName_shouldReturnListOfStudentsRelatedToCourse_whenThereAreAnyStudentsRelatedToEnteredCourseName() {

        String courseName = "Biology";
        
        Set<Course> userId3Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(2).withCourseName("Biology").build(),
                Course.builder().withCourseId(4).withCourseName("Literature").build(),
                Course.builder().withCourseId(5).withCourseName("English").build()));
        Set<Course> userId4Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(2).withCourseName("Biology").build()));
        Set<Course> userId6Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(2).withCourseName("Biology").build(),
                Course.builder().withCourseId(3).withCourseName("Philosophy").build()));
        Set<Course> userId7Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(2).withCourseName("Biology").build()));
        Set<Course> userId9Courses = new HashSet<>(Arrays.asList(
                Course.builder().withCourseId(2).withCourseName("Biology").build(),
                Course.builder().withCourseId(4).withCourseName("Literature").build()));

        Set<Student> expectedStudents = new HashSet<>(Arrays.asList(
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
                .withUserId(9)
                .withGroupId(5)
                .withFirstName("Karen")
                .withLastName("Garcia")
                .withEmail("karen.garcia@gmail.com")
                .withPassword("karen1234")
                .withCourses(userId9Courses)
                .build())); 

        assertEquals(expectedStudents, courseDao.findStudentsByCourseName(courseName));
    }
    
    @Test
    @DisplayName("findCourseByCourseName method should return Course from the table if Course with given name exists")
    void findCourseByCourseName_shouldReturnCourse_whenThereIsSomeCourseInTableWithEnteredCourseName() {

        String courseName = "Math";
        Optional<Course> expectedCourse = Optional.of(
                Course.builder()
                .withCourseId(1)
                .withCourseName("Math")
                .build());

        assertEquals(expectedCourse, courseDao.findCourseByCourseName(courseName));
    }

    @Test
    @DisplayName("findCourseByCourseName method should return empty Optional if Course doesn't exist")
    void findCourseByCourseName_shouldReturnEmptyOptional_whenThereIsNotAnyCourseInTableWithEnteredCourseName() {

        String courseName = "Wrong name";

        assertEquals(Optional.empty(), courseDao.findCourseByCourseName(courseName));
    }
}

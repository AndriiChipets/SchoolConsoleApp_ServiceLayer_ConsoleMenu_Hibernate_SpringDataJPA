package ua.prom.roboticsdmc.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

import ua.prom.roboticsdmc.config.SchoolApplicationConfig;
import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.testcontainer.PostgresqlTestContainer;

@ActiveProfiles("test")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        CourseRepository.class }))
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
@DisplayName("CourseRepositoryTest")
class CourseRepositoryTest {
    
    @ClassRule
    public static PostgreSQLContainer<?> postgreSQLContainer = PostgresqlTestContainer.getInstance();

    @Autowired
    CourseRepository courseRepository;

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

        courseRepository.save(addedCourse);

        assertEquals(expectedCourse, courseRepository.findById(expectedCourseId));
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

        courseRepository.saveAll(addedCourses);
        assertEquals(expectedCourses, courseRepository.findAll());
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

        assertEquals(expectedCourse, courseRepository.findById(courseId));
    }

    @Test
    @DisplayName("findById method should return empty Optional if Course not exists")
    void findById_shouldReturnEmptyOptional_whenThereIsNotAnyCourseInTableWithEnteredCourseId() {

        int courseId = 100;

        assertEquals(Optional.empty(), courseRepository.findById(courseId));
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

        assertEquals(expectedCourses, courseRepository.findAll());
    }

    @Test
    @DisplayName("findAll method with pagination should return Courses with defined offset and limit")
    void findAll_withPaginationShouldReturnDefinedListOfCourses_whenThereAreCoursesInTableWithOffsetAndLimit() {

        int pageNumber = 1;
        int courseOnPage = 3;
        Pageable pegination = PageRequest.of(pageNumber, courseOnPage, Sort.by("courseId"));
        
        List<Course> expectedCourses = new ArrayList<Course>(Arrays.asList(
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
        
        Page <Course> coursePage = courseRepository.findAll(pegination);
        List<Course> actualCourse = coursePage.getContent();

        assertEquals(expectedCourses, actualCourse);
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
        
        courseRepository.save(updatedCourse);

        assertEquals(expectedCourse, courseRepository.findById(courseId));
    }

    @Test
    @DisplayName("deleteById method should delete Course from the table")
    void deleteById_shouldDeleteCourse_whenThereIsSomeCourseInTableWithEnteredCourseId() {

        int courseId = 1;

        courseRepository.deleteById(courseId);

        assertEquals(Optional.empty(), courseRepository.findById(courseId));
    }
    
    @Test
    @DisplayName("findCourseByCourseName method should return Course")
    void findCourseByCourseName_shouldReturnCourse_whenThereAreAnyCourseRelatedToEnteredCourseName() {

        String courseName = "Biology";
        Optional<Course> expectedCourseOptional = Optional.of(Course.builder().withCourseName(courseName).withCourseId(2).build());

        Optional<Course> actualCourseOptional = courseRepository.findCourseByCourseName(courseName);

        assertEquals(expectedCourseOptional, actualCourseOptional);
    }

    @Test
    @DisplayName("findCourseByCourseName method should return empty Optional if Course doesn't exist")
    void findCourseByCourseName_shouldReturnEmptyOptional_whenThereIsNotAnyCourseInTableWithEnteredCourseName() {

        String courseName = "Wrong name";
        Optional<Course> expectedCourseOptional = Optional.empty();
        
        Optional<Course> actualCourseOptional = courseRepository.findCourseByCourseName(courseName);

        assertEquals(expectedCourseOptional, actualCourseOptional);
    }
}

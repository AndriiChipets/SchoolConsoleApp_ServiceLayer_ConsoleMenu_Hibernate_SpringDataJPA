package ua.prom.roboticsdmc.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

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

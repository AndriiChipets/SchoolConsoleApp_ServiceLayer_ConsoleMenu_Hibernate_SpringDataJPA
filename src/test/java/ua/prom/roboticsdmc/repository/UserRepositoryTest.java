package ua.prom.roboticsdmc.repository;

import static org.junit.Assert.assertFalse;
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
import ua.prom.roboticsdmc.testcontainer.PostgresqlTestContainer;

@ActiveProfiles("test")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        UserRepository.class }))
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
@DisplayName("UserRepositoryTest")
class UserRepositoryTest {
    
    @ClassRule
    public static PostgreSQLContainer<?> postgreSQLContainer = PostgresqlTestContainer.getInstance();

    @Autowired
    UserRepository userRepository;
   
    @Test
    @DisplayName("findByEmail method should return User if User exists")
    void findByEmail_shouldReturnUser_whenThereIsSomeSUserInTableWithEnteredEmail() {

        String expectedEmail = "michael.thomas@gmail.com";

        assertEquals(expectedEmail, userRepository.findByEmail(expectedEmail).get().getEmail());
    }
    
    @Test
    @DisplayName("findByEmail method should return empty Optional if User not exists")
    void findByEmail_shouldReturnEmptyOptional_whenThereIsNotAnyUserInTableWithEnteredEmail() {

        String email = "notexisting.email@gmail.com";

        assertEquals(Optional.empty(), userRepository.findByEmail(email));
    }
    
    @Test
    @DisplayName("isAnyTableInDbSchema method should return True when there is table in data base schema")
    void isAnyTableInDbSchema_shouldReturnTrue_whenThereIsTableInDataBaseSchema() {

        assertFalse(userRepository.isAnyTableInDbSchema());
    }
}

package ua.prom.roboticsdmc.repository;

import static org.junit.Assert.assertFalse;
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
import ua.prom.roboticsdmc.domain.User;
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
    @DisplayName("save method should add User to the table")
    void save_shouldAddUserToTheTable_whenEnteredDataIsCorrect() {

        int expectedUserId = 10;
        String firstName = "James";
        String lastName = "Garcia";
        String email = "james.garsia@gmail.com";
        
        User addedUser = User.builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .build();

        Optional<User> expectedUser = Optional.of(
                User.builder()
                .withUserId(expectedUserId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .build());

        userRepository.save(addedUser);

        assertEquals(expectedUser, userRepository.findById(expectedUserId));
    }

    @Test
    @DisplayName("saveAll method should add Users to the table")
    void saveAll_shouldAddUsersToTable_whenEnteredDataIsCorrect() {

        List<User> addedUsers = new ArrayList<User>(Arrays.asList(
                User.builder()
                .withFirstName("Christopher")
                .withLastName("Thomas")
                .withEmail("christopher.thomas@gmail.com")
                .withPassword("christopher1234")
                .build(),
                User.builder()
                .withFirstName("Patricia")
                .withLastName("Wilson")
                .withEmail("patricia.wilson@gmail.com")
                .withPassword("patricia1234")
                .build()));
        List<User> expectedUsers = new ArrayList<User>(Arrays.asList(
                User.builder()
                .withUserId(1)
                .withFirstName("Michael")
                .withLastName("Thomas")
                .withEmail("michael.thomas@gmail.com")
                .withPassword("michael1234")
                .build(),
                User.builder()
                .withUserId(2)
                .withFirstName("Christopher")
                .withLastName("Garcia")
                .withEmail("christopher.garcia@gmail.com")
                .withPassword("christopher1234")
                .build(),
                User.builder()
                .withUserId(3)
                .withFirstName("Patricia")
                .withLastName("Garcia")
                .withEmail("patricia.garcia@gmail.com")
                .withPassword("patricia1234")
                .build(),
                User.builder()
                .withUserId(4)
                .withFirstName("Patricia")
                .withLastName("Jackson")
                .withEmail("patricia.jackson@gmail.com")
                .withPassword("patricia2345")
                .build(),
                User.builder()
                .withUserId(5)
                .withFirstName("William")
                .withLastName("Wilson")
                .withEmail("william.wilson@gmail.com")
                .withPassword("william1234")
                .build(),
                User.builder()
                .withUserId(6)
                .withFirstName("James")
                .withLastName("Williams")
                .withEmail("james.williams@gmail.com")
                .withPassword("james1234")
                .build(),
                User.builder()
                .withUserId(7)
                .withFirstName("Robert")
                .withLastName("Rodriguez")
                .withEmail("robert.rodriguez@gmail.com")
                .withPassword("robert1234")
                .build(),
                User.builder()
                .withUserId(8)
                .withFirstName("John")
                .withLastName("Martinez")
                .withEmail("john.martinez@gmail.com")
                .withPassword("john1234")
                .build(),
                User.builder()
                .withUserId(9)
                .withFirstName("Karen")
                .withLastName("Garcia")
                .withEmail("karen.garcia@gmail.com")
                .withPassword("karen1234")
                .build(),
                User.builder()
                .withUserId(10)
                .withFirstName("Christopher")
                .withLastName("Thomas")
                .withEmail("christopher.thomas@gmail.com")
                .withPassword("christopher1234")
                .build(),
                User.builder()
                .withUserId(11)
                .withFirstName("Patricia")
                .withLastName("Wilson")
                .withEmail("patricia.wilson@gmail.com")
                .withPassword("patricia1234")
                .build()));

        userRepository.saveAll(addedUsers);
        assertEquals(expectedUsers, userRepository.findAll());
    }

    @Test
    @DisplayName("findById method should return User if User exists")
    void findById_shouldReturnUser_whenThereIsSomeSUserInTableWithEnteredUserId() {

        int userId = 1;
        Optional<User> expectedUser = Optional.of(
                User.builder()
                .withUserId(1)
                .withFirstName("Michael")
                .withLastName("Thomas")
                .withEmail("michael.thomas@gmail.com")
                .withPassword("michael1234")
                .build());

        assertEquals(expectedUser, userRepository.findById(userId));
    }

    @Test
    @DisplayName("findById method should return empty Optional if User not exists")
    void findById_shouldReturnEmptyOptional_whenThereIsNotAnyUserInTableWithEnteredUserId() {

        int userId = 100;

        assertEquals(Optional.empty(), userRepository.findById(userId));
    }

    @Test
    @DisplayName("findAll method without pagination should return all Users if Users exist")
    void findAll_shouldReturnAllUsers_whenThereAreSomeUsersInTable() {

        List<User> expectedUsers = new ArrayList<User>(Arrays.asList(
                User.builder()
                .withUserId(1)
                .withFirstName("Michael")
                .withLastName("Thomas")
                .withEmail("michael.thomas@gmail.com")
                .withPassword("michael1234")
                .build(),
                User.builder()
                .withUserId(2)
                .withFirstName("Christopher")
                .withLastName("Garcia")
                .withEmail("christopher.garcia@gmail.com")
                .withPassword("christopher1234")
                .build(),
                User.builder()
                .withUserId(3)
                .withFirstName("Patricia")
                .withLastName("Garcia")
                .withEmail("patricia.garcia@gmail.com")
                .withPassword("patricia1234")
                .build(),
                User.builder()
                .withUserId(4)
                .withFirstName("Patricia")
                .withLastName("Jackson")
                .withEmail("patricia.jackson@gmail.com")
                .withPassword("patricia2345")
                .build(),
                User.builder()
                .withUserId(5)
                .withFirstName("William")
                .withLastName("Wilson")
                .withEmail("william.wilson@gmail.com")
                .withPassword("william1234")
                .build(),
                User.builder()
                .withUserId(6)
                .withFirstName("James")
                .withLastName("Williams")
                .withEmail("james.williams@gmail.com")
                .withPassword("james1234")
                .build(),
                User.builder()
                .withUserId(7)
                .withFirstName("Robert")
                .withLastName("Rodriguez")
                .withEmail("robert.rodriguez@gmail.com")
                .withPassword("robert1234")
                .build(),
                User.builder()
                .withUserId(8)
                .withFirstName("John")
                .withLastName("Martinez")
                .withEmail("john.martinez@gmail.com")
                .withPassword("john1234")
                .build(),
                User.builder()
                .withUserId(9)
                .withFirstName("Karen")
                .withLastName("Garcia")
                .withEmail("karen.garcia@gmail.com")
                .withPassword("karen1234")
                .build()));

        assertEquals(expectedUsers, userRepository.findAll());
    }

    @Test
    @DisplayName("findAll method with pagination should return Users with defined offset and limit")
    void findAll_withPaginationShouldReturnDefinedListOfUsers_whenThereAreUsersInTableWithOffsetAndLimit() {

        int pageNumber = 1;
        int userOnPage = 2;
        Pageable pegination = PageRequest.of(pageNumber, userOnPage, Sort.by("userId"));
        
        List<User> expectedUsers = new ArrayList<User>(Arrays.asList(
                User.builder()
                .withUserId(3)
                .withFirstName("Patricia")
                .withLastName("Garcia")
                .withEmail("patricia.garcia@gmail.com")
                .withPassword("patricia1234")
                .build(),
                User.builder()
                .withUserId(4)
                .withFirstName("Patricia")
                .withLastName("Jackson")
                .withEmail("patricia.jackson@gmail.com")
                .withPassword("patricia2345")
                .build()));
        
        Page <User> userPage = userRepository.findAll(pegination);
        List<User> actualUsers = userPage.getContent();

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    @DisplayName("update method should update User in the table")
    void update_shouldUpdateUserInTable_whenEnteredDataIsCorrect() {

        int userId = 3;
        String firstName = "James";
        String lastName = "Garcia";
        String email = "james.garcia@gmail.com";
        User updatedUser = User.builder()
                .withUserId(userId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .build();
        Optional<User> expectedUser = Optional.of(
                User.builder()
                .withUserId(userId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .build());

        userRepository.save(updatedUser);

        assertEquals(expectedUser, userRepository.findById(userId));
    }

    @Test
    @DisplayName("deleteById method should delete User from the table")
    void deleteById_shouldDeleteUser_whenThereIsSomeUserInTableWithEnteredUserId() {

        int userId = 1;

        userRepository.deleteById(userId);

        assertEquals(Optional.empty(), userRepository.findById(userId));
    }
    
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

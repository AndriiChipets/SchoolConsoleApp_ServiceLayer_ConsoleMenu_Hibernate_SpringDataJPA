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
import ua.prom.roboticsdmc.dao.UserDao;
import ua.prom.roboticsdmc.domain.User;
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
@DisplayName("UserDaoImplTest")

class UserDaoImplTest {
    
    @ClassRule
    public static PostgreSQLContainer<?> postgreSQLContainer = PostgresqlTestContainer.getInstance();

    @Autowired
    JdbcTemplate jdbcTemplate;
    UserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl(jdbcTemplate);
    }

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

        userDao.save(addedUser);

        assertEquals(expectedUser, userDao.findById(expectedUserId));
    }

    @Test
    @DisplayName("saveAll method should add Users to the table")
    void saveAll_shouldAddUsersToTable_whenEnteredDataIsCorrect() {

        List<User> addedUsers = new ArrayList<User>(Arrays.asList(
                User.builder()
                .withFirstName("Christopher")
                .withLastName("Thomas")
                .withEmail("christopher.thomas@gmail.com")
                .build(),
                User.builder()
                .withFirstName("Patricia")
                .withLastName("Wilson")
                .withEmail("patricia.wilson@gmail.com")
                .build()));
        List<User> expectedUsers = new ArrayList<User>(Arrays.asList(
                User.builder()
                .withUserId(1)
                .withFirstName("Michael")
                .withLastName("Thomas")
                .withEmail("michael.thomas@gmail.com")
                .build(),
                User.builder()
                .withUserId(2)
                .withFirstName("Christopher")
                .withLastName("Garcia")
                .withEmail("christopher.garcia@gmail.com")
                .build(),
                User.builder()
                .withUserId(3)
                .withFirstName("Patricia")
                .withLastName("Garcia")
                .withEmail("patricia.garcia@gmail.com")
                .build(),
                User.builder()
                .withUserId(4)
                .withFirstName("Patricia")
                .withLastName("Jackson")
                .withEmail("patricia.jackson@gmail.com")
                .build(),
                User.builder()
                .withUserId(5)
                .withFirstName("William")
                .withLastName("Wilson")
                .withEmail("william.wilson@gmail.com")
                .build(),
                User.builder()
                .withUserId(6)
                .withFirstName("James")
                .withLastName("Williams")
                .withEmail("james.williams@gmail.com")
                .build(),
                User.builder()
                .withUserId(7)
                .withFirstName("Robert")
                .withLastName("Rodriguez")
                .withEmail("robert.rodriguez@gmail.com")
                .build(),
                User.builder()
                .withUserId(8)
                .withFirstName("John")
                .withLastName("Martinez")
                .withEmail("john.martinez@gmail.com")
                .build(),
                User.builder()
                .withUserId(9)
                .withFirstName("Karen")
                .withLastName("Garcia")
                .withEmail("karen.garcia@gmail.com")
                .build(),
                User.builder()
                .withUserId(10)
                .withFirstName("Christopher")
                .withLastName("Thomas")
                .withEmail("christopher.thomas@gmail.com")
                .build(),
                User.builder()
                .withUserId(11)
                .withFirstName("Patricia")
                .withLastName("Wilson")
                .withEmail("patricia.wilson@gmail.com")
                .build()));

        userDao.saveAll(addedUsers);
        assertEquals(expectedUsers, userDao.findAll());
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
                .build());

        assertEquals(expectedUser, userDao.findById(userId));
    }

    @Test
    @DisplayName("findById method should return empty Optional if User not exists")
    void findById_shouldReturnEmptyOptional_whenThereIsNotAnyUserInTableWithEnteredUserId() {

        int userId = 100;

        assertEquals(Optional.empty(), userDao.findById(userId));
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
                .build(),
                User.builder()
                .withUserId(2)
                .withFirstName("Christopher")
                .withLastName("Garcia")
                .withEmail("christopher.garcia@gmail.com")
                .build(),
                User.builder()
                .withUserId(3)
                .withFirstName("Patricia")
                .withLastName("Garcia")
                .withEmail("patricia.garcia@gmail.com")
                .build(),
                User.builder()
                .withUserId(4)
                .withFirstName("Patricia")
                .withLastName("Jackson")
                .withEmail("patricia.jackson@gmail.com")
                .build(),
                User.builder()
                .withUserId(5)
                .withFirstName("William")
                .withLastName("Wilson")
                .withEmail("william.wilson@gmail.com")
                .build(),
                User.builder()
                .withUserId(6)
                .withFirstName("James")
                .withLastName("Williams")
                .withEmail("james.williams@gmail.com")
                .build(),
                User.builder()
                .withUserId(7)
                .withFirstName("Robert")
                .withLastName("Rodriguez")
                .withEmail("robert.rodriguez@gmail.com")
                .build(),
                User.builder()
                .withUserId(8)
                .withFirstName("John")
                .withLastName("Martinez")
                .withEmail("john.martinez@gmail.com")
                .build(),
                User.builder()
                .withUserId(9)
                .withFirstName("Karen")
                .withLastName("Garcia")
                .withEmail("karen.garcia@gmail.com")
                .build()));

        assertEquals(expectedUsers, userDao.findAll());
    }

    @Test
    @DisplayName("findAll method with pagination should return Users with defined offset and limit")
    void findAll_withPaginationShouldReturnDefinedListOfUsers_whenThereAreUsersInTableWithOffsetAndLimit() {

        int rowOffset = 2;
        int rowLimit = 2;
        List<User> expectedUsers = new ArrayList<User>(Arrays.asList(
                User.builder()
                .withUserId(3)
                .withFirstName("Patricia")
                .withLastName("Garcia")
                .withEmail("patricia.garcia@gmail.com")
                .build(),
                User.builder()
                .withUserId(4)
                .withFirstName("Patricia")
                .withLastName("Jackson")
                .withEmail("patricia.jackson@gmail.com")
                .build()));

        assertEquals(expectedUsers, userDao.findAll(rowOffset, rowLimit));
    }

    @Test
    @DisplayName("update method should update User in the table")
    void update_shouldUpdateUserInTable_whenEnteredDataIsCorrect() {

        int studentId = 3;
        String firstName = "James";
        String lastName = "Garcia";
        String email = "james.garcia@gmail.com";
        User updatedUser = User.builder()
                .withUserId(studentId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .build();
        Optional<User> expectedUser = Optional.of(
                User.builder()
                .withUserId(studentId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .build());

        userDao.update(updatedUser);

        assertEquals(expectedUser, userDao.findById(studentId));
    }

    @Test
    @DisplayName("deleteById method should delete User from the table")
    void deleteById_shouldDeleteUser_whenThereIsSomeUserInTableWithEnteredUserId() {

        int userId = 1;

        userDao.deleteById(userId);

        assertEquals(Optional.empty(), userDao.findById(userId));
    }
    
    @Test
    @DisplayName("findByEmail method should return User if User exists")
    void findByEmail_shouldReturnUser_whenThereIsSomeSUserInTableWithEnteredEmail() {

        String expectedEmail = "michael.thomas@gmail.com";

        assertEquals(expectedEmail, userDao.findByEmail(expectedEmail).get().getEmail());
    }
    
    @Test
    @DisplayName("findByEmail method should return empty Optional if User not exists")
    void findByEmail_shouldReturnEmptyOptional_whenThereIsNotAnyUserInTableWithEnteredEmail() {

        String email = "notexisting.email@gmail.com";

        assertEquals(Optional.empty(), userDao.findByEmail(email));
    }
}

package ua.prom.roboticsdmc.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import ua.prom.roboticsdmc.domain.Group;
import ua.prom.roboticsdmc.testcontainer.PostgresqlTestContainer;

@ActiveProfiles("test")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        GroupRepository.class }))
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
@DisplayName("GroupRepositoryTest")
class GroupRepositoryTest {
    
    @ClassRule
    public static PostgreSQLContainer<?> postgreSQLContainer = PostgresqlTestContainer.getInstance();
    
    @Autowired
    GroupRepository groupRepository;

    @Test
    @DisplayName("findGroupWithLessOrEqualsStudentQuantity method should return List of Groups")
    void findGroupWithLessOrEqualsStudentQuantity_shouldReturnListOfGroupsEachContainsDefinedStudentQuantity_whenThereIsSomeGroupWithOrMoreEnteredStudentQuantity() {

        int studentQuantity = 2;
        List<Group> expectedGroups = new ArrayList<Group>(Arrays.asList(
                Group.builder()
                .withGroupId(1)
                .withGroupName("YY-58")
                .build(), 
                Group.builder()
                .withGroupId(4)
                .withGroupName("FF-49")
                .build(),
                Group.builder()
                .withGroupId(5)
                .withGroupName("SR-71")
                .build()));

        assertEquals(expectedGroups, groupRepository.findGroupWithLessOrEqualsStudentQuantity(studentQuantity));
    }
}

package ua.prom.roboticsdmc.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.domain.Group;

@Component
@Log4j2
public class CustomizedGroupRepositoryImpl implements CustomizedGroupRepository {

    private static final String FIND_GROUP_WITH_STUDENT_QUANTITY_QUERY = "SELECT s.groupId, g.groupName, COUNT (s.userId) "
            + "FROM Student s JOIN Group g " + "ON s.groupId = g.groupId " + "GROUP BY s.groupId, g.groupName "
            + "HAVING COUNT (s.userId) <=:quantity " + "ORDER BY s.groupId ASC";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Group> findGroupWithLessOrEqualsStudentQuantity(Integer studentQuantity) {
        log.info("Find group with less or equals student quantity = " + studentQuantity);
        List<Group> groups = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<Object[]> results = entityManager.createQuery(FIND_GROUP_WITH_STUDENT_QUANTITY_QUERY)
                .setParameter("quantity", studentQuantity).getResultList();
        results.stream().forEach(r -> {
            Integer groupId = (Integer) r[0];
            String groupName = (String) r[1];
            Group group = Group.builder()
                    .withGroupId(groupId)
                    .withGroupName(groupName)
                    .build();
            groups.add(group);
        });
        return groups;
    }
}

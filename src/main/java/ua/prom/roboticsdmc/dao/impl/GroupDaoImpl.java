package ua.prom.roboticsdmc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.dao.GroupDao;
import ua.prom.roboticsdmc.domain.Group;

@Repository
@Log4j2
public class GroupDaoImpl extends AbstractCrudDaoImpl<Integer, Group> implements GroupDao {

    private static final String FIND_BY_ID_QUERY_HQL = "SELECT g FROM Group g WHERE g.groupId=:id";
    private static final String FIND_ALL_QUERY_HQL = "SELECT g FROM Group g ORDER BY g.groupId ASC";
    private static final String DELETE_BY_ID_QUERY_HQL = "DELETE FROM Group g WHERE g.groupId=:id";
    private static final String FIND_GROUP_WITH_STUDENT_QUANTITY_QUERY = "SELECT s.groupId, g.groupName, COUNT (s.userId) "
            + "FROM Student s JOIN Group g " + "ON s.groupId = g.groupId " + "GROUP BY s.groupId, g.groupName "
            + "HAVING COUNT (s.userId) <=:quantity " + "ORDER BY s.groupId ASC";

    @PersistenceContext
    private EntityManager entityManager;

    public GroupDaoImpl() {
        super(FIND_BY_ID_QUERY_HQL, FIND_ALL_QUERY_HQL, DELETE_BY_ID_QUERY_HQL);
    }

    @Override
    public List<Group> findGroupWithLessOrEqualsStudentQuantity(Integer studentQuantity) {
        log.info("Method start");
        log.info("Find group with less or equals student quantity = " + studentQuantity);
        List<Group> groups = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<Object[]> results = entityManager.createQuery(FIND_GROUP_WITH_STUDENT_QUANTITY_QUERY)
                .setParameter("quantity", studentQuantity).getResultList();
        results.stream().forEach((r) -> {
            Integer groupId = (Integer) r[0];
            String groupName = (String) r[1];
            Group group = Group.builder()
                    .withGroupId(groupId)
                    .withGroupName(groupName)
                    .build();
            groups.add(group);
        });
        log.info("Method end");
        return groups;
    }
}

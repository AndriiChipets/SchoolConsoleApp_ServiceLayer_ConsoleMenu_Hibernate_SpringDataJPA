package ua.prom.roboticsdmc.dao.impl;

import java.util.Optional;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.dao.UserDao;
import ua.prom.roboticsdmc.domain.User;

@Repository
@Log4j2
public class UserDaoImpl extends AbstractCrudDaoImpl<Integer, User> implements UserDao {

    private static final String FIND_BY_ID_QUERY_HQL = "SELECT u FROM User u WHERE u.userId=:id";
    private static final String FIND_BY_EMAIL_QUERY_HQL = "SELECT u FROM User u WHERE u.email=:email";
    private static final String FIND_ALL_QUERY_HQL = "SELECT u FROM User u ORDER BY u.userId=:id";
    private static final String DELETE_BY_ID_QUERY_HQL = "DELETE FROM User u WHERE u.userId=:id";
    private static final String CHECKING_EXISTENCE_TABLES_IN_SCHEMA_QUERY_SQL = "SELECT * FROM information_schema.tables where table_schema = 'school_app_schema' "
            + "and table_type = 'BASE TABLE'";

    @PersistenceContext
    private EntityManager entityManager;

    protected UserDaoImpl() {
        super(FIND_BY_ID_QUERY_HQL, FIND_ALL_QUERY_HQL, DELETE_BY_ID_QUERY_HQL);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        log.info("Method start");
        log.info("Find user by email = " + email);
        User user = null;
        try {
            user = entityManager.createQuery(FIND_BY_EMAIL_QUERY_HQL, User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            log.info("get User with email: " + user.getEmail());
        } catch (NoResultException e) {
            log.warn("User with email = " + email + " is absent");
            return Optional.empty();
        }
        log.info("Return user by email = " + email);
        log.info("Method end");
        return Optional.of(user);
    }

    @Override
    public boolean isAnyTableInDbSchema() {
        log.info("Method start");
        log.info("Check is any table exist in data base schema");
        log.info("Method end");
        return entityManager.createNativeQuery(CHECKING_EXISTENCE_TABLES_IN_SCHEMA_QUERY_SQL)
                .getResultList().isEmpty();
    }
}

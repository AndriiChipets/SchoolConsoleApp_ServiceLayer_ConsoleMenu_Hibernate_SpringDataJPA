package ua.prom.roboticsdmc.repository;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class CustomizedUserRepositoryImpl implements CustomizedUserRepository {

    private static final String CHECKING_EXISTENCE_TABLES_IN_SCHEMA_QUERY_SQL = "SELECT * FROM information_schema.tables where table_schema = 'school_app_schema' "
            + "and table_type = 'BASE TABLE'";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isAnyTableInDbSchema() {
        log.info("Check is any table exist in data base schema");
        return entityManager.createNativeQuery(CHECKING_EXISTENCE_TABLES_IN_SCHEMA_QUERY_SQL).getResultList().isEmpty();
    }
}

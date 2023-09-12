package ua.prom.roboticsdmc.dao.impl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.dao.CrudDao;

@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractCrudDaoImpl<ID, E> implements CrudDao<ID, E> {

    private final String findByIdQuery;
    private final String findAllQuery;
    private final String deleteByIdQuery;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(E entity) {
        log.info("Method start");
        log.info("Save " + entity);
        entityManager.persist(entity);
        log.info("Entity " + entity);
        log.info("Method end");
    }

    @Override
    @Transactional
    public void saveAll(List<E> entities) {
        log.info("Method start");
        int batchSize = entities.size() - 1;
        log.info("Save List of entities");
        for (int i = 0; i < entities.size(); i++) {
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
            entityManager.persist(entities.get(i));
        }
        log.info("List of entities saved");
        log.info("Method end");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<E> findById(ID id) {
        log.info("Method start");
        log.info("Find entity by ID = " + id);
        E entity = null;
        try {
            entity = (E) entityManager.createQuery(findByIdQuery).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            log.warn("Entity is absent in the table");
            return Optional.empty();
        }
        log.info("Return entity by ID = " + id);
        log.info("Method end");
        return Optional.of(entity);
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        log.info("Method start");
        log.info("Delete entity by ID = " + id);
        entityManager.createQuery(deleteByIdQuery).setParameter("id", id).executeUpdate();
        log.info("Entity by ID =" + id + "is deleted");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> findAll() {
        log.info("Method start");
        log.info("Method end");
        return entityManager.createQuery(findAllQuery).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> findAll(int rawOffset, int rawLimit) {
        log.info("Method start");
        log.info("Return List of entities with rawOffset = " + rawOffset + "and rawLimit = " + rawLimit);
        log.info("Method end");
        return entityManager.createQuery(findAllQuery).setFirstResult(rawOffset).setMaxResults(rawLimit)
                .getResultList();
    }

    @Override
    @Transactional
    public void update(E entity) {
        log.info("Method start");
        log.info("Update " + entity);
        entityManager.merge(entity);
        log.info(entity + " updated");
        log.info("Method end");
    }
}

package ua.prom.roboticsdmc.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.dao.CrudDao;

@Log4j2
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractCrudDaoImpl<ID, E> implements CrudDao<ID, E> {

    protected JdbcTemplate jdbcTemplate;
    private final String saveQuery;
    private final String findByIdQuery;
    private final String findAllQuery;
    private final String findAllPeginationQuery;
    private final String updateQuery;
    private final String deleteByIdQuery;

    @Override
    public void save(E entity) {
        log.trace("Save entity");
        jdbcTemplate.update(saveQuery, getEntityPropertiesToSave(entity));
        log.trace("Entity saved");
    }

    @Override
    public void saveAll(List<E> entities) {

        log.trace("Save List of entities");
        List<Object[]> batch = new ArrayList<>();
        for (E entity : entities) {
            Object[] values = getEntityPropertiesToSave(entity);
            batch.add(values);
        }
        jdbcTemplate.batchUpdate(saveQuery, batch);
        log.trace("List of entities saved");
    }

    @Override
    public Optional<E> findById(ID id) {
        log.trace("Find entity by ID = " + id);
        E entity = null;
        try {
            entity = jdbcTemplate.queryForObject(findByIdQuery, createRowMapper(), id);
        } catch (DataAccessException e) {
            log.warn("Entity is absent in the table");
            return Optional.empty();
        }
        log.trace("Return entity by ID = " + id);
        return Optional.of(entity);
    }

    @Override
    public void deleteById(ID id) {
        log.trace("Delete entity by ID = " + id);
        jdbcTemplate.update(deleteByIdQuery, id);
        log.trace("Entity by ID =" + id + "is deleted");
    }

    @Override
    public List<E> findAll() {
        return jdbcTemplate.query(findAllQuery, createRowMapper());
    }

    @Override
    public List<E> findAll(int rawOffset, int rawLimit) {
        log.trace("Return List of entities");
        return jdbcTemplate.query(findAllPeginationQuery, createRowMapper(), rawLimit, rawOffset);
    }

    @Override
    public void update(E entity) {
        log.trace("Update entity");
        jdbcTemplate.update(updateQuery, getEntityPropertiesToUpdate(entity));
        log.trace("Entity updated");
    }

    protected abstract RowMapper<E> createRowMapper();

    protected abstract Object[] getEntityPropertiesToSave(E entity);

    protected abstract Object[] getEntityPropertiesToUpdate(E entity);
}

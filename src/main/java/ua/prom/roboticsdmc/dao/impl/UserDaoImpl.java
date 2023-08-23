package ua.prom.roboticsdmc.dao.impl;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.prom.roboticsdmc.dao.UserDao;
import ua.prom.roboticsdmc.domain.User;

@Repository
public class UserDaoImpl extends AbstractCrudDaoImpl<Integer, User> implements UserDao {

    private static final String SAVE_QUERY = "INSERT INTO school_app_schema.users (first_name, last_name, email, password) VALUES (?,?,?,?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM school_app_schema.users WHERE user_id=?";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM school_app_schema.users WHERE email=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM school_app_schema.users ORDER BY user_id ASC";
    private static final String FIND_ALL_PAGINATION_QUERY = "SELECT * FROM school_app_schema.users ORDER BY user_id ASC LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE school_app_schema.users SET first_name=?, last_name=?, email=?, password=? WHERE user_id=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM school_app_schema.users WHERE user_id=?";
    private static final String CHECKING_EXISTENCE_TABLES_IN_SCHEMA_QUERY = "SELECT * FROM information_schema.tables where table_schema = 'school_app_schema' and table_type = 'BASE TABLE'";

    protected UserDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, FIND_ALL_PAGINATION_QUERY, UPDATE_QUERY,
                DELETE_BY_ID_QUERY);
    }

    @Override
    protected RowMapper<User> createRowMapper() {
        return (rs, rowNum) -> {
            return User.builder()
                    .withUserId(rs.getInt("user_id"))
                    .withFirstName(rs.getString("first_name"))
                    .withLastName(rs.getString("last_name"))
                    .withEmail(rs.getString("email"))
                    .withPassword(rs.getString("password"))
                    .build();
        };
    }

    @Override
    protected Object[] getEntityPropertiesToSave(User user) {
        return new Object[] { user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword() };
    }

    @Override
    protected Object[] getEntityPropertiesToUpdate(User user) {
        return new Object[] { user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(),
                user.getUserId() };
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(FIND_BY_EMAIL_QUERY, createRowMapper(), email);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    public boolean isAnyTableInDbSchema() {
        return jdbcTemplate.queryForList(CHECKING_EXISTENCE_TABLES_IN_SCHEMA_QUERY).isEmpty();
    }
}

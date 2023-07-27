package ua.prom.roboticsdmc.dao.impl;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.prom.roboticsdmc.dao.UserDao;
import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.domain.User;

@Repository
public class UserDaoImpl extends AbstractCrudDaoImpl<Integer, User> implements UserDao  {
    
    private static final String SAVE_QUERY = "INSERT INTO school_app_schema.user (first_name, last_name, email, password, repeatPassword) VALUES (?,?,?,?,?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM school_app_schema.user WHERE user_id=?";
    private static final String FIND_BY_ID_EMAIL = "SELECT * FROM school_app_schema.user WHERE email=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM school_app_schema.user ORDER BY user_id ASC";
    private static final String FIND_ALL_PAGINATION_QUERY = "SELECT * FROM school_app_schema.user ORDER BY user_id ASC LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE school_app_schema.user SET first_name=?, last_name=?, email=?, password=&, repeatPassword=? WHERE user_id=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM school_app_schema.user WHERE user_id=?";

    protected UserDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, FIND_ALL_PAGINATION_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(FIND_BY_ID_EMAIL, createRowMapper(), email);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    protected RowMapper<User> createRowMapper() {
        return (rs, rowNum) -> {
            return Student.builder()
                    .withUserId(rs.getInt("user_id"))
                    .withFirstName(rs.getString("first_name"))
                    .withLastName(rs.getString("last_name"))
                    .withEmail("email")
                    .withPassword("password")
                    .withRepeatPassword("repeatPassword")
                    .build();
        };
    }

    @Override
    protected Object[] getEntityPropertiesToSave(User user) {
        return new Object[] {
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRepeatPassword()};
    }

    @Override
    protected Object[] getEntityPropertiesToUpdate(User user) {
        return new Object[] {
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRepeatPassword()};
    }
}

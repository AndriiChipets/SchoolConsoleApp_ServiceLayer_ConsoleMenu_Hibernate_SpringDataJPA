package ua.prom.roboticsdmc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.prom.roboticsdmc.dao.StudentDao;
import ua.prom.roboticsdmc.domain.Student;

@Repository
public class StudentDaoImpl extends AbstractCrudDaoImpl<Integer, Student> implements StudentDao {

    private static final String SAVE_QUERY = "INSERT INTO school_app_schema.users (group_id, first_name, last_name, email, password) VALUES (?,?,?,?,?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM school_app_schema.users WHERE user_id=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM school_app_schema.users ORDER BY school_app_schema.users.user_id ASC";
    private static final String FIND_ALL_PAGINATION_QUERY = "SELECT * FROM school_app_schema.users ORDER BY user_id ASC LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE school_app_schema.users group_id=? WHERE user_id=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM school_app_schema.users WHERE user_id=?";
    private static final String DISTRIBUTE_STUDENTS_TO_GROUPS_QUERY = "UPDATE school_app_schema.users SET group_id=? WHERE user_id=?";
    private static final String FIND_STUDENTS_BY_COURSE_NAME_QUERY = "SELECT * FROM school_app_schema.users " 
            + "INNER JOIN school_app_schema.students_courses "
            + "ON school_app_schema.users.user_id = school_app_schema.students_courses.user_id "
            + "INNER JOIN school_app_schema.courses "
            + "ON school_app_schema.students_courses.course_id = school_app_schema.courses.course_id "
            + "WHERE school_app_schema.courses.course_name = ? "
            + "ORDER BY school_app_schema.users.user_id ASC";

    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, FIND_ALL_PAGINATION_QUERY, UPDATE_QUERY,
                DELETE_BY_ID_QUERY);
    }
    
    @Override
    protected RowMapper<Student> createRowMapper() {
        return (rs, rowNum) -> {
            return Student.builder()
                    .withUserId(rs.getInt("user_id"))
                    .withGroupId(rs.getInt("group_id"))
                    .withFirstName(rs.getString("first_name"))
                    .withLastName(rs.getString("last_name"))
                    .withEmail("email")
                    .withPassword("password")
                    .build();
        };
    }
    
    @Override
    protected Object[] getEntityPropertiesToSave(Student student) {
        return new Object[] { 
                student.getGroupId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPassword() };
    }

    @Override
    protected Object[] getEntityPropertiesToUpdate(Student student) {
        return new Object[] {
                student.getUserId(),
                student.getGroupId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPassword() };
    }
    
    protected Object[] getEntityPropertiesToUpdateGroup(Student student) {
        return new Object[] {
                student.getGroupId(),
                student.getUserId() };
    }

    @Override
    public List<Student> findStudentsByCourseName(String courseName) {
        return jdbcTemplate.query(FIND_STUDENTS_BY_COURSE_NAME_QUERY, createRowMapper(), courseName);
    }
    
    @Override
    public void distributeStudentsToGroups(List<Student> students) {
        
        List<Object[]> batch = new ArrayList<>();
        for (Student student : students) {
            Object[] values = getEntityPropertiesToUpdateGroup(student);
            batch.add(values);
        }
        jdbcTemplate.batchUpdate(DISTRIBUTE_STUDENTS_TO_GROUPS_QUERY, batch);
    }
    
    @Override
    public void addStudentToGroup(Integer groupId, Integer studentId) {
        jdbcTemplate.update(DISTRIBUTE_STUDENTS_TO_GROUPS_QUERY, groupId, studentId);
    }
}

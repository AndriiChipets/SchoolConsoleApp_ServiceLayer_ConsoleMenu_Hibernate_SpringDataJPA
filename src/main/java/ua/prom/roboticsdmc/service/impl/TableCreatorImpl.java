package ua.prom.roboticsdmc.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import ua.prom.roboticsdmc.anotation.DataGeneration;
import ua.prom.roboticsdmc.dao.exception.DataBaseSqlRuntimeException;
import ua.prom.roboticsdmc.service.TableCreator;

@DataGeneration
public class TableCreatorImpl implements TableCreator {

    private final JdbcTemplate jdbcTemplate;

    public TableCreatorImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createTables(String schemaFilePath) {

        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {

            ScriptRunner scriptRunner = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader(schemaFilePath));
            scriptRunner.runScript(reader);

        } catch (SQLException | IOException e) {
            throw new DataBaseSqlRuntimeException("Schema and tables are not created..", e);
        }
    }
}

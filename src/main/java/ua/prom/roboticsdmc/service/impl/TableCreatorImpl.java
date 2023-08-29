package ua.prom.roboticsdmc.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.anotation.DataGenerator;
import ua.prom.roboticsdmc.dao.exception.DataBaseSqlRuntimeException;
import ua.prom.roboticsdmc.service.TableCreator;

@Log4j2
@DataGenerator
@AllArgsConstructor
public class TableCreatorImpl implements TableCreator {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void createTables(String schemaFilePath) {
        log.info("Create tables");
        log.trace("Create connection");
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            log.trace("Connection created");
            log.trace("Create scriptRunner");
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            log.trace("ScriptRunner created");
            log.trace("Create reader to execute script");
            try (Reader reader = new BufferedReader(new FileReader(schemaFilePath))) {
                scriptRunner.runScript(reader);
                log.trace("Reader created, script executed");
            }

        } catch (SQLException | IOException e) {
            log.error("Schema and tables are not created.. " + e);
            throw new DataBaseSqlRuntimeException("Schema and tables are not created..", e);
        }
    }
}

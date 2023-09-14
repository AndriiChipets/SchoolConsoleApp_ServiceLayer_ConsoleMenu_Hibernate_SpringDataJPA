package ua.prom.roboticsdmc.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.anotation.DataGenerator;
import ua.prom.roboticsdmc.dao.exception.DataBaseSqlRuntimeException;
import ua.prom.roboticsdmc.service.TableCreator;

@Log4j2
@DataGenerator
@AllArgsConstructor
public class TableCreatorImpl implements TableCreator {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createTables(String schemaFilePath) {
        log.info("Create connection");
        EntityManagerFactoryInfo info = (EntityManagerFactoryInfo) entityManager.getEntityManagerFactory();
        try (Connection connection = info.getDataSource().getConnection()) {
            log.info("Connection created");
            log.info("Create scriptRunner");
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            log.info("ScriptRunner created");
            log.info("Create reader to execute script");
            try (Reader reader = new BufferedReader(new FileReader(schemaFilePath))) {
                scriptRunner.runScript(reader);
                log.info("Reader created, script executed");
            }
        } catch (SQLException | IOException e) {
            log.error("Schema and tables are not created.. " + e);
            throw new DataBaseSqlRuntimeException("Schema and tables are not created..", e);
        }
    }
}

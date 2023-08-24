package ua.prom.roboticsdmc.service.impl;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import ua.prom.roboticsdmc.dao.UserDao;
import ua.prom.roboticsdmc.service.TableCreator;

@Profile("!test")
@Component
@Order(1)
@AllArgsConstructor
public class ApplicationRunnerTableCreator implements ApplicationRunner {

    private static final String SCHEMA_FILE_PATH = "src/main/resources/sgl/schema.sql";
    private final TableCreator tableCreator;
    private final UserDao userDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (userDao.isAnyTableInDbSchema()) {
            tableCreator.createTables(SCHEMA_FILE_PATH);
        }
    }
}

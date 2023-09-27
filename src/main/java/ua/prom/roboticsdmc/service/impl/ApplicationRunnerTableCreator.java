package ua.prom.roboticsdmc.service.impl;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.controller.FrontController;
import ua.prom.roboticsdmc.repository.UserRepository;
import ua.prom.roboticsdmc.service.TableCreator;

@Profile("!test")
@Component
@AllArgsConstructor
@Log4j2
public class ApplicationRunnerTableCreator implements ApplicationRunner {

    private static final String SCHEMA_FILE_PATH = "src/main/resources/sgl/schema.sql";
    
    private final FrontController frontController;
    private final TableCreator tableCreator;
    private final TableFiller tableFiller;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.isAnyTableInDbSchema()) {
            log.info("Create tables and add data to tables");
            tableCreator.createTables(SCHEMA_FILE_PATH);
            log.info("Tables created");
            tableFiller.fillData();
            log.info("Data added");
        }
        frontController.run();
    }
}

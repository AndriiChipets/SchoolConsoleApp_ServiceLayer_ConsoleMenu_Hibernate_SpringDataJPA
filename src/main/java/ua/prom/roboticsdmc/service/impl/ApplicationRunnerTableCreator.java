package ua.prom.roboticsdmc.service.impl;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import ua.prom.roboticsdmc.service.TableCreator;

@Profile("!test")
@Component
@Order(1)
@AllArgsConstructor
public class ApplicationRunnerTableCreator implements ApplicationRunner {

    private final TableCreator tableCreator;
    private final String schemaFilePath = "src/main/resources/sgl/schema.sql";

    @Override
    public void run(ApplicationArguments args) throws Exception {

        tableCreator.createTables(schemaFilePath);
    }
}

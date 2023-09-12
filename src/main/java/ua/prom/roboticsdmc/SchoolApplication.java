package ua.prom.roboticsdmc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ua.prom.roboticsdmc.config.SchoolApplicationConfig;

@SpringBootApplication
public class SchoolApplication {

    public static void main(String[] args) {

        try (ConfigurableApplicationContext context = SpringApplication.run(SchoolApplicationConfig.class)) {
        }
    }
}

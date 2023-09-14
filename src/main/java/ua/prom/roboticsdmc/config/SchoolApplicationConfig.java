package ua.prom.roboticsdmc.config;

import java.util.Scanner;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.log4j.Log4j2;

@Configuration
@ComponentScan(basePackages = "ua.prom.roboticsdmc")
@PropertySource("database.properties")
@Log4j2
public class SchoolApplicationConfig {

    @Bean
    DataSource dataSource(
            @Value("${db.url}")String url,
            @Value("${db.user}") String user,
            @Value("${db.password}") String password, 
            @Value("${db.cachePrepStmts.param}") String cachePrepStmtsParam,
            @Value("${db.cachePrepStmts.value}") String cachePrepStmtsValue,
            @Value("${db.prepStmtCacheSize.param}") String prepStmtCacheSizeParam, 
            @Value("${db.prepStmtCacheSize.value}") String prepStmtCacheSizeValue,
            @Value("${db.prepStmtCacheSqlLimit.param}") String prepStmtCacheSqlLimitParam,
            @Value("${db.prepStmtCacheSqlLimit.value}") String prepStmtCacheSqlLimitValue,
            @Value("${db.maximumPoolSize}") int maximumPoolSize) {
        log.info("Create new DataSource");
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.addDataSourceProperty(cachePrepStmtsParam, cachePrepStmtsValue);
        config.addDataSourceProperty(prepStmtCacheSizeParam, prepStmtCacheSizeValue);
        config.addDataSourceProperty(prepStmtCacheSqlLimitParam, prepStmtCacheSqlLimitValue);
        config.setMaximumPoolSize(maximumPoolSize);
        log.info("New DataSource created");
        return new HikariDataSource(config);
    }

    @Bean
    public Scanner getScanner() {
        log.info("Create Scanner");
        return new Scanner(System.in);
    }
    
}

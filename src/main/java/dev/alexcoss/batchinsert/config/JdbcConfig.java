package dev.alexcoss.batchinsert.config;

import dev.alexcoss.batchinsert.service.CarJdbcService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("jdbc")
@RequiredArgsConstructor
public class JdbcConfig {
    private final CarJdbcService carJdbcService;

    @Value("${bath.csv-file-path}")
    private String csvFilePath;

    @Bean
    public CommandLineRunner jdbcRunner() {
        return args -> carJdbcService.batchInsert(csvFilePath);
    }
}

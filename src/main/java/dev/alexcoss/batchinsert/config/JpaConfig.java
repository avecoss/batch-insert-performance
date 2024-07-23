package dev.alexcoss.batchinsert.config;

import dev.alexcoss.batchinsert.service.CarSpringJpaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("jpa")
@RequiredArgsConstructor
public class JpaConfig {
    private final CarSpringJpaService carJpaService;

    @Value("${bath.csv-file-path}")
    private String csvFilePath;

    @Bean
    public CommandLineRunner jpaRunner() {
        return args -> carJpaService.batchInsert(csvFilePath);
    }
}

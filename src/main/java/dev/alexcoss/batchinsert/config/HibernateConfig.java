package dev.alexcoss.batchinsert.config;

import dev.alexcoss.batchinsert.service.CarHibernateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("hibernate")
@RequiredArgsConstructor
public class HibernateConfig {
    private final CarHibernateService carHibernateService;

    @Value("${bath.csv-file-path}")
    private String csvFilePath;

    @Bean
    public CommandLineRunner hibernateRunner() {
        return args -> carHibernateService.batchInsert(csvFilePath);
    }
}

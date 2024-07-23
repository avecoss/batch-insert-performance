package dev.alexcoss.batchinsert.service;

import dev.alexcoss.batchinsert.model.CarCsv;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarJdbcService {
    @Value("${bath.amount}")
    private static final int BATCH_AMOUNT = 100;
    private static final String INSERT_SQL = "INSERT INTO car (object_id, year, model) VALUES (?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;
    private final CarCsvReader carCsvReader;

    public void batchInsert(String filePath) {
        List<CarCsv> carCsvList = carCsvReader.getListOfCarsCsv(filePath);

        long startTime = System.currentTimeMillis();
        jdbcTemplate.batchUpdate(INSERT_SQL, carCsvList, BATCH_AMOUNT, (ps, carCsv) -> {
            ps.setString(1, carCsv.getObjectId());
            ps.setString(2, carCsv.getYear());
            ps.setString(3, carCsv.getModel());
        });
        long endTime = System.currentTimeMillis();

        System.out.println("JDBC Batch Insert Time: " + (endTime - startTime) + " ms");
    }
}

package dev.alexcoss.bathupdate.service;

import com.opencsv.bean.CsvToBeanBuilder;
import dev.alexcoss.bathupdate.model.CarCsv;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarJdbcService {
    private static final String INSERT_SQL = "INSERT INTO car.car (object_id, year, model) VALUES (?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    public void batchInsert(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            List<CarCsv> carCsvList = new CsvToBeanBuilder<CarCsv>(reader)
                    .withType(CarCsv.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();

            long startTime = System.currentTimeMillis();
            jdbcTemplate.batchUpdate(INSERT_SQL, carCsvList, 100, (ps, carCsv) -> {
                ps.setString(1, carCsv.getObjectId());
                ps.setString(2, carCsv.getYear());
                ps.setString(3, carCsv.getModel());
            });
            long endTime = System.currentTimeMillis();

            System.out.println("JDBC Batch Insert Time: " + (endTime - startTime) + " ms");
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
    }
}

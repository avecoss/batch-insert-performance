package dev.alexcoss.bathupdate.service;

import com.opencsv.bean.CsvToBeanBuilder;
import dev.alexcoss.bathupdate.model.Car;
import dev.alexcoss.bathupdate.model.CarCsv;
import dev.alexcoss.bathupdate.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarJpaService {

    private final CarRepository carRepository;

    @Transactional
    public void batchInsert(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            List<CarCsv> carCsvList = new CsvToBeanBuilder<CarCsv>(reader)
                    .withType(CarCsv.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();

            List<Car> cars = carCsvList.stream()
                    .map(csv -> Car.builder()
                            .objectId(csv.getObjectId())
                            .year(csv.getYear())
                            .carModel(csv.getModel())
                            .build())
                    .toList();

            long startTime = System.currentTimeMillis();
            carRepository.saveAll(cars);
            long endTime = System.currentTimeMillis();

            System.out.println("JPA Batch Insert Time: " + (endTime - startTime) + " ms");
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
    }
}

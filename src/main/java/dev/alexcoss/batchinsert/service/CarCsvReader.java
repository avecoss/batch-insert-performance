package dev.alexcoss.batchinsert.service;

import com.opencsv.bean.CsvToBeanBuilder;
import dev.alexcoss.batchinsert.model.Car;
import dev.alexcoss.batchinsert.model.CarCsv;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Component
public class CarCsvReader {

    public List<Car> getListOfCars(String filePath) {
        List<CarCsv> carCsvList = getListOfCarsCsv(filePath);

        return carCsvList.stream()
            .map(csv -> Car.builder()
                .objectId(csv.getObjectId())
                .year(csv.getYear())
                .carModel(csv.getModel())
                .build())
            .toList();
    }

    public List<CarCsv> getListOfCarsCsv(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            return new CsvToBeanBuilder<CarCsv>(reader)
                .withType(CarCsv.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
                .parse();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
    }
}

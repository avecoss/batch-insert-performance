package dev.alexcoss.batchinsert.service;

import dev.alexcoss.batchinsert.model.Car;
import dev.alexcoss.batchinsert.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarSpringJpaService {
    private final CarRepository carRepository;
    private final CarCsvReader carCsvReader;

    @Transactional
    public void batchInsert(String filePath) {
        List<Car> cars = carCsvReader.getListOfCars(filePath);

        long startTime = System.currentTimeMillis();
        carRepository.saveAllAndFlush(cars);
        long endTime = System.currentTimeMillis();

        System.out.println("JPA Batch Insert Time: " + (endTime - startTime) + " ms");
    }
}

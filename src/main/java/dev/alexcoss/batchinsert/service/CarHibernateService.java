package dev.alexcoss.batchinsert.service;

import dev.alexcoss.batchinsert.model.Car;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarHibernateService {
    @Value("${bath.amount}")
    private static final int BATCH_AMOUNT = 100;

    @PersistenceContext
    private EntityManager entityManager;
    private final CarCsvReader carCsvReader;

    @Transactional
    public void batchInsert(String filePath) {
        List<Car> cars = carCsvReader.getListOfCars(filePath);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < cars.size(); i++) {
            entityManager.persist(cars.get(i));
            if (i % BATCH_AMOUNT == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Hibernate (EntityManager) Batch Insert Time: " + (endTime - startTime) + " ms");
    }
}

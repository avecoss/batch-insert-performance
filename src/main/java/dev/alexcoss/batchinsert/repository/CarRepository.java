package dev.alexcoss.batchinsert.repository;

import dev.alexcoss.batchinsert.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
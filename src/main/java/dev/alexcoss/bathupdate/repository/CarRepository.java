package dev.alexcoss.bathupdate.repository;

import dev.alexcoss.bathupdate.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}

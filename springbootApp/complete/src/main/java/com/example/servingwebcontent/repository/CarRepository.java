package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, String> {
    @Query("SELECT c FROM Car c WHERE c.importDate = :importDate AND c.status = :status")
    List<Car> findByImportDateAndStatus(Date importDate, String status);

    @Query("SELECT c FROM Car c WHERE c.brand LIKE %:keyword% OR c.model LIKE %:keyword%")
    List<Car> findByBrandOrModel(String keyword);

    @Query("SELECT c FROM Car c WHERE c.year = :year")
    List<Car> findByYear(int year);

    @Query("SELECT c FROM Car c WHERE c.price BETWEEN :minPrice AND :maxPrice")
    List<Car> findByPriceRange(double minPrice, double maxPrice);

    @Query("SELECT c FROM Car c WHERE c.status = :status")
    List<Car> findByStatus(String status);
}
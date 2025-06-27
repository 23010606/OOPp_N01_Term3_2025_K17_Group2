package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, String> {
    // Tìm kiếm theo brand hoặc model (phân trang)
    Page<Car> findByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(String brand, String model, Pageable pageable);

    // Lọc theo importDate và status (phân trang)
    Page<Car> findByImportDateAndStatus(Date importDate, String status, Pageable pageable);

    // Lọc theo year (phân trang)
    Page<Car> findByYear(int year, Pageable pageable);

    // Lọc theo khoảng giá (phân trang)
    Page<Car> findByPriceBetween(double minPrice, double maxPrice, Pageable pageable);

    // Lọc theo status (phân trang)
    Page<Car> findByStatus(String status, Pageable pageable);

    // Lọc theo số lượng (còn hàng > 0, sắp hết < 5, hết hàng = 0)
    Page<Car> findByQuantityGreaterThan(int quantity, Pageable pageable);
    Page<Car> findByQuantityLessThan(int quantity, Pageable pageable);
    Page<Car> findByQuantityEquals(int quantity, Pageable pageable);

    // Phương thức không phân trang (nếu cần)
    List<Car> findByStatus(String status);
    List<Car> findByImportDateAndStatus(Date importDate, String status);
}
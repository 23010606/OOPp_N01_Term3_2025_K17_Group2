package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Car;
import com.example.servingwebcontent.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CarList {
    @Autowired
    private CarRepository carRepository;

    public void addCar(Car car) {
        if (carRepository.existsById(car.getCarId())) {
            throw new IllegalArgumentException("Car ID " + car.getCarId() + " already exists!");
        }
        if (car.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative!");
        }
        if (car.getYear() < 1900) {
            throw new IllegalArgumentException("Year must be at least 1900!");
        }
        if (car.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative!");
        }
        carRepository.save(car);
    }

    public Page<Car> getAllCarsPage(PageRequest pageRequest) {
        return carRepository.findAll(pageRequest);
    }

    public void updateCar(String carId, String brand, String model, int year, double price, String status, Date importDate, int quantity) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if (carOpt.isEmpty()) {
            throw new IllegalArgumentException("Car with ID " + carId + " not found!");
        }
        Car car = carOpt.get();
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative!");
        }
        if (year < 1900) {
            throw new IllegalArgumentException("Year must be at least 1900!");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative!");
        }
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setPrice(price);
        car.setStatus(status);
        car.setImportDate(importDate);
        car.setQuantity(quantity);
        carRepository.save(car);
    }

    public void deleteCar(String carId) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if (carOpt.isEmpty()) {
            throw new IllegalArgumentException("Car with ID " + carId + " not found!");
        }
        carRepository.deleteById(carId);
    }

    public List<Car> getAvailableCarsByImportDate(Date importDate) {
        return carRepository.findByImportDateAndStatus(importDate, "Available");
    }

    public Car findCar(String carId) {
        Optional<Car> carOpt = carRepository.findById(carId);
        return carOpt.orElse(null);
    }

    public Page<Car> searchCarsByBrandOrModelPage(String keyword, Pageable pageable) {
        return carRepository.findByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(keyword, keyword, pageable);
    }

    public Page<Car> getCarsByYearPage(int year, PageRequest pageRequest) {
        if (year < 1900) {
            throw new IllegalArgumentException("Year must be at least 1900!");
        }
        return carRepository.findByYear(year, pageRequest);
    }

    public Page<Car> getCarsByPriceRangePage(double minPrice, double maxPrice, PageRequest pageRequest) {
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative!");
        }
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("Min price cannot be greater than max price!");
        }
        return carRepository.findByPriceBetween(minPrice, maxPrice, pageRequest);
    }

    public Page<Car> getCarsByStatusPage(String status, PageRequest pageRequest) {
        if (!("Available".equalsIgnoreCase(status) || "Sold".equalsIgnoreCase(status) || "Reserved".equalsIgnoreCase(status) || "OutOfStock".equalsIgnoreCase(status))) {
            throw new IllegalArgumentException("Invalid status! Must be 'Available', 'Sold', 'Reserved', or 'OutOfStock'.");
        }
        return carRepository.findByStatus(status, pageRequest);
    }

    public Page<Car> getCarsByQuantityStatusPage(String quantityStatus, PageRequest pageRequest) {
        if ("LowStock".equalsIgnoreCase(quantityStatus)) {
            return carRepository.findByQuantityLessThan(5, pageRequest); // Sắp hết (< 5)
        } else if ("OutOfStock".equalsIgnoreCase(quantityStatus)) {
            return carRepository.findByQuantityEquals(0, pageRequest); // Hết hàng
        } else if ("InStock".equalsIgnoreCase(quantityStatus)) {
            return carRepository.findByQuantityGreaterThan(0, pageRequest); // Còn hàng
        }
        throw new IllegalArgumentException("Invalid quantity status! Must be 'LowStock', 'OutOfStock', or 'InStock'.");
    }

    public List<Car> getAvailableCars() {
        return carRepository.findByStatus("Available");
    }
}
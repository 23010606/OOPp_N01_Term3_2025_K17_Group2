package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Car;
import com.example.servingwebcontent.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public void updateCar(String carId, String brand, String model, int year, double price, String status, Date importDate) {
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
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setPrice(price);
        car.setStatus(status);
        car.setImportDate(importDate);
        carRepository.save(car);
    }

    public void deleteCar(String carId) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if (carOpt.isEmpty()) {
            throw new IllegalArgumentException("Car with ID " + carId + " not found!");
        }
        Car car = carOpt.get();
        if ("Sold".equalsIgnoreCase(car.getStatus())) {
            throw new IllegalStateException("Cannot delete sold car!");
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

    public List<Car> searchCarsByBrandOrModel(String keyword) {
        return carRepository.findByBrandOrModel(keyword);
    }

    public List<Car> getCarsByYear(int year) {
        if (year < 1900) {
            throw new IllegalArgumentException("Year must be at least 1900!");
        }
        return carRepository.findByYear(year);
    }

    public List<Car> getCarsByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative!");
        }
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("Min price cannot be greater than max price!");
        }
        return carRepository.findByPriceRange(minPrice, maxPrice);
    }

    // Thêm: Lọc xe theo trạng thái
    public List<Car> getCarsByStatus(String status) {
        if (!("Available".equalsIgnoreCase(status) || "Sold".equalsIgnoreCase(status) || "Reserved".equalsIgnoreCase(status))) {
            throw new IllegalArgumentException("Invalid status! Must be 'Available', 'Sold', or 'Reserved'.");
        }
        return carRepository.findByStatus(status);
    }
}
package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Car;
import com.example.servingwebcontent.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
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
        carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public void updateCar(String carId, String brand, String model, double price) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if (carOpt.isEmpty()) {
            throw new IllegalArgumentException("Car with ID " + carId + " not found!");
        }
        Car car = carOpt.get();
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative!");
        }
        car.setBrand(brand);
        car.setModel(model);
        car.setPrice(price);
        carRepository.save(car);
    }

    public void deleteCar(String carId) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if (carOpt.isEmpty()) {
            throw new IllegalArgumentException("Car with ID " + carId + " not found!");
        }
        Car car = carOpt.get();
        if (car.getStatus().equals("Sold")) {
            throw new IllegalStateException("Cannot delete sold car!");
        }
        carRepository.deleteById(carId);
    }

    public List<Car> getAvailableCarsByImportDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date importDate = sdf.parse(dateStr);
            return carRepository.findAll().stream()
                    .filter(car -> car.getStatus().equals("Available") && 
                           sdf.format(car.getImportDate()).equals(sdf.format(importDate)))
                    .toList();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format! Please use dd/MM/yyyy.");
        }
    }

    public Car findCar(String carId) {
        Optional<Car> carOpt = carRepository.findById(carId);
        return carOpt.orElse(null);
    }
}
package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarManager {
    private List<Car> cars;

    public CarManager() {
        cars = new ArrayList<>();
    }

    // CRUD Methods
    public void addCar(Car car) {
        if (findCar(car.getCarId()) != null) {
            System.out.println("Error: Car ID " + car.getCarId() + " already exists!");
            return;
        }
        cars.add(car);
    }

    public void displayCars() {
        if (cars.isEmpty()) {
            System.out.println("No cars found!");
            return;
        }
        System.out.println("\nCar List:");
        for (Car car : cars) {
            System.out.println("ID: " + car.getCarId() + ", Brand: " + car.getBrand() + 
                               ", Model: " + car.getModel() + ", Price: " + car.getPrice() + 
                               ", Status: " + car.getStatus() + ", Import Date: " + car.getImportDate());
        }
    }

    public void updateCar(String carId, String brand, String model, double price) {
        Car car = findCar(carId);
        if (car == null) {
            System.out.println("Error: Car with ID " + carId + " not found!");
            return;
        }
        if (price < 0) {
            System.out.println("Error: Price cannot be negative!");
            return;
        }
        car.setBrand(brand);
        car.setModel(model);
        car.setPrice(price);
        System.out.println("Updated car: ID: " + car.getCarId() + ", Brand: " + car.getBrand() + 
                           ", Model: " + car.getModel() + ", Price: " + car.getPrice());
    }

    public void deleteCar(String carId) {
        Car car = findCar(carId);
        if (car == null) {
            System.out.println("Error: Car with ID " + carId + " not found!");
            return;
        }
        if (car.getStatus().equals("Sold")) {
            System.out.println("Error: Cannot delete sold car!");
            return;
        }
        cars.remove(car);
        System.out.println("Deleted car with ID: " + carId);
    }

    // Method 1: Display available cars by import date
    public void displayAvailableCarsByImportDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date importDate = sdf.parse(dateStr);
            boolean found = false;
            System.out.println("\nAvailable Cars Imported on " + dateStr + ":");
            for (Car car : cars) {
                if (car.getStatus().equals("Available") && 
                    sdf.format(car.getImportDate()).equals(sdf.format(importDate))) {
                    System.out.println("ID: " + car.getCarId() + ", Brand: " + car.getBrand() + 
                                       ", Model: " + car.getModel() + ", Price: " + car.getPrice());
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No available cars found for this date!");
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid date format! Please use dd/MM/yyyy.");
        }
    }

    // Helper Method
    public Car findCar(String carId) {
        for (Car car : cars) {
            if (car.getCarId().equals(carId)) {
                return car;
            }
        }
        return null;
    }
}
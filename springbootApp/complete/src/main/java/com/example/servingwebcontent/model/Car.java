package com.example.servingwebcontent.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Car {
    @Id
    private String carId;
    private String brand;
    private String model;
    private int year;
    private double price;
    private String status; // Available, Sold, Reserved
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date importDate;

    public Car() {}

    public Car(String carId, String brand, String model, int year, double price, String status, Date importDate) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.status = status;
        this.importDate = importDate;
    }

    // Getters and Setters
    public String getCarId() { return carId; }
    public void setCarId(String carId) { this.carId = carId; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) {
        if (year < 1900) throw new IllegalArgumentException("Year must be at least 1900!");
        this.year = year;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative!");
        this.price = price;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getImportDate() { return importDate; }
    public void setImportDate(Date importDate) { this.importDate = importDate; }

    // Methods
    public void sellCar() {
        if (!"Available".equalsIgnoreCase(status)) {
            throw new IllegalStateException("Car is not available for sale!");
        }
        this.status = "Sold";
    }
    public boolean isAvailable() {
        return "Available".equalsIgnoreCase(status);
    }

    public String getCarDetails() {
        return "ID: " + carId +
               ", Brand: " + brand +
               ", Model: " + model +
               ", Year: " + year +
               ", Price: " + price + " VND" +
               ", Status: " + status +
               ", Import Date: " + importDate;
    }
}
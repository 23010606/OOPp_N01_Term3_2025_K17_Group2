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
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date importDate;
    private int quantity; // Số lượng xe trong kho

    // Getters and Setters
    public String getCarId() { return carId; }
    public void setCarId(String carId) { this.carId = carId; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getImportDate() { return importDate; }
    public void setImportDate(Date importDate) { this.importDate = importDate; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Giảm số lượng xe khi bán
    public void sellCar() {
        if (this.quantity > 0) {
            this.quantity--;
        }
    }
}
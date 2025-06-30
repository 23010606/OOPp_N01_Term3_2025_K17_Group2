package com.example.servingwebcontent.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Car {
    @Id
    @NotBlank(message = "Car ID is required")
    private String carId;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Model is required")
    private String model;

    @Min(value = 1900, message = "Year must be >= 1900")
    private int year;

    @Positive(message = "Price must be positive")
    private double price;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Import date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date importDate;

    @Min(value = 0, message = "Quantity must be >= 0")
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

    // Giảm số lượng xe khi bán, có bắt lỗi
    public void sellCar() {
        try {
            if (this.quantity > 0) {
                this.quantity--;
            } else {
                throw new IllegalStateException("Số lượng xe không đủ để bán!");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi bán xe: " + e.getMessage());
        }
    }
}
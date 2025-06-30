package model;

import java.util.Date;

public class Car {
    private String carId;
    private String brand;
    private String model;
    private double price;
    private String status;
    private Date importDate;

    // Constructor
    public Car(String carId, String brand, String model, double price, String status, Date importDate) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
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
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getImportDate() { return importDate; }
    public void setImportDate(Date importDate) { this.importDate = importDate; }

    // Method to update status to Sold
    public void sellCar() {
        this.status = "Sold";
    }
}
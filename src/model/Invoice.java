package model;

import java.util.Date;

public class Invoice {
    private String invoiceId;
    private String customerId;
    private String carId;
    private Date date;
    private double totalAmount;

    // Constructor
    public Invoice(String invoiceId, Customer customer, Car car) {
        this.invoiceId = invoiceId;
        this.customerId = customer.getCustomerId();
        this.carId = car.getCarId();
        this.date = new Date();
        this.totalAmount = car.getPrice();
    }

    // Getters
    public String getInvoiceId() {
        return invoiceId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCarId() {
        return carId;
    }

    public Date getDate() {
        return date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getInvoiceDetails() {
        return "Invoice ID: " + invoiceId + ", Customer ID: " + customerId + 
               ", Car ID: " + carId + ", Date: " + date + ", Total: " + totalAmount;
    }
}
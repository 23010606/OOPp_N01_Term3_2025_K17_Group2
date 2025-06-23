package com.example.servingwebcontent.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Invoice {
    @Id
    private String invoiceId;

    private String carId;
    private Date date;
    private double totalAmount;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Invoice() {}

    public Invoice(String invoiceId, Customer customer, String carId, double totalAmount) {
        this.invoiceId = invoiceId;
        this.customer = customer;
        this.carId = carId;
        this.date = new Date();
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public String getInvoiceId() { return invoiceId; }
    public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }
    public String getCarId() { return carId; }
    public void setCarId(String carId) { this.carId = carId; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public String getCustomerId() {
        return customer != null ? customer.getCustomerId() : null; // Giả định Customer có getCustomerId()
    }

    public String getInvoiceDetails() {
        return "Invoice ID: " + invoiceId +
               ", Customer ID: " + (customer != null ? customer.getCustomerId() : "N/A") +
               ", Car ID: " + carId +
               ", Date: " + date +
               ", Total: " + totalAmount;
    }
}
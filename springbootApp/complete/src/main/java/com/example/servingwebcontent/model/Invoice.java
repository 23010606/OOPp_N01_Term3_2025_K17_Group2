package com.example.servingwebcontent.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
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

    public Invoice(String invoiceId, Customer customer, Car car) {
        this.invoiceId = invoiceId;
        this.customer = customer;
        this.carId = car.getCarId();
        this.date = new Date();
        this.totalAmount = car.getPrice();
    }

    public String getInvoiceId(){ 
        return invoiceId; 
    }
    public void setInvoiceId(String invoiceId){
         this.invoiceId = invoiceId; 
        }
    public String getCarId(){
         return carId; 
        }
    public void setCarId(String carId){
         this.carId = carId; 
        }
    public Date getDate(){
         return date; 
        }
    public void setDate(Date date){
         this.date = date; 
        }
    public double getTotalAmount(){
         return totalAmount; 
        }
    public void setTotalAmount(double totalAmount){
         this.totalAmount = totalAmount; 
        }
    public Customer getCustomer(){
         return customer; 
        }
    public void setCustomer(Customer customer){
         this.customer = customer; 
        }

    public String getCustomerId(){
         return customer != null ? customer.getId() : null; 
        }

    public String getInvoiceDetails() {
        return "Invoice ID: " + invoiceId +
               ", Customer ID: " + getCustomerId() +
               ", Car ID: " + carId +
               ", Date: " + date +
               ", Total: " + totalAmount;
    }
}
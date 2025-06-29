package com.example.servingwebcontent.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private double amount;

    public Payment() {}

    public Payment(Date date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public Long getId() { return id; }
    public Date getDate() { return date; }
    public double getAmount() { return amount; }
    public void setDate(Date date) { this.date = date; }
    public void setAmount(double amount) { this.amount = amount; }
}
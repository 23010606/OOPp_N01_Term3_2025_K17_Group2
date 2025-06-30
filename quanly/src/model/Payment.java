package model;

import java.util.Date;

public class Payment {
    private Long id; // Có thể không cần nếu không dùng database, nhưng giữ lại để đồng bộ
    private Date date;
    private double amount;

    public Payment() {}

    public Payment(Date date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
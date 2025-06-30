package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Invoice {
    private String invoiceId;
    private Car car;
    private Customer customer;
    private Date date;
    private double totalAmount;
    private PaymentStatus paymentStatus;
    private List<Payment> paymentHistory = new ArrayList<>();
    private String note;
    private Date pickupDate;

    public Invoice() {}

    public Invoice(String invoiceId, Customer customer, Car car, double totalAmount) {
        if (totalAmount <= 0) {
            throw new IllegalArgumentException("Total amount must be positive!");
        }
        this.invoiceId = invoiceId;
        this.customer = customer;
        this.car = car;
        this.date = new Date();
        this.totalAmount = totalAmount;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    // Getters and Setters
    public String getInvoiceId() { return invoiceId; }
    public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }
    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Date getDate() { return date; }
    public void setDate(Date date) {
        if (date == null || date.after(new Date())) {
            throw new IllegalArgumentException("Date must be in the past or present!");
        }
        this.date = date;
    }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) {
        if (totalAmount <= 0) {
            throw new IllegalArgumentException("Total amount must be positive!");
        }
        this.totalAmount = totalAmount;
    }
    public String getCustomerId() { return customer != null ? customer.getId() : null; }
    public String getCarId() { return car != null ? car.getCarId() : null; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
    public List<Payment> getPaymentHistory() { return paymentHistory; }
    public void setPaymentHistory(List<Payment> paymentHistory) { this.paymentHistory = paymentHistory; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Date getPickupDate() { return pickupDate; }
    public void setPickupDate(Date pickupDate) { this.pickupDate = pickupDate; }

    // Business methods
    public boolean isValid() {
        return customer != null && car != null && totalAmount > 0 && date != null;
    }

    public void updateTotalAmount(double newAmount) {
        if (newAmount <= 0) {
            throw new IllegalArgumentException("Total amount must be positive!");
        }
        this.totalAmount = newAmount;
        if (car != null) {
            car.setStatus("Sold");
        }
    }

    public void addPayment(double amount, Date paymentDate) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive!");
        }
        Payment payment = new Payment(paymentDate, amount);
        paymentHistory.add(payment);
        if (totalAmount <= calculateTotalPaid()) {
            this.paymentStatus = PaymentStatus.PAID;
        }
    }

    public double calculateTotalPaid() {
        return paymentHistory.stream().mapToDouble(Payment::getAmount).sum();
    }

    public String getInvoiceDetails() {
        return "Invoice ID: " + invoiceId +
               ", Customer ID: " + (customer != null ? customer.getId() : "N/A") +
               ", Car ID: " + (car != null ? car.getCarId() : "N/A") +
               ", Date: " + (date != null ? date : "N/A") +
               ", Total: " + totalAmount + " VND" +
               ", Status: " + paymentStatus +
               ", Paid: " + calculateTotalPaid() + " VND";
    }
}
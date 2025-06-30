package com.example.servingwebcontent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Customer {
    @Id
    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email is invalid")
    private String email;

    @Pattern(regexp = "\\d{9,11}", message = "Phone number must be 9-11 digits")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    private String address;

    @Temporal(TemporalType.DATE) // Thêm annotation này để lưu ngày đúng chuẩn JPA
    private Date registrationDate;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true) // Thêm orphanRemoval
    private List<Invoice> purchaseHistory;

    public Customer() {
        this.purchaseHistory = new ArrayList<>();
    }

    public Customer(String id, String name, String email, String phoneNumber, String address, Date registrationDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.registrationDate = registrationDate;
        this.purchaseHistory = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Date getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(Date registrationDate) { this.registrationDate = registrationDate; }
    public List<Invoice> getPurchaseHistory() { return purchaseHistory; }
    public void setPurchaseHistory(List<Invoice> purchaseHistory) { this.purchaseHistory = purchaseHistory; }

    // Methods
    public void addPurchase(Invoice invoice) {
        if (invoice != null && !purchaseHistory.contains(invoice)) { // Tránh trùng lặp
            purchaseHistory.add(invoice);
            invoice.setCustomer(this); // Đảm bảo mối quan hệ hai chiều
        }
    }
    public void deletePurchase(String invoiceId) {
        purchaseHistory.removeIf(invoice -> invoice != null && invoice.getInvoiceId().equals(invoiceId));
    }
    public String getCustomerInfo() {
        return "ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone: " + phoneNumber + ", Address: " + address +
               ", Registration Date: " + (registrationDate != null ? registrationDate : "N/A");
    }
    public double getTotalPurchaseAmount() {
        return purchaseHistory.stream()
                .filter(invoice -> invoice != null)
                .mapToDouble(Invoice::getTotalAmount)
                .sum();
    }
}
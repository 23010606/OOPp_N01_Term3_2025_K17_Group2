package model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerId;
    private String name;
    private String phone;
    private String address;
    private List<Invoice> purchaseHistory;

    // Constructor
    public Customer(String customerId, String name, String phone, String address) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.purchaseHistory = new ArrayList<>();
    }

    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Invoice> getPurchaseHistory() {
        return purchaseHistory;
    }

    // Methods
    public void addPurchase(Invoice invoice) {
        purchaseHistory.add(invoice);
    }

    public void deletePurchase(String invoiceId) {
        purchaseHistory.removeIf(invoice -> invoice.getInvoiceId().equals(invoiceId));
    }

    public String getCustomerInfo() {
        return "ID: " + customerId + ", Name: " + name + ", Phone: " + phone + ", Address: " + address;
    }
}
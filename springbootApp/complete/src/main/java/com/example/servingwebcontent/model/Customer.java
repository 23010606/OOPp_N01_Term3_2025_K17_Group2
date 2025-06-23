package com.example.servingwebcontent.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    @Transient
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Invoice> purchaseHistory;
    
    public Customer() {}
    

    // Constructor đầy đủ
    public Customer(String id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phone; // Sửa lại dòng này
        this.address = address;
        this.purchaseHistory = new ArrayList<>();
    }

    // Getters and Setters
    public String getId(){ 
        return id;
    }
    public void setId(String id){ 
        this.id = id;
    }
    public String getName(){ 
        return name;
    }
    public void setName(String name){ 
        this.name = name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){ 
        this.email = email;
    }
    public String getPhoneNumber(){ 
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){ 
        this.phoneNumber = phoneNumber;
    }
    public String getAddress(){ 
        return address;
    }
    public void setAddress(String address){ 
        this.address = address;
    }
    public List<Invoice> getPurchaseHistory(){ 
        return purchaseHistory;
    }

    // Methods
    public void addPurchase(Invoice invoice){ 
        purchaseHistory.add(invoice);
    }
    public void deletePurchase(String invoiceId){ 
        purchaseHistory.removeIf(invoice -> invoice.getInvoiceId().equals(invoiceId));
    }
    public String getCustomerInfo() {
        return "ID: " + id +
               ", Name: " + name +
               ", Email: " + email +
               ", Phone: " + phoneNumber +
               ", Address: " + address;
    }
      public double getTotalPurchaseAmount() {
        return purchaseHistory.stream()
                .mapToDouble(Invoice::getTotalAmount)
                .sum();
    }
    public String getCustomerId(){ 
        return id; 
    }
    public String getPhone(){ 
        return phoneNumber;
    }
    public void setPhone(String phone){ 
        this.phoneNumber = phone; 
    }
}
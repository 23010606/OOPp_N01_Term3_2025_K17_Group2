package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.service.CustomerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {
    private final CustomerList customerList;

    @Autowired
    public CustomerController(CustomerList customerList) {
        this.customerList = customerList;
    }

    @GetMapping("/customers")
    public String showCustomers(Model model) {
        model.addAttribute("customers", customerList.getAllCustomers());
        return "customer/customer-list";
    }

    @PostMapping("/customers/add")
    public String addCustomer(@RequestParam String id, @RequestParam String name,
                             @RequestParam String email, @RequestParam String phoneNumber, @RequestParam String address, Model model) {
        try {
            Customer customer = new Customer(id, name, email, phoneNumber, address);
            customerList.addCustomer(customer);
            model.addAttribute("message", "Customer added successfully!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("customers", customerList.getAllCustomers());
        return "customer/customers";
    }
}
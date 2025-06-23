package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Car;
import com.example.servingwebcontent.service.CarList;
import com.example.servingwebcontent.service.CustomerList;
import com.example.servingwebcontent.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CarController {
    private final CarList carList;
    private final CustomerList customerList;

    @Autowired
    public CarController(CarList carList, CustomerList customerList) {
        this.carList = carList;
        this.customerList = customerList;
    }

    @GetMapping("/cars")
    public String showCars(Model model) {
        model.addAttribute("cars", carList.getAllCars());
        return "car/car-list";
    }

    @PostMapping("/cars/add")
    public String addCar(@RequestParam String carId, @RequestParam String brand, @RequestParam String carModel,
                         @RequestParam double price, @RequestParam String status, @RequestParam String importDateStr,
                         Model model) {
        try {
            // Parse importDate (simple parsing, enhance if needed)
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            java.util.Date importDate = sdf.parse(importDateStr);
            Car car = new Car(carId, brand, carModel, price, status, importDate);
            carList.addCar(car);
            model.addAttribute("message", "Car added successfully!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("cars", carList.getAllCars());
        return "car/car-list";
    }

    @GetMapping("/car-customers")
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerList.getAllCustomers());
        return "customers";
    }

    @GetMapping("/customers/add")
    public String showAddForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "add-customer";
    }

    @PostMapping("/customers/add")
    public String addCustomer(@ModelAttribute Customer customer) {
        customerList.addCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customers/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Customer customer = customerList.findCustomer(id);
        if (customer == null) {
            return "redirect:/customers";
        }
        model.addAttribute("customer", customer);
        return "edit-customer";
    }

    @PostMapping("/customers/edit")
    public String editCustomer(@ModelAttribute Customer customer) {
        customerList.updateCustomer(
            customer.getCustomerId(), 
            customer.getName(), 
            customer.getPhone(), 
            customer.getAddress()
        );
        return "redirect:/customers";
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable String id) {
        customerList.deleteCustomer(id);
        return "redirect:/customers";
    }
}
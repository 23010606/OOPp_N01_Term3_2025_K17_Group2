package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.service.CarList;
import com.example.servingwebcontent.service.CustomerList;
import com.example.servingwebcontent.service.InvoiceList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InvoiceController {
    private final InvoiceList invoiceList;
    private final CustomerList customerList;
    private final CarList carList;

    @Autowired
    public InvoiceController(InvoiceList invoiceList, CustomerList customerList, CarList carList) {
        this.invoiceList = invoiceList;
        this.customerList = customerList;
        this.carList = carList;
    }

    @GetMapping("/create-invoice")
    public String showCreateInvoiceForm(Model model) {
        model.addAttribute("customers", customerList.getAllCustomers());
        model.addAttribute("cars", carList.getAllCars());
        return "invoice/create-invoice";
    }

    @PostMapping("/create-invoice")
    public String createInvoice(@RequestParam String invoiceId, 
                               @RequestParam String customerId, 
                               @RequestParam String carId, 
                               Model model) {
        try {
            invoiceList.createInvoice(invoiceId, customerId, carId);
            model.addAttribute("message", "Invoice created successfully!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("customers", customerList.getAllCustomers());
        model.addAttribute("cars", carList.getAllCars());
        return "invoice/create-invoice";
    }
}
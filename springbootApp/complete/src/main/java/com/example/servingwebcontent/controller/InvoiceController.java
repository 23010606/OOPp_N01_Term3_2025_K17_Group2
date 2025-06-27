package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Invoice;
import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.model.Car;
import com.example.servingwebcontent.service.CarList;
import com.example.servingwebcontent.service.CustomerList;
import com.example.servingwebcontent.service.InvoiceList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/invoices")
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

    @GetMapping("/create")
    public String showCreateInvoiceForm(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        model.addAttribute("customers", customerList.getAllCustomers(PageRequest.of(page, size)).getContent());
        model.addAttribute("cars", carList.getAvailableCars().stream().limit(size).toList()); // Lấy một số lượng giới hạn
        return "invoice/create-invoice";
    }

    @PostMapping("/create")
    public String createInvoice(@RequestParam String invoiceId,
                               @RequestParam String customerId,
                               @RequestParam String carId,
                               @RequestParam double totalAmount,
                               RedirectAttributes redirectAttributes) {
        if (invoiceId == null || invoiceId.trim().isEmpty() ||
            customerId == null || customerId.trim().isEmpty() ||
            carId == null || carId.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "All fields are required!");
            return "redirect:/invoices/create";
        }
        try {
            invoiceList.createInvoice(invoiceId, customerId, carId, totalAmount);
            redirectAttributes.addFlashAttribute("message", "Invoice created successfully!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/invoices/create";
    }

    @GetMapping
    public String showInvoices(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        model.addAttribute("invoices", invoiceList.getAllInvoices(PageRequest.of(page, size)).getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", invoiceList.getAllInvoices(PageRequest.of(page, size)).getTotalPages());
        return "invoice/invoice-list";
    }

    @GetMapping("/delete/{invoiceId}")
    public String deleteInvoice(@PathVariable String invoiceId, RedirectAttributes redirectAttributes) {
        if (invoiceId == null || invoiceId.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Invoice ID cannot be null or empty!");
            return "redirect:/invoices";
        }
        try {
            invoiceList.deleteInvoice(invoiceId);
            redirectAttributes.addFlashAttribute("message", "Invoice deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/invoices";
    }

    @GetMapping("/edit/{invoiceId}")
    public String showEditInvoiceForm(@PathVariable String invoiceId, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Invoice invoice = invoiceList.findInvoice(invoiceId);
        if (invoice == null) {
            return "redirect:/invoices";
        }
        model.addAttribute("invoice", invoice);
        model.addAttribute("customers", customerList.getAllCustomers(PageRequest.of(page, size)).getContent());
        model.addAttribute("cars", carList.getAvailableCars().stream().limit(size).toList());
        return "invoice/edit-invoice";
    }

    @PostMapping("/edit")
    public String editInvoice(@RequestParam String invoiceId,
                             @RequestParam String customerId,
                             @RequestParam String carId,
                             @RequestParam double totalAmount,
                             RedirectAttributes redirectAttributes) {
        if (invoiceId == null || invoiceId.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Invoice ID cannot be null or empty!");
            return "redirect:/invoices";
        }
        Customer customer = customerList.findCustomer(customerId);
        Car car = carList.findCar(carId);
        if (customer == null || car == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid customer or car!");
            return "redirect:/invoices";
        }
        if (totalAmount <= 0) {
            redirectAttributes.addFlashAttribute("error", "Total amount must be positive!");
            return "redirect:/invoices/edit/" + invoiceId;
        }
        try {
            invoiceList.updateInvoice(invoiceId, customer, car, totalAmount);
            redirectAttributes.addFlashAttribute("message", "Invoice updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/invoices";
    }
}
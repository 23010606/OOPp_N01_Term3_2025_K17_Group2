package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.service.CustomerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerList customerList;

    @Autowired
    public CustomerController(CustomerList customerList) {
        this.customerList = customerList;
    }

    @GetMapping
    public String showCustomers(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        Page<Customer> customerPage = customerList.getAllCustomers(PageRequest.of(page, size));
        model.addAttribute("customers", customerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customerPage.getTotalPages());
        model.addAttribute("customer", new Customer());
        return "customer/customer-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/add-customer";
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute @Valid Customer customer, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("customer", customer);
            return "customer/add-customer";
        }
        try {
            customer.setRegistrationDate(new Date()); // Thiết lập ngày đăng ký mặc định
            customerList.addCustomer(customer);
            redirectAttributes.addFlashAttribute("message", "Customer added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Customer customer = customerList.findCustomer(id);
        if (customer == null) return "redirect:/customers";
        model.addAttribute("customer", customer);
        return "customer/edit-customer";
    }

    @PostMapping("/edit")
    public String editCustomer(@ModelAttribute @Valid Customer customer, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("customer", customer);
            return "customer/edit-customer";
        }
        try {
            customerList.updateCustomer(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhoneNumber(),
                    customer.getAddress(), customer.getRegistrationDate());
            redirectAttributes.addFlashAttribute("message", "Customer updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            customerList.deleteCustomer(id);
            redirectAttributes.addFlashAttribute("message", "Customer deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/customers";
    }

    @GetMapping("/search")
    public String searchCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String address,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Page<Customer> customerPage = customerList.searchCustomers(name, phoneNumber, email, address, PageRequest.of(page, size));
        model.addAttribute("customers", customerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customerPage.getTotalPages());
        model.addAttribute("name", name);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("email", email);
        model.addAttribute("address", address);
        return "customer/customer-list";
    }

    @GetMapping("/filter/date")
    public String filterByRegistrationDate(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Page<Customer> customerPage = customerList.filterByRegistrationDate(startDate, endDate, PageRequest.of(page, size));
        model.addAttribute("customers", customerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customerPage.getTotalPages());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "customer/customer-list";
    }

    @GetMapping("/detail/{id}")
    public String showCustomerDetail(@PathVariable String id, Model model) {
        Customer customer = customerList.findCustomer(id);
        if (customer == null) return "redirect:/customers";
        model.addAttribute("customer", customer);
        return "customer/customer-detail";
    }
}
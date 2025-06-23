package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.service.CustomerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("customer", new Customer());
        return "customer/customer-list";
    }

    @GetMapping("/customers/add")
    public String showAddForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/add-customer";
    }

    @PostMapping("/customers/add")
    public String addCustomer(@ModelAttribute Customer customer, Model model, RedirectAttributes redirectAttributes) {
        try {
            customerList.addCustomer(customer);
            redirectAttributes.addFlashAttribute("message", "Customer added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/customers";
    }

    @GetMapping("/customers/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Customer customer = customerList.findCustomer(id);
        if (customer == null) {
            return "redirect:/customers";
        }
        model.addAttribute("customer", customer);
        return "customer/edit-customer";
    }

    @PostMapping("/customers/edit")
    public String editCustomer(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        try {
            customerList.updateCustomer(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getAddress()
            );
            redirectAttributes.addFlashAttribute("message", "Customer updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/customers";
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            customerList.deleteCustomer(id);
            redirectAttributes.addFlashAttribute("message", "Customer deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/customers";
    }

    // Hiển thị lịch sử giao dịch
    @GetMapping("/customers/history/{id}")
    public String showPurchaseHistory(@PathVariable String id, Model model) {
        try {
            model.addAttribute("invoices", customerList.getPurchaseHistory(id));
            model.addAttribute("customerId", id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "customer/purchase-history";
    }

    // Hiển thị tổng tiền mua sắm
    @GetMapping("/customers/total/{id}")
    public String showTotalPurchase(@PathVariable String id, Model model) {
        try {
            double total = customerList.getTotalPurchaseAmount(id);
            model.addAttribute("customerId", id);
            model.addAttribute("total", total);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "customer/total-purchase";
    }

    // Tìm kiếm khách hàng
    @GetMapping("/customers/search")
    public String searchCustomers(@RequestParam String keyword, Model model) {
        model.addAttribute("customers", customerList.searchCustomers(keyword));
        model.addAttribute("customer", new Customer());
        return "customer/customer-list";
    }
}
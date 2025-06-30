package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Invoice;
import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.model.Car;
import com.example.servingwebcontent.model.PaymentStatus;
import com.example.servingwebcontent.service.CarList;
import com.example.servingwebcontent.service.CustomerList;
import com.example.servingwebcontent.service.InvoiceList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.UUID;

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

    // Danh sách hóa đơn
    @GetMapping
    public String showInvoices(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Invoice> invoicePage = invoiceList.getAllInvoices(PageRequest.of(page, size));
        model.addAttribute("invoices", invoicePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", invoicePage.getTotalPages());
        return "invoice/invoice-list";
    }

    // Hiển thị form tạo hóa đơn
    @GetMapping("/create")
    public String showCreateInvoiceForm(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        model.addAttribute("customers", customerList.getAllCustomers(PageRequest.of(page, size)).getContent());
        model.addAttribute("cars", carList.getAvailableCars().stream().filter(c -> "Available".equals(c.getStatus())).limit(size).toList());
        return "invoice/create-invoice";
    }

    // Xử lý tạo hóa đơn
    @PostMapping("/create")
    public String createInvoice(
        @RequestParam(required = false) String invoiceId,
        @RequestParam String customerId,
        @RequestParam String carId,
        @RequestParam double totalAmount,
        @RequestParam(required = false) String note,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date pickupDate,
        RedirectAttributes redirectAttributes) {
        if (customerId == null || customerId.trim().isEmpty() ||
            carId == null || carId.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Customer ID and Car ID are required!");
            return "redirect:/invoices/create";
        }
        try {
            Car car = carList.findCar(carId);
            if (car == null || !"Available".equals(car.getStatus())) {
                throw new IllegalStateException("Car is not available!");
            }
            invoiceList.createInvoice(invoiceId, customerId, carId, totalAmount, note, pickupDate);
            redirectAttributes.addFlashAttribute("message", "Invoice created successfully!");
            return "redirect:/invoices";
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/invoices/create";
        }
    }

    // Hiển thị form sửa hóa đơn
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

    // Xử lý sửa hóa đơn
    @PostMapping("/edit")
    public String editInvoice(@RequestParam String invoiceId,
                         @RequestParam String customerId,
                         @RequestParam String carId,
                         @RequestParam double totalAmount,
                         @RequestParam(required = false) String note,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date pickupDate,
                         RedirectAttributes redirectAttributes) {
        if (invoiceId == null || invoiceId.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Invoice ID cannot be null or empty!");
            return "redirect:/invoices";
        }
        Customer customer = customerList.findCustomer(customerId);
        Car car = carList.findCar(carId);
        if (customer == null || car == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid customer or car!");
            return "redirect:/invoices/edit/" + invoiceId;
        }
        if (totalAmount <= 0) {
            redirectAttributes.addFlashAttribute("error", "Total amount must be positive!");
            return "redirect:/invoices/edit/" + invoiceId;
        }
        try {
            Invoice invoice = invoiceList.findInvoice(invoiceId);
            if (invoice == null) {
                throw new IllegalArgumentException("Invoice not found!");
            }
            invoice.setCustomer(customer);
            invoice.setCar(car);
            invoice.setTotalAmount(totalAmount);
            invoice.setNote(note);
            invoice.setPickupDate(pickupDate);
            invoiceList.updateInvoice(invoiceId, invoice.getCustomer(), invoice.getCar(), invoice.getTotalAmount());
            redirectAttributes.addFlashAttribute("message", "Invoice updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/invoices";
    }

    // Xóa hóa đơn
    @GetMapping("/delete/{invoiceId}")
    public String deleteInvoice(@PathVariable String invoiceId, RedirectAttributes redirectAttributes) {
        if (invoiceId == null || invoiceId.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Invoice ID cannot be null or empty!");
            return "redirect:/invoices";
        }
        try {
            Invoice invoice = invoiceList.findInvoice(invoiceId);
            if (invoice == null) {
                throw new IllegalArgumentException("Invoice not found!");
            }
            if (invoice.getPaymentStatus() == PaymentStatus.PAID || invoice.getPaymentStatus() == PaymentStatus.INSTALLMENT) {
                throw new IllegalStateException("Cannot delete paid or installment invoice!");
            }
            invoiceList.deleteInvoice(invoiceId);
            redirectAttributes.addFlashAttribute("message", "Invoice deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/invoices";
    }

    // Xem chi tiết hóa đơn
    @GetMapping("/detail/{invoiceId}")
    public String showInvoiceDetail(@PathVariable String invoiceId, Model model) {
        Invoice invoice = invoiceList.findInvoice(invoiceId);
        if (invoice == null) {
            return "redirect:/invoices";
        }
        model.addAttribute("invoice", invoice);
        return "invoice/invoice-detail";
    }

    // Cập nhật thanh toán
    @PostMapping("/updatePayment")
    public String updatePayment(@RequestParam String invoiceId,
                               @RequestParam double amount,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date paymentDate,
                               RedirectAttributes redirectAttributes) {
        if (invoiceId == null || invoiceId.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Invoice ID cannot be null or empty!");
            return "redirect:/invoices";
        }
        try {
            Invoice invoice = invoiceList.findInvoice(invoiceId);
            if (invoice == null) {
                throw new IllegalArgumentException("Invoice not found!");
            }
            invoice.addPayment(amount, paymentDate);
            invoiceList.updateInvoice(invoiceId, invoice.getCustomer(), invoice.getCar(), invoice.getTotalAmount());
            redirectAttributes.addFlashAttribute("message", "Payment updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/invoices/detail/" + invoiceId;
    }

    // Tìm kiếm hóa đơn
    @GetMapping("/search")
    public String searchInvoices(
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        Model model
    ) {
        Page<Invoice> invoicePage = invoiceList.getInvoicesByPickupDateRange(startDate, endDate, PageRequest.of(page, size));
        model.addAttribute("invoices", invoicePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", invoicePage.getTotalPages());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "invoice/invoice-list";
    }

    // In hóa đơn
    @GetMapping("/print/{invoiceId}")
    public String printInvoice(@PathVariable String invoiceId, Model model, RedirectAttributes redirectAttributes) {
        Invoice invoice = invoiceList.findInvoice(invoiceId);
        if (invoice == null) {
            redirectAttributes.addFlashAttribute("error", "Invoice not found!");
            return "redirect:/invoices";
        }
        model.addAttribute("invoice", invoice);
        return "invoice/print-invoice";
    }
}
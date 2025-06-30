package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Car;
import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.model.Invoice;
import com.example.servingwebcontent.model.PaymentStatus;
import com.example.servingwebcontent.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceList {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private CarList carList;
    @Autowired
    private CustomerList customerList;

    public void createInvoice(String invoiceId, String customerId, String carId, double totalAmount, String note, Date pickupDate) {
        if (invoiceId == null || invoiceId.trim().isEmpty()) {
            invoiceId = "INV-" + new Date().getTime() + "-" + UUID.randomUUID().toString().substring(0, 4);
        }
        if (invoiceRepository.existsById(invoiceId)) {
            throw new IllegalArgumentException("Invoice ID " + invoiceId + " already exists!");
        }
        Customer customer = customerList.findCustomer(customerId);
        if (customer == null) throw new IllegalArgumentException("Customer not found!");
        Car car = carList.findCar(carId);
        if (car == null) throw new IllegalArgumentException("Car not found!");
        if (!"Available".equals(car.getStatus())) throw new IllegalStateException("Car not available!");
        if (totalAmount <= 0) throw new IllegalArgumentException("Total amount must be positive!");

        Invoice invoice = new Invoice(invoiceId, customer, car, totalAmount);
        invoice.setDate(new Date());
        invoice.setPaymentStatus(PaymentStatus.PENDING);
        invoice.setNote(note);
        invoice.setPickupDate(pickupDate);

        // Chỉ lưu Invoice, không cần update lại customer
        invoiceRepository.save(invoice);

        // Cập nhật trạng thái xe và lưu lại car nếu cần
        car.setStatus("Sold");
        carList.updateCar(car.getCarId(), car.getBrand(), car.getModel(), car.getYear(), car.getPrice(),
                car.getStatus(), car.getImportDate(), car.getQuantity());
    }

    public Page<Invoice> getAllInvoices(PageRequest pageRequest) {
        return invoiceRepository.findAll(pageRequest);
    }

    public void deleteInvoice(String invoiceId) {
        if (invoiceId == null || invoiceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invoice ID cannot be null or empty!");
        }
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(invoiceId);
        if (invoiceOpt.isEmpty()) {
            throw new IllegalArgumentException("Invoice with ID " + invoiceId + " not found!");
        }
        Invoice invoice = invoiceOpt.get();
        if (invoice.getPaymentStatus() == PaymentStatus.PAID || invoice.getPaymentStatus() == PaymentStatus.INSTALLMENT) {
            throw new IllegalStateException("Cannot delete paid or installment invoice!");
        }
        Customer customer = invoice.getCustomer();
        if (customer != null) {
            customer.deletePurchase(invoiceId);
            customerList.updateCustomer(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhoneNumber(),
                    customer.getAddress(), customer.getRegistrationDate());
        }
        Car car = invoice.getCar();
        if (car != null) {
            car.setStatus("Available");
            carList.updateCar(car.getCarId(), car.getBrand(), car.getModel(), car.getYear(), car.getPrice(),
                    car.getStatus(), car.getImportDate(), car.getQuantity());
        }
        invoiceRepository.deleteById(invoiceId);
    }

    public Invoice findInvoice(String invoiceId) {
        if (invoiceId == null || invoiceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invoice ID cannot be null or empty!");
        }
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(invoiceId);
        return invoiceOpt.orElse(null);
    }

    public void updateInvoice(String invoiceId, Customer customer, Car car, double totalAmount) {
        if (invoiceId == null || invoiceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invoice ID cannot be null or empty!");
        }
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(invoiceId);
        if (invoiceOpt.isEmpty()) {
            throw new IllegalArgumentException("Invoice with ID " + invoiceId + " not found!");
        }
        Invoice invoice = invoiceOpt.get();
        if (totalAmount <= 0) {
            throw new IllegalArgumentException("Total amount must be positive!");
        }
        if (customer != null) invoice.setCustomer(customer);
        if (car != null) invoice.setCar(car);
        invoice.setTotalAmount(totalAmount);
        invoiceRepository.save(invoice);
        if (customer != null) {
            customerList.updateCustomer(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhoneNumber(),
                    customer.getAddress(), customer.getRegistrationDate());
        }
        if (car != null && !"Sold".equals(car.getStatus())) {
            car.setStatus("Sold");
            carList.updateCar(car.getCarId(), car.getBrand(), car.getModel(), car.getYear(), car.getPrice(),
                    car.getStatus(), car.getImportDate(), car.getQuantity());
        }
    }

    public void addPayment(String invoiceId, double amount, Date paymentDate) {
        if (invoiceId == null || invoiceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invoice ID cannot be null or empty!");
        }
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(invoiceId);
        if (invoiceOpt.isEmpty()) {
            throw new IllegalArgumentException("Invoice with ID " + invoiceId + " not found!");
        }
        Invoice invoice = invoiceOpt.get();
        invoice.addPayment(amount, paymentDate);
        invoiceRepository.save(invoice);
    }

    public Page<Invoice> getInvoicesByPickupDateRange(Date startDate, Date endDate, Pageable pageable) {
        return invoiceRepository.findByPickupDateBetween(startDate, endDate, pageable);
    }

    public Page<Invoice> searchInvoices(String invoiceId, String customerName, Date startDate, Date endDate, PaymentStatus paymentStatus, PageRequest pageRequest) {
        // Giả định InvoiceRepository có phương thức tùy chỉnh
        return invoiceRepository.findByCriteria(invoiceId, customerName, startDate, endDate, paymentStatus, pageRequest);
    }
}
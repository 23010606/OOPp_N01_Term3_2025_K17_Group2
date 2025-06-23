package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Car;
import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.model.Invoice;
import com.example.servingwebcontent.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceList {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private CarList carList;
    @Autowired
    private CustomerList customerList;

    public void createInvoice(String invoiceId, String customerId, String carId) {
        if (invoiceRepository.existsById(invoiceId)) {
            throw new IllegalArgumentException("Invoice ID " + invoiceId + " already exists!");
        }
        Customer customer = customerList.findCustomer(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " not found!");
        }
        Car car = carList.findCar(carId);
        if (car == null) {
            throw new IllegalArgumentException("Car with ID " + carId + " not found!");
        }
        if (car.getStatus().equals("Sold")) {
            throw new IllegalStateException("Car with ID " + carId + " is already sold!");
        }
        Invoice invoice = new Invoice(invoiceId, customer, car);
        invoice.setTotalAmount(car.getPrice());
        invoiceRepository.save(invoice);
        customer.addPurchase(invoice);
        car.sellCar();
        customerList.updateCustomer(customerId, customer.getName(), customer.getPhone(), customer.getAddress());
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public void deleteInvoice(String invoiceId) {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(invoiceId);
        if (invoiceOpt.isEmpty()) {
            throw new IllegalArgumentException("Invoice with ID " + invoiceId + " not found!");
        }
        Invoice invoice = invoiceOpt.get();
        Customer customer = customerList.findCustomer(invoice.getCustomerId());
        if (customer != null) {
            customer.deletePurchase(invoiceId);
            customerList.updateCustomer(customer.getCustomerId(), customer.getName(), customer.getPhone(), customer.getAddress());
        }
        Car car = carList.findCar(invoice.getCarId());
        if (car != null) {
            car.setStatus("Available");
            carList.updateCar(car.getCarId(), car.getBrand(), car.getModel(), car.getPrice());
        }
        invoiceRepository.deleteById(invoiceId);
    }

    public Invoice findInvoice(String invoiceId) {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(invoiceId);
        return invoiceOpt.orElse(null);
    }

    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }
}
package model;

import java.util.ArrayList;
import java.util.List;

public class InvoiceManager {
    private List<Invoice> invoices;

    public InvoiceManager() {
        invoices = new ArrayList<>();
    }

    // CRUD Methods
    // Phương thức chính: Create Invoice
    public void createInvoice(String invoiceId, String customerId, String carId, 
                             CustomerManager customerManager, CarManager carManager) {
        if (findInvoice(invoiceId) != null) {
            System.out.println("Error: Invoice ID " + invoiceId + " already exists!");
            return;
        }
        Customer customer = customerManager.findCustomer(customerId);
        if (customer == null) {
            System.out.println("Error: Customer with ID " + customerId + " not found!");
            return;
        }
        Car car = carManager.findCar(carId);
        if (car == null) {
            System.out.println("Error: Car with ID " + carId + " not found!");
            return;
        }
        if (car.getStatus().equals("Sold")) {
            System.out.println("Error: Car with ID " + carId + " is already sold!");
            return;
        }
        Invoice invoice = new Invoice(invoiceId, customer, car);
        invoices.add(invoice);
        customer.addPurchase(invoice);
        car.sellCar();
        System.out.println("Invoice created successfully: " + invoice.getInvoiceDetails());
    }

    public void displayInvoices() {
        if (invoices.isEmpty()) {
            System.out.println("No invoices found!");
            return;
        }
        System.out.println("\nInvoice List:");
        for (Invoice invoice : invoices) {
            System.out.println(invoice.getInvoiceDetails());
        }
    }

    public void deleteInvoice(String invoiceId, CustomerManager customerManager, CarManager carManager) {
        Invoice invoice = findInvoice(invoiceId);
        if (invoice == null) {
            System.out.println("Error: Invoice with ID " + invoiceId + " not found!");
            return;
        }
        Customer customer = customerManager.findCustomer(invoice.getCustomerId());
        if (customer != null) {
            customer.deletePurchase(invoiceId);
        }
        Car car = carManager.findCar(invoice.getCarId());
        if (car != null) {
            car.setStatus("Available");
        }
        invoices.remove(invoice);
        System.out.println("Deleted invoice with ID: " + invoiceId);
    }

    // Helper Method
    private Invoice findInvoice(String invoiceId) {
        for (Invoice invoice : invoices) {
            if (invoice.getInvoiceId().equals(invoiceId)) {
                return invoice;
            }
        }
        return null;
    }

    // Getter for invoices (used by CustomerManager)
    public List<Invoice> getInvoices() {
        return invoices;
    }
}
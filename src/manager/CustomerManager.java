package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerManager {
    private List<Customer> customers;

    public CustomerManager() {
        customers = new ArrayList<>();
    }

    // CRUD Methods
    public void addCustomer(Customer customer) {
        if (findCustomer(customer.getCustomerId()) != null) {
            System.out.println("Error: Customer ID " + customer.getCustomerId() + " already exists!");
            return;
        }
        customers.add(customer);
        System.out.println("Added customer: " + customer.getCustomerInfo());
    }

    public void displayCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found!");
            return;
        }
        System.out.println("\nCustomer List:");
        for (Customer customer : customers) {
            System.out.println(customer.getCustomerInfo());
        }
    }

    public void updateCustomer(String customerId, String name, String phone, String address) {
        Customer customer = findCustomer(customerId);
        if (customer == null) {
            System.out.println("Error: Customer with ID " + customerId + " not found!");
            return;
        }
        customer.setName(name);
        customer.setPhone(phone);
        customer.setAddress(address);
        System.out.println("Updated customer: " + customer.getCustomerInfo());
    }

    public void deleteCustomer(String customerId) {
        Customer customer = findCustomer(customerId);
        if (customer == null) {
            System.out.println("Error: Customer with ID " + customerId + " not found!");
            return;
        }
        if (!customer.getPurchaseHistory().isEmpty()) {
            System.out.println("Error: Cannot delete customer with existing invoices!");
            return;
        }
        customers.remove(customer);
        System.out.println("Deleted customer with ID: " + customerId);
    }

    // Method 3: Display customers with transactions today
    public void displayCustomersWithTransactionsToday(List<Invoice> invoices) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String today = sdf.format(new Date());
        boolean found = false;
        System.out.println("\nCustomers with Transactions Today (" + today + "):");
        for (Invoice invoice : invoices) {
            if (sdf.format(invoice.getDate()).equals(today)) {
                Customer customer = findCustomer(invoice.getCustomerId());
                if (customer != null) {
                    System.out.println(customer.getCustomerInfo() + "\nInvoice: " + invoice.getInvoiceDetails());
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No customers with transactions today!");
        }
    }

    // Helper Method
    public Customer findCustomer(String customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }
}
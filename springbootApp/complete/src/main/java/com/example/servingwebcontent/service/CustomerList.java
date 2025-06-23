package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.model.Invoice;
import com.example.servingwebcontent.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerList {
    @Autowired
    private CustomerRepository customerRepository;

    public void addCustomer(Customer customer) {
        if (customerRepository.existsById(customer.getCustomerId())) {
            throw new IllegalArgumentException("Customer ID " + customer.getCustomerId() + " already exists!");
        }
        customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void updateCustomer(String customerId, String name, String phone, String address) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " not found!");
        }
        Customer customer = customerOpt.get();
        customer.setName(name);
        customer.setPhone(phone);
        customer.setAddress(address);
        customerRepository.save(customer);
    }

    public void deleteCustomer(String customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " not found!");
        }
        Customer customer = customerOpt.get();
        if (!customer.getPurchaseHistory().isEmpty()) {
            throw new IllegalStateException("Cannot delete customer with existing invoices!");
        }
        customerRepository.deleteById(customerId);
    }

    public List<Customer> getCustomersWithTransactionsToday(List<Invoice> invoices) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String today = sdf.format(new Date());
        List<Customer> result = new ArrayList<>();
        for (Invoice invoice : invoices) {
            if (sdf.format(invoice.getDate()).equals(today)) {
                Customer customer = findCustomer(invoice.getCustomerId());
                if (customer != null && !result.contains(customer)) {
                    result.add(customer);
                }
            }
        }
        return result;
    }

    public Customer findCustomer(String customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        return customerOpt.orElse(null);
    }
}
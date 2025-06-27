package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerList {
    @Autowired
    private CustomerRepository customerRepository;

    public void addCustomer(Customer customer) {
        if (customerRepository.existsById(customer.getId())) {
            throw new IllegalArgumentException("Customer ID " + customer.getId() + " already exists!");
        }
        customerRepository.save(customer);
    }

    public void updateCustomer(String id, String name, String email, String phoneNumber, String address, Date registrationDate) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isEmpty()) {
            throw new IllegalArgumentException("Customer with ID " + id + " not found!");
        }
        Customer customer = customerOpt.get();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.setAddress(address);
        customer.setRegistrationDate(registrationDate);
        customerRepository.save(customer);
    }

    public void deleteCustomer(String id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isEmpty()) {
            throw new IllegalArgumentException("Customer with ID " + id + " not found!");
        }
        Customer customer = customerOpt.get();
        if (!customer.getPurchaseHistory().isEmpty()) {
            throw new IllegalStateException("Cannot delete customer with purchase history!");
        }
        customerRepository.deleteById(id);
    }

    public Customer findCustomer(String id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Page<Customer> searchCustomers(String name, String phoneNumber, String email, String address, PageRequest pageRequest) {
        List<Customer> all = customerRepository.findAll();
        List<Customer> filtered = all.stream()
            .filter(c -> name == null || name.isBlank() || c.getName().toLowerCase().contains(name.toLowerCase()))
            .filter(c -> phoneNumber == null || phoneNumber.isBlank() || c.getPhoneNumber().contains(phoneNumber))
            .filter(c -> email == null || email.isBlank() || c.getEmail().toLowerCase().contains(email.toLowerCase()))
            .filter(c -> address == null || address.isBlank() || c.getAddress().toLowerCase().contains(address.toLowerCase()))
            .toList();
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), filtered.size());
        List<Customer> pageContent = (start >= filtered.size()) ? List.of() : filtered.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, filtered.size());
    }

    public Page<Customer> filterByRegistrationDate(Date startDate, Date endDate, PageRequest pageRequest) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date are required!");
        }
        return customerRepository.findByRegistrationDateBetween(startDate, endDate, pageRequest);
    }

    public Page<Customer> getAllCustomers(PageRequest pageRequest) {
        return customerRepository.findAll(pageRequest);
    }
}
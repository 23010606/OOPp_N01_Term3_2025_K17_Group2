package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.model.Invoice;
import com.example.servingwebcontent.repository.CustomerRepository;
import com.example.servingwebcontent.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerList {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    // CRUD
    public void addCustomer(Customer customer) {
        if (customerRepository.existsById(customer.getId())) {
            throw new IllegalArgumentException("Customer ID " + customer.getId() + " already exists!");
        }
        // Có thể thêm kiểm tra định dạng email/phone nếu cần
        customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void updateCustomer(String id, String name, String email, String phoneNumber, String address) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isEmpty()) {
            throw new IllegalArgumentException("Customer with ID " + id + " not found!");
        }
        Customer customer = customerOpt.get();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.setAddress(address);
        customerRepository.save(customer);
    }

    public void deleteCustomer(String id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isEmpty()) {
            throw new IllegalArgumentException("Customer with ID " + id + " not found!");
        }
        Customer customer = customerOpt.get();
        if (!customer.getPurchaseHistory().isEmpty()) {
            throw new IllegalStateException("Cannot delete customer with existing invoices!");
        }
        customerRepository.deleteById(id);
    }


    // Hỗ trợ: Tìm khách hàng theo ID
    public Customer findCustomer(String id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        return customerOpt.orElse(null);
    }

    // Thêm: Lấy lịch sử giao dịch
    public List<Invoice> getPurchaseHistory(String customerId) {
        Customer customer = findCustomer(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " not found!");
        }
        return customer.getPurchaseHistory();
    }

    // Thêm: Tính tổng tiền mua sắm
    public double getTotalPurchaseAmount(String customerId) {
        Customer customer = findCustomer(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " not found!");
        }
        return customer.getTotalPurchaseAmount();
    }

    // Thêm: Tìm kiếm khách hàng
    public List<Customer> searchCustomers(String keyword) {
        return customerRepository.findByKeyword(keyword);
    }
}
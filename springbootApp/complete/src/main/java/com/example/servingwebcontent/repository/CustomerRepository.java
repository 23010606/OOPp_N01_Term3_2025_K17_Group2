package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Customer> findByPhoneNumberContaining(String phoneNumber, Pageable pageable);
    Page<Customer> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    Page<Customer> findByAddressContainingIgnoreCase(String address, Pageable pageable);
    Page<Customer> findByRegistrationDateBetween(Date startDate, Date endDate, Pageable pageable);
}
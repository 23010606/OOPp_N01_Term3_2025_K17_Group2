package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    // Có thể bổ sung các phương thức truy vấn đặc biệt nếu cần
}
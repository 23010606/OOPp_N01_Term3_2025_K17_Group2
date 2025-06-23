package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("SELECT c FROM Customer c WHERE c.name LIKE %:keyword% OR c.phoneNumber LIKE %:keyword% OR c.email LIKE %:keyword%")
    List<Customer> findByKeyword(String keyword);

    // XÓA hoặc COMMENT dòng dưới vì không dùng được với JPA
    // @Query("SELECT DISTINCT c FROM Customer c JOIN c.purchaseHistory i WHERE DATE(i.date) = CURRENT_DATE")
    // List<Customer> findCustomersWithTransactionsToday();
}
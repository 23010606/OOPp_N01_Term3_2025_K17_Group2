package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    // Có thể bổ sung các phương thức truy vấn đặc biệt nếu cần
}
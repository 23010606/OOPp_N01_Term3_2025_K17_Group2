package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    Page<Invoice> findByDateBetween(Date startDate, Date endDate, Pageable pageable);
}
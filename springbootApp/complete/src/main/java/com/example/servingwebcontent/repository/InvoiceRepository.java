package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.Invoice;
import com.example.servingwebcontent.model.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    Page<Invoice> findByPickupDateBetween(Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT i FROM Invoice i WHERE (:invoiceId IS NULL OR i.invoiceId = :invoiceId) " +
           "AND (:customerName IS NULL OR i.customer.name LIKE %:customerName%) " +
           "AND (:startDate IS NULL OR i.pickupDate >= :startDate) " +
           "AND (:endDate IS NULL OR i.pickupDate <= :endDate) " +
           "AND (:paymentStatus IS NULL OR i.paymentStatus = :paymentStatus)")
    Page<Invoice> findByCriteria(@Param("invoiceId") String invoiceId,
                                 @Param("customerName") String customerName,
                                 @Param("startDate") Date startDate,
                                 @Param("endDate") Date endDate,
                                 @Param("paymentStatus") PaymentStatus paymentStatus,
                                 Pageable pageable);
}
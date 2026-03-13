package com.example.invoice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.invoice.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByInvoiceStatus(String invoiceStatus);

    List<Invoice> findByProductId(String productId);
}

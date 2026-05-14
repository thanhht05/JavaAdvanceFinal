package com.project.thanh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.thanh.domain.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Invoice findById(long id);
}

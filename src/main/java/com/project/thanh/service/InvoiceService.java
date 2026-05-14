package com.project.thanh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thanh.domain.Invoice;
import com.project.thanh.repository.InvoiceRepository;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice save(Invoice invoice) {
        Invoice db = this.invoiceRepository.save(invoice);
        return db;
    }

    public Invoice getInvoiceByBookingId(long bookingId) {
        return invoiceRepository.findById(bookingId);
    }
}

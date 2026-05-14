package com.project.thanh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thanh.domain.Invoice;
import com.project.thanh.domain.InvoiceDetail;
import com.project.thanh.repository.InvoiceDetailRepository;

@Service
public class InvoiceDetailService {
    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    public InvoiceDetail save(InvoiceDetail invoiceDetail) {
        InvoiceDetail i = this.invoiceDetailRepository.save(invoiceDetail);
        return i;
    }

    public List<InvoiceDetail> getByInvoice(Invoice invoice) {
        return invoiceDetailRepository.findByInvoice(invoice);
    }

    // public Invoice findByBookingId(long id) {
    // return this.invoiceDetailRepository.findByBookingId(id);
    // }
}

package com.project.thanh.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "invoice_details")
@Getter
@Setter
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hóa đơn
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    // Dịch vụ (nullable)
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    private String description;

    private int quantity;

    private String unit;

    // giá tại thời điểm thanh toán
    private long unitPrice;

    private long totalPrice;
}
package com.project.thanh.domain;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "invoices")
@Getter
@Setter
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Booking liên quan
    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private LocalDateTime createdAt;

    private long roomTotal;

    private long serviceTotal;

    private long totalAmount;

    // @Enumerated(EnumType.STRING)
    // private InvoiceStatus status;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceDetail> invoiceDetails;
}
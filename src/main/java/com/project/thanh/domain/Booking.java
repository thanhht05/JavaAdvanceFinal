package com.project.thanh.domain;

import java.time.LocalDate;
import java.util.Date;

import com.project.thanh.enums.BookingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "booking")
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private long totalPrice; // tổng trước giảm
    private long discountAmount; // số tiền giảm
    private long finalPrice; // sau giảm

    @Column(columnDefinition = "NVARCHAR(200)")
    private String customerName;
    private String phone;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

}

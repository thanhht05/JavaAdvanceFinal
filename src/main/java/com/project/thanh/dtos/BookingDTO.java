package com.project.thanh.dtos;

import java.time.LocalDate;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingDTO {
    private Long roomId;

    private String voucherCode;
    private String customerName;
    private String phone;
    private String email;

    private LocalDate checkIn;
    private LocalDate checkOut;

    private long totalPrice;
    private long discountAmount;
    private long finalPrice;

    private String status;
}

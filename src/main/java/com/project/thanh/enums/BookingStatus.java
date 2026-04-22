package com.project.thanh.enums;

public enum BookingStatus {
    PENDING,
    CONFIRMED, // admin đã xác nhận - CHờ ngày checkin
    CHECKED_IN,
    CHECKED_OUT,
    CANCELLED,
    COMPLETED // khi thuê xong ở xong
}

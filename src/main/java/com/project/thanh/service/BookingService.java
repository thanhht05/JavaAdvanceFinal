package com.project.thanh.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thanh.domain.Booking;
import com.project.thanh.enums.BookingStatus;
import com.project.thanh.repository.BookingRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public void saveBooking(Booking booking) {
        this.bookingRepository.save(booking);
    }

    public List<Booking> getAllBokingByUserId(long userId) {
        return this.bookingRepository.findAllByUser_Id(userId);

    }

    public Booking getBookingById(long id) {
        return this.bookingRepository.findById(id);
    }

    public boolean canCancel(Booking booking) {
        return booking.getBookingStatus() != BookingStatus.CANCELLED
                && booking.getBookingStatus() != BookingStatus.CONFIRMED
                && booking.getCheckInDate().isAfter(LocalDate.now()); // Huỷ phòng trước ngày nhận phòng
    }
}

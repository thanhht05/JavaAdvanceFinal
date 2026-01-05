package com.project.thanh.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.thanh.domain.Booking;
import com.project.thanh.enums.BookingStatus;
import com.project.thanh.repository.BookingRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Page<Booking> getBookingPage(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return this.bookingRepository.findAll(pageable);
    }

    public void saveBooking(Booking booking) {
        this.bookingRepository.save(booking);
    }

    public List<Booking> getAllBokingByUserId(long userId) {
        return this.bookingRepository.findAllByUser_Id(userId);

    }

    public Booking getBookingById(long id) {
        return this.bookingRepository.findById(id);
    }

    // huỷ phòng nếu trạng thái đang PENDING và trước ngày nhận phòng
    public boolean canCancel(Booking booking) {
        return booking.getBookingStatus() != BookingStatus.CANCELLED
                && booking.getBookingStatus() != BookingStatus.CONFIRMED
                && booking.getCheckInDate().isAfter(LocalDate.now()); // Huỷ phòng trước ngày nhận phòng
    }

    public void updateBookingStatus(long bookingId, BookingStatus bookingStatus) {
        Booking booking = this.getBookingById(bookingId);
        if (booking != null) {
            booking.setBookingStatus(bookingStatus);
            this.bookingRepository.save(booking);
        }

    }

}

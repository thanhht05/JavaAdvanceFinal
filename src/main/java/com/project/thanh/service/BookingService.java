package com.project.thanh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thanh.domain.Booking;
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
}

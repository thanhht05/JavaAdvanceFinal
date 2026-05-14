package com.project.thanh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.thanh.domain.Booking;
import com.project.thanh.domain.BookingService;

@Repository
public interface BookingServiceRepository extends JpaRepository<BookingService, Long> {

    List<BookingService> findByBooking(Booking booking);

    List<BookingService> findByBookingId(Long bookingId);

    BookingService findById(long id);
}

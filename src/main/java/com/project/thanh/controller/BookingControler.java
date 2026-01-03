package com.project.thanh.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.project.thanh.domain.Booking;
import com.project.thanh.enums.BookingStatus;
import com.project.thanh.service.BookingService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BookingControler {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/booking-details/{id}")
    public String handleGetBookingDetailPage(@PathVariable Long id, Model model) {
        Booking booking = this.bookingService.getBookingById(id);
        long day = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        boolean canCancel = this.bookingService.canCancel(booking);
        model.addAttribute("canCancel", canCancel);
        model.addAttribute("booking", booking);
        model.addAttribute("day", day);

        return "user/booking-detail";
    }

    @PostMapping("/booking/cancel/{id}")
    public String handleCancelBooking(@PathVariable Long id) {
        Booking booking = this.bookingService.getBookingById(id);
        if (booking != null) {
            booking.setBookingStatus(BookingStatus.CANCELLED);
            this.bookingService.saveBooking(booking);
        }

        return "redirect:/booking-details/" + id;
    }

}

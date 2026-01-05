package com.project.thanh.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.project.thanh.domain.Booking;
import com.project.thanh.domain.Room;
import com.project.thanh.domain.User;
import com.project.thanh.domain.Voucher;
import com.project.thanh.dtos.BookingDTO;
import com.project.thanh.enums.BookingStatus;
import com.project.thanh.service.BookingService;
import com.project.thanh.service.RoomService;
import com.project.thanh.service.UserService;
import com.project.thanh.service.VoucherService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingControler {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/admin/bookings")
    public String handleGetBookingPage(Model model, @RequestParam(defaultValue = "1") int page) {
        int size = 6;
        Page<Booking> bookingPage = this.bookingService.getBookingPage(page, size);
        model.addAttribute("curPage", page);
        model.addAttribute("totalPages", bookingPage.getTotalPages());

        model.addAttribute("bookings", bookingPage.getContent());
        return "admin/booking/show";
    }

    @GetMapping("/admin/bookings/detail/{id}")
    public String handleGetDetailBookingPage(@PathVariable Long id, Model model) {
        Booking booking = this.bookingService.getBookingById(id);
        model.addAttribute("booking", booking);

        return "admin/booking/detail";
    }

    @PostMapping("/admin/bookings/update-status")
    public String handleUpdateBooking(@RequestParam Long bookingId, @RequestParam String status) {

        BookingStatus bookingStatus = BookingStatus.valueOf(status);
        this.bookingService.updateBookingStatus(bookingId, bookingStatus);

        return "redirect:/admin/bookings";
    }

    @PostMapping("/admin/bookings/confirm")
    public String handleConfirmBooking(@RequestParam Long bookingId) {

        Booking booking = this.bookingService.getBookingById(bookingId);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        this.bookingService.saveBooking(booking);

        return "redirect:/admin/bookings";
    }

    @PostMapping("/admin/bookings/cancel")
    public String handleCancelBooking(@RequestParam Long bookingId) {
        Booking booking = this.bookingService.getBookingById(bookingId);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        this.bookingService.saveBooking(booking);

        return "redirect:/admin/bookings";
    }

}

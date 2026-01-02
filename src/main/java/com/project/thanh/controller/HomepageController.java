package com.project.thanh.controller;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.thanh.domain.Booking;
import com.project.thanh.domain.Room;
import com.project.thanh.domain.User;
import com.project.thanh.dtos.BookingDTO;
import com.project.thanh.enums.BookingStatus;
import com.project.thanh.service.BookingService;
import com.project.thanh.service.RoomService;
import com.project.thanh.service.UserService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class HomepageController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getHomepage(Model model) {
        model.addAttribute("roomList", this.roomService.getAllRoom());
        return "user/home";
    }

    @GetMapping("/booking/room/{id}")
    public String getMethodName(@PathVariable Long id, Model model) {
        Room room = this.roomService.getRoomById(id);
        if (room != null) {
            model.addAttribute("room", room);
            model.addAttribute("bookingDTO", new BookingDTO());
        }
        return "user/bookingRoom";
    }

    @PostMapping("/booking/confirm")
    public String handleBookingRoom(@ModelAttribute("bookingDTO") BookingDTO bookingDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email == null) {
            return "redirect:/";
        }
        User user = this.userService.getUserByEmail(email);
        if (user == null) {
            return "redirect:/";

        }

        Room room = this.roomService.getRoomById(bookingDTO.getRoomId());
        if (room == null) {
            return "redirect:/booking/confirm";
        }

        long day = ChronoUnit.DAYS.between(bookingDTO.getCheckIn(), bookingDTO.getCheckOut());
        long price = room.getRoomType().getPrice();
        long totalPrice = price * day;

        Booking booking = new Booking();

        booking.setRoom(room);
        booking.setUser(user);
        booking.setCheckInDate(bookingDTO.getCheckIn());
        booking.setCheckOutDate(bookingDTO.getCheckOut());
        booking.setCustomerName(bookingDTO.getCustomerName());
        booking.setPhone(bookingDTO.getPhone());
        booking.setTotalPrice(totalPrice);
        booking.setFinalPrice(totalPrice);
        booking.setBookingStatus(BookingStatus.PENDING);

        this.bookingService.saveBooking(booking);

        return "redirect:/";
    }

    @GetMapping("/booking-history")
    public String getBookingHistory(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email == null) {
            return "redirect:/";
        }
        User user = this.userService.getUserByEmail(email);
        model.addAttribute("bookings", this.bookingService.getAllBokingByUserId(user.getId()));
        return "user/bookingHistory";
    }

}

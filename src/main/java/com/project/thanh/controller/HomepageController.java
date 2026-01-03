package com.project.thanh.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.project.thanh.domain.Voucher;
import com.project.thanh.dtos.BookingDTO;
import com.project.thanh.enums.BookingStatus;
import com.project.thanh.service.BookingService;
import com.project.thanh.service.RoomService;
import com.project.thanh.service.UserService;
import com.project.thanh.service.VoucherService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public String getHomepage(@RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer capacity, Model model, @RequestParam(defaultValue = "1") int page) {
        int size = 6;
        Page<Room> romPage = this.roomService.getRommPage(page, size);

        model.addAttribute("roomList", romPage.getContent()); // current room int page
        model.addAttribute("curPage", page);
        model.addAttribute("totalPages", romPage.getTotalPages());

        return "user/home";
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

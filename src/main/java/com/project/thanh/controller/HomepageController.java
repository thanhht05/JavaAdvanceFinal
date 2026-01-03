package com.project.thanh.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

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
    private VoucherService voucherService;

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
    public String handleBookingRoom(
            @ModelAttribute("bookingDTO") BookingDTO bookingDTO,
            RedirectAttributes redirectAttributes) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email == null || email.equals("anonymousUser")) {
            return "redirect:/";
        }

        User user = userService.getUserByEmail(email);
        if (user == null) {
            return "redirect:/login";
        }

        Room room = roomService.getRoomById(bookingDTO.getRoomId());
        if (room == null) {
            return "redirect:/";
        }

        // 3. TÍNH SỐ NGÀY Ở

        long days = ChronoUnit.DAYS.between(
                bookingDTO.getCheckIn(),
                bookingDTO.getCheckOut());

        if (days <= 0) {
            redirectAttributes.addFlashAttribute("error", "Ngày trả phòng không hợp lệ");
            return "redirect:/booking/room/" + bookingDTO.getRoomId();
        }

        long pricePerDay = room.getRoomType().getPrice();
        long totalPrice = days * pricePerDay;

        // 4. XỬ LÝ VOUCHER

        String voucherCode = bookingDTO.getVoucherCode();
        Voucher voucher = null;
        long discount = 0;

        if (voucherCode != null && !voucherCode.trim().isEmpty()) {

            voucher = voucherService.getVoucherByCode(voucherCode);

            if (voucher == null
                    || voucherService.checkVoucherExpired(voucher)
                    || voucherService.checkUsageVoucher(voucher)) {

                redirectAttributes.addFlashAttribute(
                        "error", "Voucher không hợp lệ hoặc đã hết hạn");
                return "redirect:/booking/room/" + bookingDTO.getRoomId();
            }

            long discountByPercent = totalPrice * voucher.getDiscountValue() / 100;
            discount = Math.min(discountByPercent, voucher.getMaxDiscount());

            voucher.setUsageLimit(voucher.getUsageLimit() - 1);
        }

        long finalPrice = totalPrice - discount;

        // 5. TẠO BOOKING

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckInDate(bookingDTO.getCheckIn());
        booking.setCheckOutDate(bookingDTO.getCheckOut());
        booking.setCustomerName(bookingDTO.getCustomerName());
        booking.setPhone(bookingDTO.getPhone());

        booking.setTotalPrice(totalPrice);
        booking.setFinalPrice(finalPrice);
        booking.setDiscountAmount(discount);
        booking.setBookingStatus(BookingStatus.PENDING);

        if (voucher != null) {
            booking.setVoucher(voucher);
        }

        bookingService.saveBooking(booking);

        return "redirect:/booking-history";
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

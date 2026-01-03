package com.project.thanh.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class BookingControler {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private UserService userService;

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

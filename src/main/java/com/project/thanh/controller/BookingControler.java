package com.project.thanh.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.project.thanh.domain.Booking;

import com.project.thanh.domain.InvoiceDetail;
import com.project.thanh.domain.Room;
import com.project.thanh.domain.Service;
import com.project.thanh.domain.User;
import com.project.thanh.domain.Voucher;
import com.project.thanh.dtos.BookingDTO;
import com.project.thanh.enums.BookingStatus;
import com.project.thanh.repository.InvoiceDetailRepository;
import com.project.thanh.service.BookingService;
import com.project.thanh.service.BookingServiceService;
import com.project.thanh.service.InvoiceDetailService;
import com.project.thanh.service.InvoiceService;
import com.project.thanh.service.RoomService;
import com.project.thanh.service.ServiceService;
import com.project.thanh.service.UserService;
import com.project.thanh.service.VoucherService;
import com.project.thanh.domain.Invoice;
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
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceDetailService invoiceDetailService;
    @Autowired
    private BookingServiceService bookingServiceService;
    @Autowired
    private ServiceService serviceService;

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

        Invoice invoice = invoiceService.getInvoiceByBookingId(id);
        List<Service> services = serviceService.getAllServices();

        model.addAttribute("services", services);
        List<InvoiceDetail> invoiceDetails = invoiceDetailService.getByInvoice(invoice);
        List<com.project.thanh.domain.BookingService> serviceDetails = bookingServiceService.getByBookingId(id);
        model.addAttribute("serviceDetails", serviceDetails);
        model.addAttribute("booking", booking);
        model.addAttribute("invoice", invoice);
        model.addAttribute("invoiceDetails", invoiceDetails);

        long serviceTotal = serviceDetails.stream()
                .mapToLong(bs -> bs.getQuantity() * bs.getUnitPrice())
                .sum();

        model.addAttribute("serviceTotal", serviceTotal);
        return "admin/booking/detail";
    }

    @PostMapping("/admin/bookings/update-status")
    public String handleUpdateBooking(@RequestParam Long bookingId, @RequestParam String status) {

        BookingStatus bookingStatus = BookingStatus.valueOf(status);
        this.bookingService.updateBookingStatus(bookingId, bookingStatus);

        return "redirect:/admin/bookings/detail/" + bookingId;
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

    @PostMapping("/admin/bookings/add-service")
    public String addService(
            @RequestParam Long bookingId,
            @RequestParam Long serviceId,
            @RequestParam int quantity) {

        Booking booking = this.bookingService.getBookingById(bookingId);
        Service service = serviceService.getServiceById(serviceId);

        /*
         * =========================
         * SAVE BOOKING SERVICE
         * =========================
         */

        com.project.thanh.domain.BookingService bookingService = new com.project.thanh.domain.BookingService();

        bookingService.setBooking(booking);

        bookingService.setService(service);

        bookingService.setQuantity(quantity);

        bookingService.setUnitPrice(service.getDefaultPrice());

        bookingService.setTotalPrice(
                quantity * service.getDefaultPrice());

        this.bookingServiceService.save(bookingService);

        /*
         * =========================
         * UPDATE INVOICE DETAIL
         * =========================
         */

        // Invoidce
        Invoice invoice = invoiceService.getInvoiceByBookingId(bookingId);

        // Invoice invoice = new Invoice();

        InvoiceDetail invoiceDetail = new InvoiceDetail();

        invoiceDetail.setInvoice(invoice);

        invoiceDetail.setService(service);

        invoiceDetail.setDescription(service.getServiceName());

        invoiceDetail.setQuantity(quantity);

        invoiceDetail.setUnit(service.getUnit());

        invoiceDetail.setUnitPrice(service.getDefaultPrice());

        invoiceDetail.setTotalPrice(
                quantity * service.getDefaultPrice());

        invoiceDetailService.save(invoiceDetail);

        /*
         * =========================
         * UPDATE INVOICE TOTAL
         * =========================
         */
        List<com.project.thanh.domain.BookingService> serviceDetails = bookingServiceService
                .getByBookingId(booking.getId());

        long serviceTotal = serviceDetails.stream()
                .mapToLong(bs -> bs.getQuantity() * bs.getUnitPrice())
                .sum();

        invoice.setServiceTotal(serviceTotal);

        invoice.setTotalAmount(
                invoice.getRoomTotal() + serviceTotal);

        invoiceService.save(invoice);

        return "redirect:/admin/bookings/detail/" + bookingId;
    }

}

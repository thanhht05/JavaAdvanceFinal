package com.project.thanh.service;

import com.project.thanh.domain.Booking;
import com.project.thanh.domain.BookingService;
import com.project.thanh.domain.Service;
import com.project.thanh.repository.BookingRepository;
import com.project.thanh.repository.BookingServiceRepository;
import com.project.thanh.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class BookingServiceService {

    @Autowired
    private BookingServiceRepository bookingServiceRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    public List<BookingService> getByBookingId(Long bookingId) {
        return bookingServiceRepository.findByBookingId(bookingId);
    }

    public void save(BookingService bs) {
        this.bookingServiceRepository.save(bs);
    }

    public void addService(Long bookingId,
            Long serviceId,
            int quantity) {

        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Service service = serviceRepository
                .findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        BookingService bookingService = new BookingService();

        bookingService.setBooking(booking);

        bookingService.setService(service);

        bookingService.setQuantity(quantity);

        bookingService.setUnitPrice(service.getDefaultPrice());

        bookingService.setTotalPrice(
                quantity * service.getDefaultPrice());

        bookingServiceRepository.save(bookingService);
    }

    public void deleteById(Long id) {
        bookingServiceRepository.deleteById(id);
    }

    public long getTotalServicePrice(Long bookingId) {

        List<BookingService> services = bookingServiceRepository.findByBookingId(bookingId);

        return services.stream()
                .mapToLong(BookingService::getTotalPrice)
                .sum();
    }

}
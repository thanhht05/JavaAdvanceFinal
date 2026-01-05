package com.project.thanh.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.thanh.domain.Booking;
import com.project.thanh.domain.Room;
import com.project.thanh.enums.BookingStatus;
import com.project.thanh.repository.BookingRepository;
import com.project.thanh.repository.RoomRepository;
import com.project.thanh.specification.RoomSpecification;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public List<Room> getAllRoom() {
        return this.roomRepository.findAll();
    }

    public Page<Room> getRommPage(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return this.roomRepository.findAll(pageable);
    }

    public Room getRoomById(long id) {
        return this.roomRepository.findById(id);
    }

    public void saveRoom(Room room) {
        this.roomRepository.save(room);
    }

    public Page<Room> getRooms(
            Integer capacity,
            String type,
            Long minPrice,
            Long maxPrice,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Specification<Room> spec = Specification
                .where(RoomSpecification.hasCapacity(capacity)
                        .and(RoomSpecification.hasType(type))
                        .and(RoomSpecification.priceBetween(minPrice, maxPrice)));

        return roomRepository.findAll(spec, pageable);
    }

    @Scheduled(cron = "0 0 0 * * * ") // 00:00 mỗi ngày

    @Transactional
    public void autoChecking() {
        LocalDate today = LocalDate.now();
        List<Booking> bookings = bookingRepository.findByBookingStatusAndCheckInDate(
                BookingStatus.CONFIRMED,
                today);
        for (Booking booking : bookings) {
            booking.setBookingStatus(BookingStatus.CHECKED_IN);
        }
        System.out.println("AUTO INCHEC RUN AT " + LocalDateTime.now());

    }

    @Scheduled(cron = "0 0 0 * * *") // 00:00 mỗi ngày
    // @Scheduled(cron = "0 * * * * *") // mỗi phút

    @Transactional
    public void autoCheckout() {

        LocalDate today = LocalDate.now();

        List<Booking> bookings = bookingRepository.findByCheckOutDateBefore(today);

        for (Booking booking : bookings) {

            if (booking.getBookingStatus() == BookingStatus.CHECKED_IN) {
                booking.setBookingStatus((BookingStatus.COMPLETED));
            }
        }
        System.out.println("AUTO CHECKOUT RUN AT " + LocalDateTime.now());
    }

    public boolean checkDeleteRoom(Long roomId) {

        Room room = this.getRoomById(roomId);
        if (room == null) {
            return false;

        }
        List<Booking> bookings = bookingRepository.findByRoomId(roomId);

        for (Booking booking : bookings) {

            // Chỉ cần 1 booking đang hoạt động → không xóa
            if (booking.getBookingStatus() == BookingStatus.PENDING
                    || booking.getBookingStatus() == BookingStatus.CONFIRMED
                    || booking.getBookingStatus() == BookingStatus.CHECKED_IN) {

                return false;
            }
        }
        return true;

    }

}

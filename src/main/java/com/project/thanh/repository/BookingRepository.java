package com.project.thanh.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.thanh.domain.Booking;
import com.project.thanh.enums.BookingStatus;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUser_Id(Long userId);

    Booking findById(long id);

    List<Booking> findByCheckOutDateBefore(LocalDate date);

    List<Booking> findByBookingStatusAndCheckInDate(BookingStatus status, LocalDate date);

    List<Booking> findByRoomId(Long roomId);

    Page<Booking> findAll(Pageable pageable);

}

package com.project.thanh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thanh.domain.BookingDetail;
import com.project.thanh.repository.BookingDetailRepository;

@Service
public class BookingDetailService {
    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    public void save(BookingDetail bookingDetail) {
        this.bookingDetailRepository.save(bookingDetail);
    }
}

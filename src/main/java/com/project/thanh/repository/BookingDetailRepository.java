package com.project.thanh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.thanh.domain.BookingDetail;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {
    BookingDetail findById(long id);
}

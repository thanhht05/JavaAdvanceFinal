package com.project.thanh.controller.admin;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.thanh.domain.Booking;
import com.project.thanh.repository.BookingRepository;
import com.project.thanh.service.BookingExcelExport;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class BookingExportController {

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/admin/bookings/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {

        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(
                "Content-Disposition", "attachment; filename=bookings.xlsx");

        List<Booking> bookings = bookingRepository.findAll();

        BookingExcelExport exporter = new BookingExcelExport(bookings);
        exporter.export(response.getOutputStream());
    }
}

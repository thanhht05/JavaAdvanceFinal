package com.project.thanh.service;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.project.thanh.domain.Booking;

public class BookingExcelExport {
    private List<Booking> bookings;

    public BookingExcelExport(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void export(OutputStream outputStream) {
        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Bookings");

            // ===== Header =====
            Row headerRow = sheet.createRow(0);
            String[] columns = { "ID", "Customer", "Phone", "Check-in", "Check-out", "Discount", "Total Price",
                    "Final price" };

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // ===== Data =====
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            int rowIndex = 1;

            for (Booking booking : bookings) {
                Row row = sheet.createRow(rowIndex++);

                row.createCell(0).setCellValue(booking.getId());
                row.createCell(1).setCellValue(booking.getCustomerName());
                row.createCell(2).setCellValue(booking.getPhone());
                row.createCell(3).setCellValue(booking.getCheckInDate().format(formatter));
                row.createCell(4).setCellValue(booking.getCheckOutDate().format(formatter));
                row.createCell(5).setCellValue(booking.getDiscountAmount());
                row.createCell(6).setCellValue(booking.getTotalPrice());
                row.createCell(7).setCellValue(booking.getFinalPrice());
            }

            // Auto size column
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);

        } catch (Exception e) {
            throw new RuntimeException("Error exporting Excel: " + e.getMessage());
        }
    }

}

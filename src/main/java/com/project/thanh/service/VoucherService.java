package com.project.thanh.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thanh.domain.Voucher;
import com.project.thanh.repository.VoucherRepository;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    public Voucher getVoucherByCode(String code) {
        return this.voucherRepository.findByCode(code);
    }

    public boolean checkVoucherExpired(Voucher voucher) {
        LocalDate today = LocalDate.now();
        return today.isAfter(voucher.getEndDate());
    }

    public boolean checkUsageVoucher(Voucher voucher) {

        return voucher.getUsageLimit() == 0;
    }

}

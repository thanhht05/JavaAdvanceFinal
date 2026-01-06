package com.project.thanh.service;

import java.time.LocalDate;
import java.util.List;

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

    public Voucher getVoucherById(long id) {
        return this.voucherRepository.findById(id);
    }

    public boolean checkVoucherExpired(Voucher voucher) {
        LocalDate today = LocalDate.now();
        return today.isAfter(voucher.getEndDate());
    }

    public boolean checkUsageVoucher(Voucher voucher) {

        return voucher.getUsageLimit() == 0;
    }

    public List<Voucher> getAll() {
        return this.voucherRepository.findAll();
    }

    public void saveVoucher(Voucher voucher) {
        this.voucherRepository.save(voucher);
    }

    public void deleteVoucher(Long id) {
        this.voucherRepository.deleteById(id);
    }

}

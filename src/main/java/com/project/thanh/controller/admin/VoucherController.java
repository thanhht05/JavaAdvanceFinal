package com.project.thanh.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.thanh.domain.Voucher;
import com.project.thanh.service.VoucherService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class VoucherController {
    @Autowired
    private VoucherService voucherService;

    @GetMapping("/admin/vouchers")
    public String getVoucherPage(Model model) {
        model.addAttribute("voucherList", this.voucherService.getAll());
        return "admin/voucher/show";
    }

    @GetMapping("/admin/vouchers/create")
    public String handleGetCreateVoucherPage(Model model) {
        model.addAttribute("voucher", new Voucher());
        return "admin/voucher/create";
    }

    @PostMapping("/admin/vouchers/create")
    public String createVoucher(@ModelAttribute Voucher voucher) {
        this.voucherService.saveVoucher(voucher);

        return "redirect:/admin/vouchers";
    }

    @GetMapping("/admin/vouchers/update/{id}")
    public String getMethodName(@PathVariable Long id, Model model) {
        Voucher voucher = this.voucherService.getVoucherById(id);
        model.addAttribute("voucher", voucher);

        return "admin/voucher/update";
    }

    @PostMapping("/admin/vouchers/update")
    public String postMethodName(@ModelAttribute Voucher voucher) {
        Voucher updateVoucher = this.voucherService.getVoucherById(voucher.getId());
        if (updateVoucher != null) {
            updateVoucher.setCode(voucher.getCode());
            updateVoucher.setDiscountValue(voucher.getDiscountValue());
            updateVoucher.setEndDate(voucher.getEndDate());
            updateVoucher.setStartDate(voucher.getStartDate());
            updateVoucher.setMaxDiscount(voucher.getMaxDiscount());
            updateVoucher.setUsageLimit(voucher.getUsageLimit());

            this.voucherService.saveVoucher(updateVoucher);
        }
        return "redirect:/admin/vouchers";

    }

    @GetMapping("/admin/vouchers/delete/{id}")
    public String handleDeleteVoucer(@PathVariable Long id) {
        this.voucherService.deleteVoucher(id);

        return "redirect:/admin/vouchers";

    }

}

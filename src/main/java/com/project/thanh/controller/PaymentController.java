package com.project.thanh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {
    @GetMapping("/payment")
    public String getPaymentPage() {
        return "user/pay";
    }

}

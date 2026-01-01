package com.project.thanh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("newBooking", 12);
        model.addAttribute("availableRoom", 35);
        model.addAttribute("checkInToday", 5);
        model.addAttribute("checkOutToday", 3);
        model.addAttribute("pendingBooking", 4);
        model.addAttribute("doneBooking", 8);

        return "admin/dashboard";
    }

}

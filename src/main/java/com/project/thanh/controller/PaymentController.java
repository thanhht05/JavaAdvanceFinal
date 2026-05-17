package com.project.thanh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.thanh.domain.Room;
import com.project.thanh.service.RoomService;

@Controller
public class PaymentController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/payment")
    public String getPaymentPage(@RequestParam long roomId,
            @RequestParam long totalPrice,
            @RequestParam long stayDays,
            Model model) {
        Room room = roomService.getRoomById(roomId);

        model.addAttribute("room", room);
        model.addAttribute("stayDays", stayDays);
        model.addAttribute("totalPrice", totalPrice);

        return "user/pay";
    }

}

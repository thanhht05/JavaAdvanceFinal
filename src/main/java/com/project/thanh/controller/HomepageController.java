package com.project.thanh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.thanh.domain.Room;
import com.project.thanh.service.RoomService;

@Controller
public class HomepageController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/")
    public String getHomepage(Model model) {
        model.addAttribute("roomList", this.roomService.getAllRoom());
        return "user/home";
    }

}

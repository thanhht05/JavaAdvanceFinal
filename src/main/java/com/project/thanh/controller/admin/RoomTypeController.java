package com.project.thanh.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.thanh.domain.RoomType;
import com.project.thanh.service.RoomTypeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RoomTypeController {
    @Autowired
    private RoomTypeService roomTypeService;

    @GetMapping("/admin/room-types")
    public String getRoomTypPage(Model model) {
        model.addAttribute("roomTypeList", this.roomTypeService.getAll());
        return "admin/type/show";
    }

    @GetMapping("/admin/room-types/create")
    public String getCreateRoomTypePage(Model model) {
        model.addAttribute("roomType", new RoomType());
        return "admin/type/create";
    }

    @PostMapping("/admin/room-types/create")
    public String createRoomType(@ModelAttribute RoomType roomType) {

        this.roomTypeService.saveRoomType(roomType);

        return "redirect:/admin/room-types";

    }

}

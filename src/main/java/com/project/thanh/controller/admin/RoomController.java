package com.project.thanh.controller.admin;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.project.thanh.domain.Room;
import com.project.thanh.service.BookingService;
import com.project.thanh.service.RoomService;
import com.project.thanh.service.RoomTypeService;
import com.project.thanh.service.UploadService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class RoomController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/admin/room")
    public String handleGetRoomPage(Model model) {
        model.addAttribute("rooms", this.roomService.getAllRoom());
        return "admin/room/show";
    }

    @GetMapping("/admin/create-room")
    public String getCreateRoomPage(Model model) {

        model.addAttribute("room", new Room());
        model.addAttribute("roomTypes", this.roomTypeService.getAll());

        return "admin/room/create";
    }

    @PostMapping("/admin/create-room")
    public String postMethodName(@ModelAttribute Room room,
            @RequestParam("imgRoom") MultipartFile file)
            throws IOException {

        String img = this.uploadService.handleUpLoadFile(file, "img");
        room.setImg(img);
        this.roomService.saveRoom(room);

        return "redirect:/admin/dashboard";
    }

}

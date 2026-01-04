package com.project.thanh.controller.admin;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.thanh.domain.Room;
import com.project.thanh.domain.RoomType;
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
    public String handleGetRoomPage(Model model, @RequestParam(defaultValue = "1") int page) {
        int size = 6;
        Page<Room> roomPages = this.roomService.getRommPage(page, size);
        model.addAttribute("curPage", page);
        model.addAttribute("rooms", roomPages.getContent());
        model.addAttribute("totalPages", roomPages.getTotalPages());
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

    @GetMapping("/admin/rooms/edit/{id}")
    public String getUpdateRoomPage(@PathVariable Long id, Model model) {
        Room room = this.roomService.getRoomById(id);
        model.addAttribute("room", room);
        model.addAttribute("roomTypes", this.roomTypeService.getAll());
        return "admin/room/update";
    }

    @PostMapping("/admin/rooms/update")
    public String handleUpdateRoom(@ModelAttribute("room") Room room, @RequestParam("imageFile") MultipartFile file)
            throws IOException {

        Room updateRoom = this.roomService.getRoomById(room.getId());

        updateRoom.setDescription(room.getDescription());
        updateRoom.setFlor(room.getFlor());
        updateRoom.setRoomNumber(room.getRoomNumber());
        String img = this.uploadService.handleUpLoadFile(file, "img");
        updateRoom.setImg(img);

        RoomType roomType = this.roomTypeService.getRoomTypeById(room.getRoomType().getId());

        updateRoom.setRoomType(roomType);
        this.roomService.saveRoom(updateRoom);

        return "redirect:/admin/room";
    }

}

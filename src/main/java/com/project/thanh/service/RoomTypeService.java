package com.project.thanh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thanh.domain.RoomType;
import com.project.thanh.repository.RoomTypeRepository;

@Service
public class RoomTypeService {
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    public List<RoomType> getAll() {
        return this.roomTypeRepository.findAll();
    }

    public RoomType getRoomTypeById(long id) {
        return this.roomTypeRepository.findById(id);
    }

    public void saveRoomType(RoomType roomType) {
        this.roomTypeRepository.save(roomType);
    }
}

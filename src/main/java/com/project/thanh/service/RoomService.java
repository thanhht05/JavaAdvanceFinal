package com.project.thanh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thanh.domain.Room;
import com.project.thanh.repository.RoomRepository;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRoom() {
        return this.roomRepository.findAll();
    }

    public Room getRoomById(long id) {
        return this.roomRepository.findById(id);
    }
}

package com.project.thanh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.project.thanh.domain.Room;
import com.project.thanh.repository.RoomRepository;
import com.project.thanh.specification.RoomSpecification;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRoom() {
        return this.roomRepository.findAll();
    }

    public Page<Room> getRommPage(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return this.roomRepository.findAll(pageable);
    }

    public Room getRoomById(long id) {
        return this.roomRepository.findById(id);
    }

    public void saveRoom(Room room) {
        this.roomRepository.save(room);
    }

    public Page<Room> getRooms(
            Integer capacity,
            String type,
            Long minPrice,
            Long maxPrice,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Specification<Room> spec = Specification
                .where(RoomSpecification.hasCapacity(capacity)
                        .and(RoomSpecification.hasType(type))
                        .and(RoomSpecification.priceBetween(minPrice, maxPrice)));

        return roomRepository.findAll(spec, pageable);
    }

}

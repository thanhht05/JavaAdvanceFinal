package com.project.thanh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.thanh.domain.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAll();

    Room findById(long id);
}

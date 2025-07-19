package com.example.demo.repository;

import com.example.demo.entity.CinemaRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CinemaRoomRepository extends JpaRepository<CinemaRoom, Long> {
    boolean existsByCinemaRoomNameAndIsDeletedFalse(String cinemaRoomName);
    List<CinemaRoom> findByIsDeletedFalse();
}

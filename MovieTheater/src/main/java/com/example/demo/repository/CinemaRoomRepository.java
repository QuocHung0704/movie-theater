package com.example.demo.repository;

import com.example.demo.entity.CinemaRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRoomRepository extends JpaRepository<CinemaRoom, Long> {
    boolean existsByCinemaRoomNameAndIsDeletedFalse(String cinemaRoomName);
}

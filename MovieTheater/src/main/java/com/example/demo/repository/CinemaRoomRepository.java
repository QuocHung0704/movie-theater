package com.example.demo.repository;

import com.example.demo.entity.CinemaRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CinemaRoomRepository extends JpaRepository<CinemaRoom, Long> {
    boolean existsByCinemaRoomNameAndIsDeletedFalse(String cinemaRoomName);
    List<CinemaRoom> findByIsDeletedFalse();
    Optional<CinemaRoom> getCinemaRoomByCinemaRoomIdAndIsDeletedFalse(Long cinemaRoomId);
    boolean existsByCinemaRoomNameAndIsDeletedFalseAndCinemaRoomIdNot(String cinemaRoomName, Long cinemaRoomId);
}

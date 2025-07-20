package com.example.demo.repository;

import com.example.demo.entity.CinemaRoom;
import com.example.demo.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findBySeatId(Long seatId);

    List<Seat> findSeatsByCinemaRoom(CinemaRoom cinemaRoom);
    @Query("SELECT s FROM Seat s WHERE s.cinemaRoom = :cinemaRoom AND s.seatStatus = true")
    List<Seat> findActiveSeatsByCinemaRoom(@Param("cinemaRoom") CinemaRoom cinemaRoom);
}

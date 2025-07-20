package com.example.demo.service;

import com.example.demo.entity.CinemaRoom;
import com.example.demo.entity.Seat;
import com.example.demo.entity.request.CinemaRoomRequest;
import com.example.demo.entity.request.SeatRequest;
import com.example.demo.entity.response.CinemaRoomResponse;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CinemaRoomService {
    CinemaRoom createCinemaRoom(CinemaRoomRequest cinemaRoomRequest);
    List<CinemaRoom> getAllCinemaRooms();
    Optional<CinemaRoom> getCinemaRoomById(Long id);
    CinemaRoomResponse updateCinemaRoomById(Long id, CinemaRoomRequest cinemaRoomRequest);
    CinemaRoom deleteCinemaRoomById(Long id);

    List<Seat> createSeatsForRoomManually(Long cinemaRoomId, List<SeatRequest> seatRequests);
    List<Seat> createSeatsForRoomAutomatically(Long cinemaRoomId, int rows, int columns);
    List<Seat> getSeatsByCinemaRoom(Long cinemaRoomId);
}

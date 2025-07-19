package com.example.demo.service;

import com.example.demo.entity.CinemaRoom;
import com.example.demo.entity.request.CinemaRoomRequest;

import java.util.List;

public interface CinemaRoomService {
    CinemaRoom createCinemaRoom(CinemaRoomRequest cinemaRoomRequest);
    List<CinemaRoom> getAllCinemaRooms();
}

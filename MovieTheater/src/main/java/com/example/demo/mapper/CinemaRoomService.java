package com.example.demo.mapper;

import com.example.demo.entity.CinemaRoom;
import com.example.demo.entity.request.CinemaRoomRequest;

public interface CinemaRoomService {
    CinemaRoom createCinemaRoom(CinemaRoomRequest cinemaRoomRequest);
}

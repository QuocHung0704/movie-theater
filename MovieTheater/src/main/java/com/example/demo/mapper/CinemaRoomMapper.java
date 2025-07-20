package com.example.demo.mapper;

import com.example.demo.entity.CinemaRoom;
import com.example.demo.entity.request.CinemaRoomRequest;
import com.example.demo.entity.response.CinemaRoomResponse;

public interface CinemaRoomMapper {
    CinemaRoomResponse toCinemaRoomResponse(CinemaRoom cinemaRoom);
    CinemaRoom updatedCinemaRoom(CinemaRoom cinemaRoom, CinemaRoomRequest cinemaRoomRequest);
}

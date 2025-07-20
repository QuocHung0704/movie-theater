package com.example.demo.mapper;

import com.example.demo.entity.CinemaRoom;
import com.example.demo.entity.Seat;
import com.example.demo.entity.request.SeatRequest;

public interface SeatMapper {
    Seat toSeatResponse(SeatRequest seatRequest, CinemaRoom cinemaRoom);

}

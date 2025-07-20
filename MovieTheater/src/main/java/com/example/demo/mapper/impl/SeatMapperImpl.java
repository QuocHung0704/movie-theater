package com.example.demo.mapper.impl;

import com.example.demo.entity.CinemaRoom;
import com.example.demo.entity.Seat;
import com.example.demo.entity.request.CinemaRoomRequest;
import com.example.demo.entity.request.SeatRequest;
import com.example.demo.enums.SeatType;
import com.example.demo.mapper.SeatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatMapperImpl implements SeatMapper {

    @Override
    public Seat toSeatResponse(SeatRequest seatRequest, CinemaRoom cinemaRoom) {
        return Seat.builder()
                .seatColumn(seatRequest.getSeatColumn())
                .seatRow(Long.valueOf(seatRequest.getSeatRow()))
                .seatStatus(seatRequest.getSeatStatus() != null ? seatRequest.getSeatStatus() : true)
                .seatType(seatRequest.getSeatType() != null ? seatRequest.getSeatType() : SeatType.NORMAL)
                .additionalPrice(seatRequest.getAdditionalPrice() != null ? seatRequest.getAdditionalPrice() : 0L)
                .isCouple(seatRequest.getIsCouple() != null ? seatRequest.getIsCouple() : false)
                .couplePartner(seatRequest.getCouplePartner())
                .position(seatRequest.getPosition() != null ? seatRequest.getPosition() : "center")
                .cinemaRoom(cinemaRoom)
                .build();
    }

}

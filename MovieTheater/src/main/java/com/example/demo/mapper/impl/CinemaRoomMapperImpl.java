package com.example.demo.mapper.impl;

import com.example.demo.entity.CinemaRoom;
import com.example.demo.entity.request.CinemaRoomRequest;
import com.example.demo.entity.response.CinemaRoomResponse;
import com.example.demo.mapper.CinemaRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CinemaRoomMapperImpl implements CinemaRoomMapper {
    @Override
    public CinemaRoomResponse toCinemaRoomResponse(CinemaRoom cinemaRoom) {
        return CinemaRoomResponse.builder()
                .cinemaRoomId(cinemaRoom.getCinemaRoomId())
                .cinemaRoomName(cinemaRoom.getCinemaRoomName())
                .baseTicketPrice(cinemaRoom.getBaseTicketPrice())
                .isDeleted(cinemaRoom.getIsDeleted() != null ? cinemaRoom.getIsDeleted() : false)
                .totalRows(cinemaRoom.getTotalRows())
                .totalColumns(cinemaRoom.getTotalColumns())
                .walkingLanePosition(cinemaRoom.getWalkingLanePosition())
                .supportedFilmVersions(cinemaRoom.getSupportedFilmVersions())
                .build();
    }

    @Override
    public CinemaRoom updatedCinemaRoom(CinemaRoom cinemaRoom, CinemaRoomRequest cinemaRoomRequest) {
        cinemaRoom.setCinemaRoomName(cinemaRoomRequest.getCinemaRoomName());
        cinemaRoom.setBaseTicketPrice(cinemaRoomRequest.getBaseTicketPrice());
        cinemaRoom.setIsDeleted(cinemaRoomRequest.isDeleted());
        cinemaRoom.setTotalRows(cinemaRoomRequest.getTotalRows());
        cinemaRoom.setTotalColumns(cinemaRoomRequest.getTotalColumns());
        cinemaRoom.setWalkingLanePosition(cinemaRoomRequest.getWalkingLanePosition());
        cinemaRoom.setSupportedFilmVersions(cinemaRoomRequest.getSupportedFilmVersions());
        return cinemaRoom;
    }


}

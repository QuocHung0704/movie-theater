package com.example.demo.service.impl;

import com.example.demo.entity.CinemaRoom;
import com.example.demo.entity.request.CinemaRoomRequest;
import com.example.demo.entity.response.CinemaRoomResponse;
import com.example.demo.mapper.CinemaRoomMapper;
import com.example.demo.service.CinemaRoomService;
import com.example.demo.repository.CinemaRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CinemaRoomImpl implements CinemaRoomService {
    private final CinemaRoomRepository cinemaRoomRepository;
    private final CinemaRoomMapper cinemaRoomMapper;

    @Override
    public CinemaRoom createCinemaRoom(CinemaRoomRequest cinemaRoomRequest) {
        boolean nameExists = cinemaRoomRepository.existsByCinemaRoomNameAndIsDeletedFalse(cinemaRoomRequest.getCinemaRoomName());
        if (nameExists) {
            throw new RuntimeException("Cinema room name already exists: " + cinemaRoomRequest.getCinemaRoomName());
        }
    CinemaRoom cinemaRoom = new CinemaRoom();
        cinemaRoom.setCinemaRoomName(cinemaRoomRequest.getCinemaRoomName());
        cinemaRoom.setBaseTicketPrice(cinemaRoomRequest.getBaseTicketPrice() != null ? cinemaRoomRequest.getBaseTicketPrice() : 100000L);
        if(cinemaRoomRequest.getWalkingLanePosition() != null) {
        cinemaRoom.setWalkingLanePosition(cinemaRoomRequest.getWalkingLanePosition());
        }
    if (cinemaRoomRequest.getSupportedFilmVersions() != null) {
        cinemaRoom.setSupportedFilmVersions(cinemaRoomRequest.getSupportedFilmVersions());
    }

    try {
        CinemaRoom savedRoom = cinemaRoomRepository.save(cinemaRoom);
        return savedRoom;
    } catch (Exception e) {
        throw e;
        }
    }

    @Override
    public List<CinemaRoom> getAllCinemaRooms() {
        List<CinemaRoom> cinemaRooms = cinemaRoomRepository.findByIsDeletedFalse();
        if (cinemaRooms.isEmpty()) {
            throw new RuntimeException("Cinema rooms not exists");
        }
        return cinemaRooms;
    }

    @Override
    public Optional<CinemaRoom> getCinemaRoomById(Long id) {
        Optional<CinemaRoom> cinemaRoom = cinemaRoomRepository.getCinemaRoomByCinemaRoomIdAndIsDeletedFalse(id);
        if (!cinemaRoom.isPresent()) {
            throw new RuntimeException("Cinema room not exists: " + id);
        }
        return cinemaRoom;
    }

    @Override
    public CinemaRoomResponse updateCinemaRoomById(Long id, CinemaRoomRequest cinemaRoomRequest) {
        CinemaRoom existingRoom = cinemaRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cinema room not found with id: " + id));

        if (!existingRoom.getCinemaRoomName().equalsIgnoreCase(cinemaRoomRequest.getCinemaRoomName())) {
            boolean nameExists = cinemaRoomRepository.existsByCinemaRoomNameAndIsDeletedFalse(cinemaRoomRequest.getCinemaRoomName());
            if (nameExists) {
                throw new RuntimeException("Cinema room name already exists: " + cinemaRoomRequest.getCinemaRoomName());
            }
        }

        cinemaRoomMapper.updatedCinemaRoom(existingRoom, cinemaRoomRequest);
        CinemaRoom updatedRoom = cinemaRoomRepository.save(existingRoom);

        return cinemaRoomMapper.toCinemaRoomResponse(updatedRoom);
    }

    @Override
    public CinemaRoom deleteCinemaRoomById(Long id) {
        Optional<CinemaRoom> existingRoom = cinemaRoomRepository.getCinemaRoomByCinemaRoomIdAndIsDeletedFalse(id);
        if (!existingRoom.isPresent()) {
            throw new RuntimeException("Cinema room not exists: " + id);
        } else {
            CinemaRoom room = existingRoom.get();
            room.setIsDeleted(true);
            return cinemaRoomRepository.save(room);
        }
    }
}

package com.example.demo.controller;

import com.example.demo.entity.CinemaRoom;
import com.example.demo.entity.request.CinemaRoomRequest;
import com.example.demo.entity.response.CinemaRoomResponse;
import com.example.demo.service.CinemaRoomService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cinema")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
@Slf4j
public class CinemaRoomController {
    private final CinemaRoomService cinemaRoomService;

    @PostMapping("")
    public ResponseEntity<CinemaRoom> createCinemaRoom(@Valid @RequestBody CinemaRoomRequest cinemaRoomRequest) {
        try {
            CinemaRoom createdRoom = cinemaRoomService.createCinemaRoom(cinemaRoomRequest);
            return ResponseEntity.ok(createdRoom);
        } catch (Exception e) {
            log.error("Error to create cinema room: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("")
    public ResponseEntity<List<CinemaRoom>> getAllCinemaRooms() {
        return ResponseEntity.ok(cinemaRoomService.getAllCinemaRooms());
    }

    @GetMapping("{id}")
    public ResponseEntity getCinemaRoomById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(cinemaRoomService.getCinemaRoomById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<CinemaRoomResponse> updateCinemaRoom(@PathVariable("id") Long id, @Valid @RequestBody CinemaRoomRequest cinemaRoomRequest) {
        CinemaRoomResponse cinemaRoomResponse = cinemaRoomService.updateCinemaRoomById(id, cinemaRoomRequest);
        return ResponseEntity.ok(cinemaRoomResponse);
    }
}

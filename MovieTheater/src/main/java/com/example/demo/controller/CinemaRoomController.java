package com.example.demo.controller;

import com.example.demo.entity.CinemaRoom;
import com.example.demo.entity.Seat;
import com.example.demo.entity.request.CinemaRoomRequest;
import com.example.demo.entity.request.SeatRequest;
import com.example.demo.entity.response.CinemaRoomResponse;
import com.example.demo.enums.SeatType;
import com.example.demo.repository.CinemaRoomRepository;
import com.example.demo.service.CinemaRoomService;
import com.example.demo.service.SeatPricingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cinema")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
@Slf4j
public class CinemaRoomController {
    private final CinemaRoomService cinemaRoomService;
    private final CinemaRoomRepository cinemaRoomRepository;
    private final SeatPricingService seatPricingService;

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

    @DeleteMapping("{id}")
    public ResponseEntity<CinemaRoom> deleteCinemaRoom(@PathVariable("id") Long id) {
        return ResponseEntity.ok(cinemaRoomService.deleteCinemaRoomById(id));
    }

    @PostMapping("{cinemaRoomId}/seats/manually")
    public ResponseEntity<List<Seat>> createSeatForRoom(@PathVariable("cinemaRoomId") Long cinemaRoomId,
                                                        @Valid @RequestBody List<SeatRequest> seatRequest) {
        try {
            List<Seat> createdSeats = cinemaRoomService.createSeatsForRoomManually(cinemaRoomId, seatRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSeats);
        }catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("{cinemaRoomId}/seats/automatically")
    public ResponseEntity<List<Seat>> createSeatsForRoomAutomatically(@PathVariable("cinemaRoomId") Long cinemaRoomId,
                                                                      @RequestParam("rows") int rows,
                                                                      @RequestParam("columns") int columns) {
        List<Seat> createSeats = cinemaRoomService.createSeatsForRoomAutomatically(cinemaRoomId, rows, columns);
        return ResponseEntity.status(HttpStatus.CREATED).body(createSeats);
    }

    @GetMapping("{cinemaRoomId}/seats")
    public ResponseEntity<List<Seat>> getSeatsByCinemaRoom(@PathVariable("cinemaRoomId") Long cinemaRoomId) {
        List<Seat> seats = cinemaRoomService.getSeatsByCinemaRoom(cinemaRoomId);
        return ResponseEntity.ok(seats);
    }

    @GetMapping("{cinemaRoomId}/pricing/{seatType}")
    public ResponseEntity<Map<String, Object>> getSeatPrice(@PathVariable("cinemaRoomId") Long cinemaRoomId,
                                                            @PathVariable("seatType") SeatType seatType) {
        CinemaRoom cinemaRoom = cinemaRoomRepository.findById(cinemaRoomId)
                .orElseThrow(() -> new RuntimeException("Cinema room not found"));
        Long price = seatPricingService.calculateSeatPricing(cinemaRoom, seatType);
        return ResponseEntity.ok(Map.of(
                "cinemaRoomId", cinemaRoomId,
                "seatType", seatType,
                "price", price,
                "basePrice", cinemaRoom.getBaseTicketPrice(),
                "multiplier", seatType.getPriceMultilier()
        ));
    }

    @GetMapping("{cinemaRoomId}/pricing")
    public ResponseEntity<Map<String, Object>> getAllSeatPricing(@PathVariable("cinemaRoomId") Long cinemaRoomId) {
        CinemaRoom cinemaRoom = cinemaRoomRepository.findById(cinemaRoomId)
                .orElseThrow(() -> new RuntimeException("Cinema room not found"));
        Map<String, Object> pricing = Map.of(
                "cinemaRoomId", cinemaRoomId,
                "basePrice", cinemaRoom.getBaseTicketPrice(),
                "seatPrices", Map.of(
                        "NORMAL", seatPricingService.calculateSeatPricing(cinemaRoom, SeatType.NORMAL),
                        "VIP", seatPricingService.calculateSeatPricing(cinemaRoom, SeatType.VIP),
                        "COUPLE", seatPricingService.calculateSeatPricing(cinemaRoom, SeatType.COUPLE)
                )
        );
        return ResponseEntity.ok(pricing);
    }
}

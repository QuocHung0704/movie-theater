package com.example.demo.service.impl;

import com.example.demo.entity.CinemaRoom;
import com.example.demo.enums.SeatType;
import com.example.demo.repository.CinemaRoomRepository;
import com.example.demo.service.SeatPricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatPricingServiceImpl implements SeatPricingService {

    @Override
    public Long calculateSeatPricing(CinemaRoom cinemaRoom, SeatType seatType) {
        if (cinemaRoom == null || seatType == null) {
            return 0L;
        }

        Long basePrice = cinemaRoom.getBaseTicketPrice() != null ?
                cinemaRoom.getBaseTicketPrice() : 100000L;
        return basePrice;
    }
}

package com.example.demo.service;

import com.example.demo.entity.CinemaRoom;
import com.example.demo.enums.SeatType;

public interface SeatPricingService {
    Long calculateSeatPricing(CinemaRoom cinemaRoom, SeatType seatType);

}

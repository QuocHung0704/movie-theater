package com.example.demo.entity;

import com.example.demo.enums.SeatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    private String seatColumn;
    private String seatRow;
    private Boolean seatStatus = true;
    @Enumerated(EnumType.STRING)
    private SeatType seatType = SeatType.NORMAL;
    private Long additionalPrice = 0L;

    private Boolean isCouple = false;

    private String couplePartner;

    private String position = "center";


    @ManyToOne
    @JoinColumn(name = "cinema_room_id")
    private CinemaRoom cinemaRoom;
}

package com.example.demo.entity.request;

import lombok.Data;

@Data
public class SeatRequest {
    private String seatColumn;
    private Long seatRow;
    private Boolean seatStatus = true;
}

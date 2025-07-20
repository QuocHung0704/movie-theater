package com.example.demo.entity.request;

import com.example.demo.enums.SeatType;
import lombok.Data;

@Data
public class SeatRequest {
    private String seatColumn;
    private String seatRow;
    private Boolean seatStatus = true;
    private SeatType seatType = SeatType.NORMAL;
    private Long additionalPrice = 0L;
    private Boolean isCouple = false;
    private String couplePartner;
    private String position = "center";
}

package com.example.demo.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CinemaRoomResponse {
    private long cinemaRoomId;
    private String cinemaRoomName;
    private Boolean isDeleted = false;
}

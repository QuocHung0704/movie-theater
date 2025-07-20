package com.example.demo.entity.response;

import com.example.demo.enums.FilmVersion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CinemaRoomResponse {
    private long cinemaRoomId;
    private String cinemaRoomName;
    private Boolean isDeleted = false;
    private Long baseTicketPrice; // 👈 Đảm bảo có dòng này
    private Integer totalRows;
    private Integer totalColumns;
    private String walkingLanePosition;
    private Set<FilmVersion> supportedFilmVersions;
}

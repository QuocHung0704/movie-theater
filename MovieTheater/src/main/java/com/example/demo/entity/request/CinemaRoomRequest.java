package com.example.demo.entity.request;

import com.example.demo.enums.FilmVersion;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaRoomRequest {
    @NotBlank(message = "Cinema room must not be blank")
    private String cinemaRoomName;

    @Min(value = 10000, message = "Base ticket price must be at least 10,000 VND")
    private Long baseTicketPrice;

    private boolean isDeleted;
    private Integer totalRows = 0;
    private Integer totalColumns = 0;

    private String walkingLanePosition;
    private Set<FilmVersion> supportedFilmVersions;
}

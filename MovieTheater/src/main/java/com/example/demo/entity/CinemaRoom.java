package com.example.demo.entity;

import com.example.demo.enums.FilmVersion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CinemaRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cinemaRoomId;
    private String cinemaRoomName;
    private Boolean isDeleted = false;

    private Long baseTicketPrice = 100000L;

    private Integer totalRows = 0;
    private Integer totalColumns = 0;

    @Column(columnDefinition = "TEXT")
    private String walkingLanePosition;

    @ElementCollection(targetClass = FilmVersion.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "cinema_room_film_versions",
            joinColumns = @JoinColumn(name = "cinema_room_id"))
    @Column(name = "film_version")
    private Set<FilmVersion> supportedFilmVersions;
}

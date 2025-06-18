package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}

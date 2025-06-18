package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showDateId;

    @Column(name = "show_time")
    private LocalDateTime showTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_id", referencedColumnName = "period_id")
    private ShowDatePeriod showDatePeriod;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_room_id", referencedColumnName = "cinemaRoomId")
    private CinemaRoom cinemaRoom;

    @OneToMany(mappedBy = "showDate", orphanRemoval = true)
    private List<ScheduleSeat> scheduleSeats;
}

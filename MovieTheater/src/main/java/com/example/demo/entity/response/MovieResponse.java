package com.example.demo.entity.response;

import com.example.demo.entity.ShowDatePeriod;
import com.example.demo.enums.MovieStatus;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponse {
    private Long id;
    private String title;
    private String director;
    private String actor;
    private String productionCompany;
    private String content;
    private Boolean isDeleted;
    private String posterUrl;
    private String trailerUrl;
    private String version;
    private List<TypeResponse> types;
    private Integer duration;
    private MovieStatus status;
    private float rating;
    private LocalDate releaseDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ShowDatePeriodResponse> showDatePeriod;
    private Boolean haveshowTime = Boolean.FALSE;
}

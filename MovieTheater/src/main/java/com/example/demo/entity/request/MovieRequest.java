package com.example.demo.entity.request;

import com.example.demo.enums.MovieStatus;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieRequest {
    private String title;
    private String director;
    private String actor;
    private String productionCompany;
    private String content;
    private String version;
    private List<Long> types;
    private Integer duration;
    private Boolean isDeleted = false;
    private String posterUrl;
    private String trailerUrl;
    private MovieStatus status;
    private Float rating;
    private String language;
}

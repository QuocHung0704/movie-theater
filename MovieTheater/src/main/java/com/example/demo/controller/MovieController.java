package com.example.demo.controller;

import com.example.demo.entity.request.MovieRequest;
import com.example.demo.entity.response.MovieResponse;
import com.example.demo.enums.MovieStatus;
import com.example.demo.service.MovieService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping("")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MovieResponse> addMovie(@Valid @RequestBody MovieRequest movieRequest) {
        MovieResponse response = movieService.addMovie(movieRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieResponse>> getMoviesByStatus(@RequestParam("status")MovieStatus status) {
        List<MovieResponse> movies = movieService.getMoviesByStatus(status);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }


}

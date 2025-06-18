package com.example.demo.service;

import com.example.demo.entity.request.MovieRequest;
import com.example.demo.entity.response.MovieResponse;
import com.example.demo.enums.MovieStatus;

import java.util.List;

public interface MovieService {
    MovieResponse addMovie(MovieRequest movieRequest);
    List<MovieResponse> getMoviesByStatus(MovieStatus movieStatus);
    MovieResponse getMovieById(Long movieId);
    MovieResponse updateMovie(Long movieId, MovieRequest movieRequest);
}

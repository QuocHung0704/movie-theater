package com.example.demo.service;

import com.example.demo.entity.request.MovieRequest;
import com.example.demo.entity.response.MovieResponse;
import com.example.demo.enums.MovieStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    MovieResponse addMovie(MovieRequest movieRequest);
    List<MovieResponse> getMoviesByStatus(MovieStatus movieStatus);
    MovieResponse getMovieById(Long movieId);
    MovieResponse updateMovie(Long movieId, MovieRequest movieRequest);
    MovieResponse deleteMovie(Long movieId);
    List<MovieResponse> importMovieFromExcel(MultipartFile file) throws IOException;

    List<MovieResponse> searchMovieByTitle(String title);
}

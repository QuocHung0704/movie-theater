package com.example.demo.mapper;

import com.example.demo.entity.Movie;
import com.example.demo.entity.request.MovieRequest;

public interface MovieMapper {
    Movie toMovie(MovieRequest movieRequest);
    Movie updateMovie(Movie movie, MovieRequest movieRequest);
}

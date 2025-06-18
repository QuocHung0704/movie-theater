package com.example.demo.service;

import com.example.demo.entity.request.MovieRequest;
import com.example.demo.entity.response.MovieResponse;

public interface MovieService {
    MovieResponse addMovie(MovieRequest movieRequest);

}

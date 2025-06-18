package com.example.demo.service.impl;

import com.example.demo.entity.Movie;
import com.example.demo.entity.Type;
import com.example.demo.entity.request.MovieRequest;
import com.example.demo.entity.response.MovieResponse;
import com.example.demo.mapper.MovieMapper;
import com.example.demo.repository.MovieRepository;
import com.example.demo.service.MovieService;
import com.example.demo.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieMapper movieMapper;
    private final TypeService typeService;
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    @Override
    public MovieResponse addMovie(MovieRequest movieRequest) {
        Movie movie = movieMapper.toMovie(movieRequest);
        List<Type> types = typeService.getTypesById(movieRequest.getTypes());
        movie.setTypes(types);
        Movie savedMovies = movieRepository.save(movie);
        return modelMapper.map(savedMovies, MovieResponse.class);
    }
}

package com.example.demo.service.impl;

import com.example.demo.entity.Movie;
import com.example.demo.entity.Type;
import com.example.demo.entity.request.MovieRequest;
import com.example.demo.entity.response.MovieResponse;
import com.example.demo.enums.MovieStatus;
import com.example.demo.exception.exceptions.MovieException;
import com.example.demo.mapper.MovieMapper;
import com.example.demo.repository.MovieRepository;
import com.example.demo.service.MovieService;
import com.example.demo.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<MovieResponse> getMoviesByStatus(MovieStatus status) {
        List<Movie> movieList = movieRepository.findMovieByStatusAndIsDeletedFalse(status);
        return movieList.stream()
                .map(movie -> modelMapper.map(movie, MovieResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public MovieResponse getMovieById(Long id) {
        Movie movie = getMovieByMovieId(id);
        return modelMapper.map(movie, MovieResponse.class);
    }

    @Override
    public MovieResponse updateMovie(Long movieId, MovieRequest movieRequest) {
        Movie oldMovie = getMovieByMovieId(movieId);
        movieMapper.updateMovie(oldMovie, movieRequest);

        List<Type> types = typeService.getTypesById(movieRequest.getTypes());
        oldMovie.setTypes(types);
        Movie updateMovie = movieRepository.save(oldMovie);
        return modelMapper.map(updateMovie, MovieResponse.class);
    }

    @Override
    public MovieResponse deleteMovie(Long movieId) {
        Movie oldMovie = getMovieByMovieId(movieId);
        oldMovie.setIsDeleted(true);
        Movie deletedMovie = movieRepository.save(oldMovie);
        return modelMapper.map(deletedMovie, MovieResponse.class);
    }


    public Movie getMovieByMovieId(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieException("Movie not found with id: " + id));
    }
}

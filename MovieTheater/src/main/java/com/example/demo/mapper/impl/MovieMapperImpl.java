package com.example.demo.mapper.impl;

import com.example.demo.entity.Movie;
import com.example.demo.entity.request.MovieRequest;
import com.example.demo.enums.MovieStatus;
import com.example.demo.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieMapperImpl implements MovieMapper {
    @Override
    public Movie toMovie(MovieRequest movieRequest) {
        return Movie.builder()
                .title(movieRequest.getTitle())
                .director(movieRequest.getDirector())
                .actor(movieRequest.getActor())
                .productCompany(movieRequest.getProductionCompany())
                .content(movieRequest.getContent())
                .posterUrl(movieRequest.getPosterUrl())
                .trailerUrl(movieRequest.getTrailerUrl())
                .version(movieRequest.getVersion())
                .duration(movieRequest.getDuration())
                .status(movieRequest.getStatus() == null ? MovieStatus.COMING_SOON : movieRequest.getStatus())
                .isDeleted(false)
                .build();
    }

    @Override
    public Movie updateMovie(Movie movie, MovieRequest request) {
        Optional.ofNullable(request.getTitle()).ifPresent(movie::setTitle);
        Optional.ofNullable(request.getDirector()).ifPresent(movie::setDirector);
        Optional.ofNullable(request.getActor()).ifPresent(movie::setActor);
        Optional.ofNullable(request.getProductionCompany()).ifPresent(movie::setProductCompany);
        Optional.ofNullable(request.getContent()).ifPresent(movie::setContent);
        Optional.ofNullable(request.getVersion()).ifPresent(movie::setVersion);
        Optional.ofNullable(request.getDuration()).ifPresent(movie::setDuration);
        Optional.ofNullable(request.getPosterUrl()).ifPresent(movie::setPosterUrl);
        Optional.ofNullable(request.getTrailerUrl()).ifPresent(movie::setTrailerUrl);
        Optional.ofNullable(request.getStatus()).ifPresent(movie::setStatus);
        Optional.ofNullable(request.getRating()).ifPresent(movie::setRating);
        return movie;
    }

}

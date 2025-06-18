package com.example.demo.mapper.impl;

import com.example.demo.entity.Movie;
import com.example.demo.entity.request.MovieRequest;
import com.example.demo.enums.MovieStatus;
import com.example.demo.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieMapperImpl implements MovieMapper {
    @Override
    public Movie toMovie(MovieRequest movieRequest) {
        return Movie.builder()
                .titleVN(movieRequest.getTitleVN())
                .titleEng(movieRequest.getTitleENG())
                .director(movieRequest.getDirector())
                .actor(movieRequest.getActor())
                .productCompany(movieRequest.getProductionCompany())
                .content(movieRequest.getContent())
                .posterUrl(movieRequest.getPosterUrl())
                .trailerUrl(movieRequest.getTrailerUrl())
                .version(movieRequest.getVersion())
                .duration(movieRequest.getDuration())
                .status(movieRequest.getStatus() == null ? MovieStatus.COMING_SOON : movieRequest.getStatus())
                .isDeleted(movieRequest.getIsDeleted() != null && movieRequest.getIsDeleted())
                .build();
    }
}

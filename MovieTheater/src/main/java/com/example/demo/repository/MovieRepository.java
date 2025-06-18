package com.example.demo.repository;

import com.example.demo.entity.Movie;
import com.example.demo.enums.MovieStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findMovieByStatusAndIsDeletedFalse(MovieStatus status);
}

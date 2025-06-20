package com.example.demo.service.impl;

import com.example.demo.entity.Movie;
import com.example.demo.entity.ShowDatePeriod;
import com.example.demo.entity.Type;
import com.example.demo.entity.request.MovieRequest;
import com.example.demo.entity.response.MovieResponse;
import com.example.demo.enums.MovieStatus;
import com.example.demo.exception.exceptions.MovieException;
import com.example.demo.mapper.MovieMapper;
import com.example.demo.repository.MovieRepository;
import com.example.demo.service.MovieService;
import com.example.demo.service.TypeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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

    @Override
    public List<MovieResponse> importMovieFromExcel(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        List<Movie> movies = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }

            MovieRequest movieRequest = MovieRequest.builder()
                    .title(row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue())
                    .director(row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue())
                    .actor(row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue())
                    .productionCompany(row.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue())
                    .content(row.getCell(4, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue())
                    .posterUrl(row.getCell(5, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue())
                    .trailerUrl(row.getCell(6, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue())
                    .version(row.getCell(7, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue())
                    .duration((int) row.getCell(9, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getNumericCellValue())
                    .build();

            Movie movie = movieMapper.toMovie(movieRequest);

            // Comedy, Drama
            String[] typeNames = row.getCell(8, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().split(", ");
            List<Type> types = typeService.getTypesByTypesName(List.of(typeNames));
            movie.setTypes(types);

            // Show Date Period: StartDate, EndDate
            List<ShowDatePeriod> periodList = new ArrayList<>();
            ShowDatePeriod datePeriod = ShowDatePeriod.builder()
                    .startDate(row.getCell(10, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getLocalDateTimeCellValue().toLocalDate())
                    .endDate(row.getCell(11, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getLocalDateTimeCellValue().toLocalDate())
                    .isActive(true)
                    .movie(movie)
                    .build();
            periodList.add(datePeriod);
            movie.setShowDatePeriods(periodList);

            movies.add(movie);
        }

        List<Movie> savedMovies = movieRepository.saveAll(movies);
        return savedMovies.stream()
                .map(movie -> modelMapper.map(movie, MovieResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieResponse> searchMovieByTitle(String title) {
        List<Movie> movieList = movieRepository.findByTitleContainingIgnoreCase(title);

        if (movieList.isEmpty()) {
            throw new EntityNotFoundException("Không tìm thấy phim " + title);
        }

        return movieList.stream()
                .map(movie -> modelMapper.map(movie, MovieResponse.class))
                .collect(Collectors.toList());
    }


    public Movie getMovieByMovieId(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieException("Movie not found with id: " + id));
    }
}

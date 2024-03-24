package com.cine.verse.controller;

import com.cine.verse.Dto.response.MoviesResponse;
import com.cine.verse.Dto.response.ReviewedMovieResponse;
import com.cine.verse.Dto.response.SearchedMovieResponse;
import com.cine.verse.Dto.response.UserResponse;
import com.cine.verse.domain.Movie;
import com.cine.verse.mappers.MovieMapper;
import com.cine.verse.mappers.ReviewMapper;
import com.cine.verse.mappers.UserMapper;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.MovieService;
import com.cine.verse.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final ReviewService reviewService;

    @GetMapping("/all")
    public ResponseEntity getMovies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Page<Movie> moviesPage = movieService.getMovies(PageRequest.of(page, size));
        if (moviesPage.isEmpty()) {
            return ResponseMessage.notFound("Movies Not Found");
        } else {
            Page<MoviesResponse> moviesResponses = moviesPage.map(MovieMapper::convertMovieToMoviesResponse);
            return ResponseMessage.ok("Success", moviesResponses.getContent());
        }
    }

    @GetMapping("/filterByGenre/{userId}")
    public ResponseEntity getMoviesByGenre(@RequestParam(name = "genre", required = false) String genreName, @PathVariable Long userId) {
        List<Object[]> allMovies = reviewService.getReviewdMovies(userId);
        List<ReviewedMovieResponse> reviewedMovieResponses = allMovies.stream()
                .map(ReviewMapper::convertObjectArrayToReviewedMovieResponse)
                .collect(Collectors.toList());
        if (genreName == null || genreName.isEmpty()) {
            return ResponseMessage.ok("No Genre Specified", reviewedMovieResponses);
        }
        List<Object[]> objects = allMovies.stream()
                .filter(movie -> {
                    Movie dbMovie = (Movie) movie[0];
                    return dbMovie.getGenres().stream()
                            .anyMatch(genre -> genre.getName().equalsIgnoreCase(genreName));
                }).collect(Collectors.toList());
        if (objects.isEmpty()) {
            return ResponseMessage.notFound("No Movie Found");
        } else {
            List<ReviewedMovieResponse> movieResponses = objects.stream().map(ReviewMapper::convertObjectArrayToReviewedMovieResponse)
                    .collect(Collectors.toList());
            return ResponseMessage.ok("Success", movieResponses);
        }
    }

    @GetMapping("/filterByDecade/{userId}")
    public ResponseEntity getMoviesByDecade(@RequestParam("decade") String decade, @PathVariable Long userId) {
        List<Object[]> allMovies = reviewService.getReviewdMovies(userId);
        List<ReviewedMovieResponse> reviewedMovieResponses = allMovies.stream()
                .map(ReviewMapper::convertObjectArrayToReviewedMovieResponse)
                .collect(Collectors.toList());
        if (decade == null || decade.isEmpty()) {
            return ResponseMessage.ok("No Decade Specified", reviewedMovieResponses);
        }
        List<Object[]> objects = allMovies.stream().filter(movie -> {
                    Movie dbMovie = (Movie) movie[0];
                    return isMovieInDecade(dbMovie, decade);
                }).collect(Collectors.toList());
        if (objects.isEmpty()) {
            return ResponseMessage.notFound("No Movie Found");
        } else {
            List<ReviewedMovieResponse> movieResponses = objects.stream().map(ReviewMapper::convertObjectArrayToReviewedMovieResponse)
                    .collect(Collectors.toList());
            return ResponseMessage.ok("Success", movieResponses);
        }
    }

    @GetMapping("/search")
    public ResponseEntity searchMovies(@RequestParam("query") String searchTerm) {
        List<Movie> searchMovies = movieService.searchMovies(searchTerm);
        if (searchMovies.isEmpty()) {
            return ResponseMessage.ok("No Result Found", searchMovies);
        } else {
            List<SearchedMovieResponse> searchedMovieResponses = searchMovies.stream().map(MovieMapper::convertMovieToSearchedMovie)
                    .collect(Collectors.toList());
            return ResponseMessage.ok("Success", searchedMovieResponses);
        }
    }

    @GetMapping("/last")
    public ResponseEntity getLastMovies() {
        List<Movie> lastSixMovies = movieService.getLastSixMovies();
        if (lastSixMovies.isEmpty()) {
            return ResponseMessage.notFound("Movies Not Found");
        } else {
            List<MoviesResponse> moviesResponses = lastSixMovies.stream().map(MovieMapper::convertMovieToMoviesResponse)
                    .collect(Collectors.toList());
            return ResponseMessage.ok("Success", moviesResponses);
        }
    }


    @GetMapping("/trending")
    public ResponseEntity getTrendingMovies() {
        List<Movie> movies = movieService.getTrendingMovies();
        if (movies.isEmpty()) {
            return ResponseMessage.notFound("Trending Movies Not Found");
        } else {
            List<MoviesResponse> moviesResponses = movies.stream().map(MovieMapper::convertMovieToMoviesResponse)
                    .collect(Collectors.toList());
            return ResponseMessage.ok("Successfully gotten Trending Movies", moviesResponses);
        }
    }

    @GetMapping("/count")
    public ResponseEntity getTotalMovies() {
        long totalMovies = movieService.getTotalMovies();
        return ResponseMessage.ok("Success", totalMovies);
    }

    @GetMapping("/similar/{id}")
    public ResponseEntity getSimilarMovies(@PathVariable Long id) {
        List<Movie> movies = movieService.getSimilarMovies(id);
        if (movies.isEmpty()) {
            return ResponseMessage.notFound("Similar Movies Not Found");
        } else {
            List<MoviesResponse> moviesResponses = movies.stream().map(MovieMapper::convertMovieToMoviesResponse)
                    .collect(Collectors.toList());
            return ResponseMessage.ok("Successfully gotten Similar Movies", moviesResponses);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getMovieById(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        if (movie == null) {
            return ResponseMessage.notFound("Movie Not Found");
        } else {
            return ResponseMessage.ok("Success", movie);
        }
    }

    private boolean isMovieInDecade(Movie movie, String decade) {
        int year = movie.getYear();
        switch (decade.toLowerCase()) {
            case "1860s":
                return year >= 1860 && year <= 1869;
            case "1870s":
                return year >= 1870 && year <= 1879;
            case "1880s":
                return year >= 1880 && year <= 1889;
            case "1890s":
                return year >= 1890 && year <= 1899;
            case "1900s":
                return year >= 1900 && year <= 1909;
            case "1910s":
                return year >= 1910 && year <= 1919;
            case "1920s":
                return year >= 1920 && year <= 1929;
            case "1930s":
                return year >= 1930 && year <= 1939;
            case "1940s":
                return year >= 1940 && year <= 1949;
            case "1950s":
                return year >= 1950 && year <= 1959;
            case "1960s":
                return year >= 1960 && year <= 1969;
            case "1970s":
                return year >= 1970 && year <= 1979;
            case "1980s":
                return year >= 1980 && year <= 1989;
            case "1990s":
                return year >= 1990 && year <= 1999;
            case "2000s":
                return year >= 2000 && year <= 2009;
            case "2010s":
                return year >= 2010 && year <= 2019;
            case "2020s":
                return year >= 2020 && year <= 2024; // Up to 2024
            default:
                return false;
        }
    }
}

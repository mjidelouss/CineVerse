package com.cine.verse.controller;

import com.cine.verse.Dto.request.MovieRequest;
import com.cine.verse.mappers.MovieMapper;
import com.cine.verse.domain.Movie;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final MovieMapper movieMapper;

    @GetMapping("/all")
    public ResponseEntity getMovies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Page<Movie> moviesPage = movieService.getMovies(PageRequest.of(page, size));

        if (moviesPage.isEmpty()) {
            return ResponseMessage.notFound("Movies Not Found");
        } else {
            return ResponseMessage.ok("Success", moviesPage.getContent());
        }
    }

    @GetMapping("/last")
    public ResponseEntity getLastMovies() {
        List<Movie> lastSixMovies = movieService.getLastSixMovies();
        if (lastSixMovies.isEmpty()) {
            return ResponseMessage.notFound("Movies Not Found");
        } else {
            return ResponseMessage.ok("Success", lastSixMovies);
        }
    }


    @GetMapping("/trending")
    public ResponseEntity getTrendingMovies() {
        List<Movie> movies = movieService.getTrendingMovies();
        if (movies.isEmpty()) {
            return ResponseMessage.notFound("Trending Movies Not Found");
        } else {
            return ResponseMessage.ok("Successfully gotten Trending Movies", movies);
        }
    }

    @GetMapping("/similar/{id}")
    public ResponseEntity getSimilarMovies(@PathVariable Long id) {
        List<Movie> movies = movieService.getSimilarMovies(id);
        if (movies.isEmpty()) {
            return ResponseMessage.notFound("Similar Movies Not Found");
        } else {
            return ResponseMessage.ok("Successfully gotten Similar Movies", movies);
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

    @PostMapping("")
    public ResponseEntity addMovie(@RequestBody @Valid MovieRequest movieRequest) {
        Movie movie = movieMapper.movieRequestToMovie(movieRequest);
        Movie movie1 = movieService.addMovie(movie);
        if(movie1 == null) {
            return ResponseMessage.badRequest("Failed To Create Movie");
        } else {
            return ResponseMessage.created("Movie Created Successfully", movie1);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateMovie(@RequestBody @Valid MovieRequest movieRequest, @PathVariable Long id) {
        Movie movie = movieMapper.movieRequestToMovie(movieRequest);
        Movie movie1 = movieService.updateMovie(movie, id);
        if (movie1 == null) {
            return ResponseMessage.badRequest("Movie Not Updated");
        } else {
            return ResponseMessage.created("Movie Updated Successfully", movie1);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteMovie(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        if (movie == null) {
            return ResponseMessage.notFound("Movie Not Found");
        } else {
            movieService.deleteMovie(id);
            return ResponseMessage.ok("Movie Deleted Successfully", movie);
        }
    }
}

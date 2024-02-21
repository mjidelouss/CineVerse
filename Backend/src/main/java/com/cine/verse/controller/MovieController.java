package com.cine.verse.controller;

import com.cine.verse.Dto.request.MovieRequest;
import com.cine.verse.Mapper.MovieMapper;
import com.cine.verse.domain.Movie;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity getMovies() {
        List<Movie> movies = movieService.getMovies();
        if (movies.isEmpty()) {
            return ResponseMessage.notFound("Movies Not Found");
        } else {
            return ResponseMessage.ok("Success", movies);
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

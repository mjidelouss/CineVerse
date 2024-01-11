package com.cine.verse.service;

import com.cine.verse.domain.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getMovies();

    Movie getMovieById(Long id);

    Movie addMovie(Movie movie);

    Movie updateMovie(Movie movie, Long id);

    void deleteMovie(Long id);
}

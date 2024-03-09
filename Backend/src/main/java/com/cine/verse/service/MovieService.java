package com.cine.verse.service;

import com.cine.verse.Dto.response.MovieCredits;
import com.cine.verse.Dto.response.MovieDetailsTrailer;
import com.cine.verse.domain.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getMovies();

    Movie getMovieById(Long id);

    Movie addMovie(Movie movie);

    Movie updateMovie(Movie movie, Long id);

    void deleteMovie(Long id);

    int syncMovies();

    List<Movie> getTrendingMovies();
    MovieCredits getMovieCredits(Long movieId);
    MovieDetailsTrailer getMovieDetailsTrailer(Long movieId);
    public List<Movie> getSimilarMovies(Long movieId);
}

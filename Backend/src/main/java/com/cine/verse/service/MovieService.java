package com.cine.verse.service;

import com.cine.verse.Dto.response.MovieCredits;
import com.cine.verse.Dto.response.MovieDetailsTrailer;
import com.cine.verse.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {

    Movie getMovieById(Long id);

    Movie addMovie(Movie movie);

    List<Movie> getLastSixMovies();

    Movie updateMovie(Movie movie, Long id);

    void deleteMovie(Long id);

    int syncMovies();
    Page<Movie> getMovies(Pageable pageable);

    List<Movie> getTrendingMovies();
    MovieCredits getMovieCredits(Long movieId);
    MovieDetailsTrailer getMovieDetailsTrailer(Long movieId);
    public List<Movie> getSimilarMovies(Long movieId);
}

package com.cine.verse.mappers;

import com.cine.verse.Dto.response.MoviesResponse;
import com.cine.verse.Dto.response.SearchedMovieResponse;
import com.cine.verse.domain.Movie;

public class MovieMapper {

    public static MoviesResponse convertMovieToMoviesResponse(Movie movie) {
        return MoviesResponse.builder()
                .movieId(movie.getId())
                .movieYear(movie.getYear())
                .movieLanguage(movie.getLanguage())
                .movieImage(movie.getImage())
                .movieTitle(movie.getTitle())
                .build();
    }

    public static SearchedMovieResponse convertMovieToSearchedMovie(Movie movie) {
        return SearchedMovieResponse.builder()
                .movieId(movie.getId())
                .movieImage(movie.getImage())
                .movieTitle(movie.getTitle())
                .movieYear(movie.getYear())
                .build();
    }
}

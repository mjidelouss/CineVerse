package com.cine.verse.service;

import com.cine.verse.domain.MovieList;

import java.util.List;

public interface MovieListService {
    List<MovieList> getMovieLists();

    MovieList getMovieListById(Long id);

    MovieList addMovieList(MovieList movieList);

    MovieList updateMovieList(MovieList movieList, Long id);

    void deleteMovieList(Long id);
}
